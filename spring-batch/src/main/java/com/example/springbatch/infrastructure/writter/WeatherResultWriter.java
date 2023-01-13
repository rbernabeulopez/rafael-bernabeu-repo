package com.example.springbatch.infrastructure.writter;

import com.example.springbatch.domain.entity.WeatherResult;
import com.example.springbatch.domain.repository.WeatherResultRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@AllArgsConstructor
public class WeatherResultWriter implements ItemWriter<WeatherResult> {

   WeatherResultRepository weatherResultRepository;

    @Override
    public void write(List<? extends WeatherResult> weatherResults) {
        weatherResultRepository.saveAll(weatherResults);
    }
}