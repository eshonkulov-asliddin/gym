package dev.gym.rabbitmq;

import dev.gym.service.client.workload.ActionType;
import dev.gym.service.client.workload.TrainerWorkload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static dev.gym.rabbitmq.RabbitMQConsts.WORKLOAD_MESSAGES;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
public class WorkloadProducerIT {

    @Container
    public static RabbitMQContainer rabbitMQContainer = new RabbitMQContainer("rabbitmq:3-management").withReuse(true);
    private BlockingQueue<TrainerWorkload> receivedMessages = new LinkedBlockingQueue<>();

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private DirectExchange direct;

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", rabbitMQContainer::getHost);
        registry.add("spring.rabbitmq.port", rabbitMQContainer::getAmqpPort);
    }

    @BeforeEach
    void clearReceivedMessages() {
        receivedMessages.clear();
    }

    @Test
    void testSendingDirectMessage() throws InterruptedException {
        TrainerWorkload trainerWorkload = new TrainerWorkload("trUsername", "trFirstname", "trLastname", true, LocalDate.now().plusDays(2), 60, ActionType.ADD);
        WorkloadProducer workloadProducer = new WorkloadProducer(rabbitTemplate, direct);
        workloadProducer.send(trainerWorkload);

        Thread.sleep(1000);

        assertThat(receivedMessages).contains(trainerWorkload);
    }

    @RabbitListener(queues = WORKLOAD_MESSAGES)
    public void receive(TrainerWorkload trainerWorkload) {
        receivedMessages.add(trainerWorkload);
    }
}
