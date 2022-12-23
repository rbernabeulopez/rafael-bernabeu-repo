package org.example.backendFront;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "org.example.openfeignclients")
public class BackendFrontApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendFrontApplication.class, args);
    }
}