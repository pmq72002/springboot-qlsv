package com.pmq.spring.qlsv.service;

import com.pmq.spring.qlsv.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StudentProducer {
    private final RabbitTemplate rabbitTemplate;

    public StudentProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendStudentCreated(String message) {
        rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_NAME, message);
        log.info("rabbit sent: " + message);
    }
}
