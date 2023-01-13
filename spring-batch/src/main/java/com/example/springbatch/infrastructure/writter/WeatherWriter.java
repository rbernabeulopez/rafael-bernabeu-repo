package com.example.springbatch.infrastructure.writter;

import com.example.springbatch.domain.entity.Weather;
import com.example.springbatch.domain.repository.WeatherRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@AllArgsConstructor
@Slf4j
public class WeatherWriter implements ItemWriter<Weather> {

    private WeatherRepository weatherRepository;

    @Override
    public void write(List<? extends Weather> weathers) {
        log.info("Guardando el tiempo: " + weathers);
        weatherRepository.saveAll(weathers);
    }
}