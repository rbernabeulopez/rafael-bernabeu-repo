package com.example.block5logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class Block5LoggingApplication {

    public static void main(String[] args) {
        SpringApplication.run(Block5LoggingApplication.class, args);
    }

    @Bean
    public void showLogs() {
      log.debug("Debugging");
      log.trace("Tracing");
      log.info("Informing");
      log.warn("Warning");
      log.error("Failing");
    }
}
