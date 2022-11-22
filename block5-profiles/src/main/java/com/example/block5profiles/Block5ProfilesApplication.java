package com.example.block5profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Block5ProfilesApplication {

    public static void main(String[] args) {
        SpringApplication.run(Block5ProfilesApplication.class, args);
    }

     @Autowired
     AppConfig appConfig;

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            System.out.println(appConfig.getBdUrl() + " " + appConfig.getEnvironment()
            );
        };
    }
}
