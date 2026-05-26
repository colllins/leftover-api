package com.collins.leftover.consumer;

import com.collins.leftover.event.PayPeriodClosedEvent;
import com.collins.leftover.model.PayPeriod;
import com.collins.leftover.model.PayPeriodSummary;
import com.collins.leftover.repository.PayPeriodRepository;
import com.collins.leftover.repository.PayPeriodSummaryRepository;
import com.collins.leftover.service.NotificationService;
import com.collins.leftover.service.PayPeriodSummaryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);
    private final PayPeriodRepository payPeriodRepository;
    private final PayPeriodSummaryService payPeriodSummaryService;
    private final NotificationService notificationService;
    private final PayPeriodSummaryRepository payPeriodSummaryRepository;

    @RabbitListener(queues = {"${pay-period.closed.queue.name}"})
    public void consumeMessage(PayPeriodClosedEvent payPeriodClosedEvent){

        LOGGER.info(String.format("Received JSON message -> %s", payPeriodClosedEvent.toString()));

        PayPeriod payPeriod = payPeriodRepository.findById(payPeriodClosedEvent.getPayPeriodId()).orElseThrow(() -> new RuntimeException("Pay period not found"));

        if (payPeriodSummaryRepository.existsByPayPeriodId(payPeriod.getId())) {
            LOGGER.info("Summary already exists for pay period {}", payPeriod.getId());
            return;
        }

        PayPeriodSummary payPeriodSummary = payPeriodSummaryService.createSummary(payPeriod);

        String title = "Your Pay Period summary is Ready!";
        String message = "Your Pay Period Summary is Ready. You had $"+payPeriodSummary.getLeftOver()+" left over.";

        notificationService.createNotification(payPeriod.getUser(), title, message, false);


    }

}
