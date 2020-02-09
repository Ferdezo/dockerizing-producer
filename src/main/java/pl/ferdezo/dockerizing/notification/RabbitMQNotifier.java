package pl.ferdezo.dockerizing.notification;

import io.vavr.control.Option;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.ferdezo.dockerizing.model.Identifiable;

@Service
public class RabbitMQNotifier implements Notifier {
    private final RabbitTemplate rabbitTemplate;
    private final String exchangeName;
    private final String routingKey;

    public RabbitMQNotifier(
        RabbitTemplate rabbitTemplate,
        @Value("${messages.discount.exchange.name}") String exchangeName,
        @Value("${messages.discount.routing-key.name}") String routingKey
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
    }

    @Override
    public void accept(Identifiable discount) {
        Message message = Option.of(discount)
            .map(Object::toString)
            .map(String::getBytes)
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
