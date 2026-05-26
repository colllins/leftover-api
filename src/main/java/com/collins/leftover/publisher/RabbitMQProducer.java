package com.collins.leftover.publisher;

import com.collins.leftover.event.PayPeriodClosedEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQProducer {

    @Value("${pay-period.exchange.name}")
    private String exchange;

    @Value("${pay-period.routing.key}")
    private String routingKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(PayPeriodClosedEvent payPeriodClosedEvent){
        LOGGER.info(String.format("Json message sent -> %s",payPeriodClosedEvent.toString()));
        rabbitTemplate.convertAndSend(exchange, routingKey, payPeriodClosedEvent);

    }
}
