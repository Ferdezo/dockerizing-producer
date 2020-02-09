package pl.ferdezo.dockerizing;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import lombok.RequiredArgsConstructor;
import pl.ferdezo.dockerizing.model.Discount;
import pl.ferdezo.dockerizing.notification.Notifier;

@SpringBootApplication
@EnableRabbit
@EnableMongoRepositories(basePackages = "pl.ferdezo.dockerizing.repository")
@RequiredArgsConstructor
public class DockerizingApplication implements CommandLineRunner {
    private final Notifier rabbitmqNotifier;

    public static void main(String[] args) {
        SpringApplication.run(DockerizingApplication.class, args);
    }

    @Override
    public void run(String... args) {
        rabbitmqNotifier.accept(new Discount());
    }
}
