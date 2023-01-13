package com.example.springbatch.domain.repository;

import com.example.springbatch.domain.entity.WeatherResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherResultRepository extends JpaRepository<WeatherResult, Long> {
}
