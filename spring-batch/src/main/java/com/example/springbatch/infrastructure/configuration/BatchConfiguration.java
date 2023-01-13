package com.example.springbatch.infrastructure.configuration;

import com.example.springbatch.domain.entity.Weather;
import com.example.springbatch.domain.exception.InvalidTemperatureException;
import com.example.springbatch.domain.repository.WeatherRepository;
import com.example.springbatch.domain.repository.WeatherResultRepository;
import com.example.springbatch.infrastructure.dto.WeatherInputDto;
import com.example.springbatch.infrastructure.processor.WeatherProcessor;
import com.example.springbatch.infrastructure.reader.WeatherReader;
import com.example.springbatch.infrastructure.writter.WeatherGroupWriter;
import com.example.springbatch.infrastructure.writter.WeatherResultWriter;
import com.example.springbatch.infrastructure.writter.WeatherWriter;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@AllArgsConstructor
@Configuration
public class BatchConfiguration {
    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private WeatherRepository weatherRepository;
    private WeatherResultRepository weatherResultRepository;
    private WeatherReader weatherReader;


    /**
     * STEP 1: Write weather data from CSV file to database
     */

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<WeatherInputDto, Weather>chunk(100)
                .faultTolerant()
                .skipLimit(100)
                .skip(InvalidTemperatureException.class)
                .reader(weatherReader.csvLineReader())
                .processor(new WeatherProcessor())
                .writer(new WeatherWriter(weatherRepository))
                .build();
    }

    /**
     * STEP 2: Group weather data from CSV file to database
     */
    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .<WeatherInputDto, Weather>chunk(100)
                .faultTolerant()
                .skipLimit(100)
                .skip(InvalidTemperatureException.class)
                .reader(weatherReader.csvLineReader())
                .processor(new WeatherProcessor())
                .writer(new WeatherGroupWriter(new WeatherResultWriter(weatherResultRepository)))
                .build();
    }

    @Bean
    public Job processCsvJob() {
        return jobBuilderFactory.get("processCsvJob")
                .incrementer(new RunIdIncrementer())
                .listener(new JobListener())
                .flow(step1())
                .next(step2())
                .end()
                .build();
    }
}
