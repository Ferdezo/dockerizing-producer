package pl.ferdezo.dockerizing.notification;

import java.util.Optional;

import io.vavr.API;
import io.vavr.control.Option;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.vavr.control.Try;
import lombok.extern.log4j.Log4j2;
import pl.ferdezo.dockerizing.model.Identifiable;

@Service
@Log4j2
public class RabbitMQNotifier implements Notifier {
    private final RabbitTemplate rabbitTemplate;
    private final String exchangeName;
    private final String routingKey;
    private final ObjectMapper objectMapper;

    public RabbitMQNotifier(
        RabbitTemplate rabbitTemplate,
        @Value("${messages.discount.exchange.name}") String exchangeName,
        @Value("${messages.discount.routing-key.name}") String routingKey
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void accept(Identifiable discount) {
        Message message = Option.of(discount)
            .map(API.unchecked(objectMapper::writeValueAsBytes))
            .map(MessageBuilder::withBody)
            .getOrElseThrow(IllegalAccessError::new)
            .andProperties(MessagePropertiesBuilder
                .newInstance()
                .setHeader("id", discount.getId())
                .build()
            )
            .build();

        rabbitTemplate.send(exchangeName, routingKey, message);
    }
}
