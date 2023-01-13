package com.example.springbatch.infrastructure.writter;

import com.example.springbatch.domain.entity.Weather;
import com.example.springbatch.domain.entity.WeatherResult;
import com.example.springbatch.domain.enums.Risk;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.util.Pair;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherGroupWriter implements ItemWriter<Weather> {
    private final Map<Pair<String, String>, WeatherResult> weatherGroupedData = new HashMap<>();
    private final ItemWriter<WeatherResult> itemWriter;

    public WeatherGroupWriter(ItemWriter<WeatherResult> itemWriter) {
        this.itemWriter = itemWriter;
    }

    private WeatherResult generateWeatherResult(Weather weather) {
        WeatherResult weatherResult = new WeatherResult();
        weatherResult.setLocation(weather.getLocation());
        weatherResult.setYear(LocalDate.parse(weather.getDay(), DateTimeFormatter.ofPattern("dd/MM/yyyy")).getYear());
        weatherResult.setMonth(LocalDate.parse(weather.getDay(), DateTimeFormatter.ofPattern("dd/MM/yyyy")).getMonthValue());
        weatherResult.setNumMeasurements(1);
        weatherResult.setAverageTemperature(weather.getTemperature());
        weatherResult.setRisk(weather.getWeatherRisk().getRisk());
        return weatherResult;
    }

    private WeatherResult updateWeatherResult(WeatherResult weatherResult, Weather weather) {
        weatherResult.setNumMeasurements(weatherResult.getNumMeasurements() + 1);
        weatherResult.setAverageTemperature((weatherResult.getAverageTemperature() + weather.getTemperature()) / 2);
        if(weatherResult.getAverageTemperature() > 36) {
            weatherResult.setRisk(Risk.HIGH);
        } else if(weather.getTemperature() < -24) {
            weatherResult.setRisk(Risk.LOW);
        } else {
            weatherResult.setRisk(Risk.MEDIUM);
        }
        return weatherResult;
    }

    @Override
    public void write(List<? extends Weather> weathers) throws Exception {
        for (Weather weather : weathers) {
            Pair<String, String> key = Pair.of(weather.getLocation(), weather.getDay());
            WeatherResult weatherResult = weatherGroupedData.get(key);
            if (weatherResult == null) {
                weatherGroupedData.put(key, generateWeatherResult(weather));
            } else {
                weatherGroupedData.put(key, updateWeatherResult(weatherResult, weather));
            }
        }
        itemWriter.write(weatherGroupedData.values().stream().toList());
    }
}
