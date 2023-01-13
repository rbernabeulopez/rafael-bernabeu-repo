package com.example.springbatch.domain.entity;

import com.example.springbatch.domain.enums.Risk;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class WeatherRisk {

    @Id
    @GeneratedValue
    private long id;

    private String predictionDate;

    @Enumerated(EnumType.STRING)
    private Risk risk;

    @OneToOne
    private Weather weather;
}
