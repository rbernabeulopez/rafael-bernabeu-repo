package com.example.block12kafkaconsumer.domain;

import java.time.LocalDateTime;

public record Message(String message, LocalDateTime created) {

}