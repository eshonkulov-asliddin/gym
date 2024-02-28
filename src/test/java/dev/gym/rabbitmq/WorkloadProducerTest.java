package dev.gym.rabbitmq;

import dev.gym.service.client.workload.TrainerWorkload;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static dev.gym.rabbitmq.RabbitMQConsts.WORKLOAD_DIRECT_BINDING_KEY;
import static dev.gym.rabbitmq.RabbitMQConsts.WORKLOAD_DIRECT_EXCHANGE;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkloadProducerTest {

    @Mock
    private RabbitTemplate template;
    @Mock
    private DirectExchange direct;
    @InjectMocks
    private WorkloadProducer workloadProducer;

    @Test
    void testSendingDirectMessage() {
        TrainerWorkload trainerWorkload = mock(TrainerWorkload.class);

        when(direct.getName()).thenReturn(WORKLOAD_DIRECT_EXCHANGE);

        doNothing().when(template).convertAndSend(WORKLOAD_DIRECT_EXCHANGE, WORKLOAD_DIRECT_BINDING_KEY, trainerWorkload);

        assertThatCode(() -> workloadProducer.send(trainerWorkload)).doesNotThrowAnyException();
        verify(this.template, times(1))
                .convertAndSend(eq(WORKLOAD_DIRECT_EXCHANGE), eq(WORKLOAD_DIRECT_BINDING_KEY), eq(trainerWorkload));

    }
}
