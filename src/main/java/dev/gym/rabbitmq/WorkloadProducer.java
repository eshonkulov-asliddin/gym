package dev.gym.rabbitmq;

import dev.gym.service.client.workload.TrainerWorkload;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WorkloadProducer {

    private final RabbitTemplate template;
    private final DirectExchange direct;
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkloadProducer.class);

    public void send(TrainerWorkload workload) {
        this.template.convertAndSend(direct.getName(), RabbitMQConsts.WORKLOAD_DIRECT_BINDING_KEY,  workload);
        LOGGER.info(" [x] Sent {}", workload);
    }
}
