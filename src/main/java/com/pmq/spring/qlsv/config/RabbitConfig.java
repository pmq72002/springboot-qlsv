package com.pmq.spring.qlsv.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String QUEUE_NAME = "studentQueue";
    public static final String QUEUE_PDF = "PdfQueue";

    @Bean
    public Queue studentQueue() {
        return new Queue(QUEUE_NAME, true); // durable
    }
    @Bean
    public Queue pdfQueue() {
        return new Queue(QUEUE_PDF, true); // durable
    }
}
