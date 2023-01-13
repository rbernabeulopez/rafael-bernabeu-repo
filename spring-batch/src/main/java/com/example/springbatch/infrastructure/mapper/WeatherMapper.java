package com.example.springbatch.infrastructure.mapper;

import com.example.springbatch.domain.entity.Weather;
import com.example.springbatch.infrastructure.dto.WeatherInputDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WeatherMapper {
    WeatherMapper INSTANCE = Mappers.getMapper(WeatherMapper.class);

    Weather toWeather(WeatherInputDto weatherInputDto);
}
