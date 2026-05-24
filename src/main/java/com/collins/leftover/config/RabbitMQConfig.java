package com.collins.leftover.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${pay-period.closed.queue.name}")
    private String queue;

    @Value("${pay-period.exchange.name}")
    private String exchange;

    @Value("${pay-period.routing.key}")
    private String routingKey;

    //bean for rabbitmq queue
    @Bean
    public Queue queue(){
        return new Queue(queue);
    }

    //bean for exchange
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }

    //bind queue to exchange w routing key
    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue())
                .to(exchange())
                .with(routingKey);
    }


    @Bean
    public MessageConverter converter(){
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
