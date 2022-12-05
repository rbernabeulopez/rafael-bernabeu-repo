package com.example.block12kafkaproducer.infrastructure.controller;

import com.example.block12kafkaproducer.domain.Message;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("message")
@AllArgsConstructor
public class MessageController {

    private KafkaTemplate<String, Message> kafkaTemplate;

    @PostMapping
    public void sendMessage(@RequestBody String content) {
        kafkaTemplate.send("myTopic", new Message(content, LocalDateTime.now()));
    }
}
