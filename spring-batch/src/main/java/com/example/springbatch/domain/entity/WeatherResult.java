package com.example.springbatch.domain.entity;

import com.example.springbatch.domain.enums.Risk;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class WeatherResult {
    @Id
    @GeneratedValue
    private long id;

    private String location;

    private int month;

    private int numMeasurements;

    private int year;

    private double averageTemperature;

    @Enumerated(EnumType.STRING)
    private Risk risk;
}
