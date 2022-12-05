package com.example.block12kafkaconsumer.infrastructure.kafka;

import com.example.block12kafkaconsumer.domain.Message;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
    @KafkaListener(topics = "myTopic", groupId = "groupId", containerGroup = "messageFactory")
    void listener(Message data) {
        System.out.println("Consumer received: " + data + " from queue");
    }
}