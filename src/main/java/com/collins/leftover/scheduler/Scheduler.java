package com.collins.leftover.scheduler;

import com.collins.leftover.event.PayPeriodClosedEvent;
import com.collins.leftover.model.PayPeriod;
import com.collins.leftover.publisher.RabbitMQProducer;
import com.collins.leftover.repository.PayPeriodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Scheduler {

    private final PayPeriodRepository payPeriodRepository;
    private final RabbitMQProducer rabbitMQProducer;

    @Scheduled(cron = "0 * * * * *")
    public void endOfPayPeriod(){
        List<PayPeriod> payPeriods = payPeriodRepository.findAll();
        payPeriods.forEach(payPeriod -> {
            if(payPeriod.isActive() && (payPeriod.getEndDate().isBefore(LocalDate.now()) || payPeriod.getEndDate().isEqual(LocalDate.now()))){
                payPeriod.setActive(false);
                payPeriodRepository.save(payPeriod);
                PayPeriodClosedEvent payPeriodClosedEvent = new PayPeriodClosedEvent(payPeriod.getId(), payPeriod.getUser().getId(), LocalDateTime.now());
                rabbitMQProducer.sendMessage(payPeriodClosedEvent);
            }
        });
    }
}
