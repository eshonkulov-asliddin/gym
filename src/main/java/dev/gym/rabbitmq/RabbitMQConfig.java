package dev.gym.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static dev.gym.rabbitmq.RabbitMQConsts.DEAD_LETTER_BINDING_KEY;
import static dev.gym.rabbitmq.RabbitMQConsts.WORKLOAD_DIRECT_BINDING_KEY;
import static dev.gym.rabbitmq.RabbitMQConsts.WORKLOAD_DIRECT_EXCHANGE;
import static dev.gym.rabbitmq.RabbitMQConsts.WORKLOAD_MESSAGES;
import static dev.gym.rabbitmq.RabbitMQConsts.WORKLOAD_MESSAGES_DLX;

@Configuration
public class RabbitMQConfig {

    @Bean
    public DirectExchange direct() {
        return new DirectExchange(WORKLOAD_DIRECT_EXCHANGE);
    }

    @Bean
    public Queue workloadQueue() {
        return QueueBuilder.durable(WORKLOAD_MESSAGES)
                .withArgument("x-dead-letter-exchange", WORKLOAD_MESSAGES_DLX)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_BINDING_KEY)
                .build();
    }

    @Bean
    public Binding trainerWorkloadBinding() {
        return BindingBuilder.bind(workloadQueue())
                .to(direct())
                .with(WORKLOAD_DIRECT_BINDING_KEY);
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory,
                                                                               Jackson2JsonMessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        return factory;
    }

}
