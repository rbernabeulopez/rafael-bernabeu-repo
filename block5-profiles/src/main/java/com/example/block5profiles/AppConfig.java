package com.example.block5profiles;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class AppConfig
{
    @Value("${spring.profiles.active}")
    private String environment;

    @Value("${bd.url}")
    private String bdUrl;
}