package com.example.springbatch.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class Weather {
    @Id
    @GeneratedValue
    private long id;

    private String location;

    private String day;

    private int temperature;

    @OneToOne(cascade = CascadeType.ALL)
    private WeatherRisk weatherRisk;
}
