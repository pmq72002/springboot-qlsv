package com.pmq.spring.qlsv.service;

import com.pmq.spring.qlsv.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StudentConsumer {
    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void receiveMessage(String message) {
            log.info("Received: " + message);
    }
}
