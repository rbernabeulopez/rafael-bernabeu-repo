package com.example.springbatch.infrastructure.configuration;

import com.example.springbatch.domain.repository.WeatherRepository;
import com.example.springbatch.domain.repository.WeatherRiskRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;

@Slf4j
@AllArgsConstructor
public class JobListener implements org.springframework.batch.core.JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("Iniciando job");
        log.info("Borrando la tabla TiempoRiesgo de la base de datos");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus().isUnsuccessful()){
            log.error("Errores ejecutando el job");
            jobExecution.getAllFailureExceptions().forEach(e->log.error(e.getMessage()));
            log.error("Borrando la tabla Tiempo de la base de datos");
        }else{
            log.info("Finalizando job -> Ejecución exitosa");
        }
    }
}