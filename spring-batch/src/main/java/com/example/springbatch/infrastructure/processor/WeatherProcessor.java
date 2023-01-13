package com.example.springbatch.infrastructure.processor;

import com.example.springbatch.domain.entity.Weather;
import com.example.springbatch.domain.entity.WeatherRisk;
import com.example.springbatch.domain.enums.Risk;
import com.example.springbatch.domain.exception.InvalidTemperatureException;
import com.example.springbatch.infrastructure.dto.WeatherInputDto;
import com.example.springbatch.infrastructure.mapper.WeatherMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class WeatherProcessor implements ItemProcessor<WeatherInputDto, Weather> {
    @Override
    public Weather process(WeatherInputDto weatherInputDto) {
        log.info("Procesando el tiempo: " + weatherInputDto);
        if(weatherInputDto.getTemperature() > 50 || weatherInputDto.getTemperature() < -20) {
            log.error("La temperatura es invalida: " + weatherInputDto.getTemperature());
            throw new InvalidTemperatureException("Invalid temperature: " + weatherInputDto.getTemperature());
        }
        Weather weather = WeatherMapper.INSTANCE.toWeather(weatherInputDto);

        WeatherRisk weatherRisk = new WeatherRisk();
        weatherRisk.setWeather(weather);
        weatherRisk.setPredictionDate(weather.getDay());
        if(weather.getTemperature() > 36) {
            weatherRisk.setRisk(Risk.HIGH);
        } else if(weather.getTemperature() < -24) {
            weatherRisk.setRisk(Risk.LOW);
        } else {
            weatherRisk.setRisk(Risk.MEDIUM);
        }

        weather.setWeatherRisk(weatherRisk);
        return weather;
    }
}

