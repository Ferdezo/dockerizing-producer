package pl.ferdezo.dockerizing.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Exchange discountExchange(
        @Value("${messages.discount.exchange.name}") String exchangeName
    ) {
        return ExchangeBuilder
            .topicExchange(exchangeName)
            .durable(true)
            .build();
    }

    @Bean
    public Queue discountQueue(
        @Value("${messages.discount.queue.name}") String queueName
    ) {
        return QueueBuilder
            .durable(queueName)
            .build();
    }

    @Bean
    public Binding discountBinding(
        Queue discountQueue,
        Exchange discountExchange,
        @Value("${messages.discount.routing-key.name}") String routingKey
    ) {
        return BindingBuilder
            .bind(discountQueue)
            .to(discountExchange)
            .with(routingKey)
            .noargs();
    }
}
