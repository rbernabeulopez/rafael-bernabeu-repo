package com.example.springbatch.infrastructure.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherInputDto {
    private String location;

    private String day;

    private int temperature;
}
