package com.example.springbatch.infrastructure.reader;

import com.example.springbatch.domain.entity.Weather;
import com.example.springbatch.infrastructure.dto.WeatherInputDto;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class WeatherReader {
    @Bean
    public FlatFileItemReader<WeatherInputDto> csvLineReader() {
        return new FlatFileItemReaderBuilder<WeatherInputDto>()
                .name("weatherLineReader")
                .resource(new ClassPathResource("input.csv"))
                .delimited()
                .names("location", "day", "temperature")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(WeatherInputDto.class);
                }})
                .build();
    }
}
