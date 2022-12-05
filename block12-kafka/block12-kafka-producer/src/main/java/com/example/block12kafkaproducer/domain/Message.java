package com.example.block12kafkaproducer.domain;

import java.time.LocalDateTime;

public record Message(String message, LocalDateTime created) {

}