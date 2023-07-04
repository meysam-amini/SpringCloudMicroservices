package com.meysam.common.utils.config;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.logback.AmqpAppender;

import java.lang.reflect.Field;

@Getter
@Setter
public class ExtendedAmqpAppender extends AmqpAppender {
    private boolean required = false;

    @SneakyThrows
    @Override
    public void start() {
        try {
            super.start();
            if (required) {
                Field connectionFactoryField = AmqpAppender.class.getDeclaredField("connectionFactory");
                connectionFactoryField.setAccessible(true);
                ConnectionFactory connectionFactory = (ConnectionFactory) connectionFactoryField.get(this);
                connectionFactory.createConnection();
            }
        } catch (Throwable ex) {
            addError("Cannot initialize RabbitMQ connection for log. Logging on rabbit is REQUIRED", ex);
            stop();
        }
    }
}
