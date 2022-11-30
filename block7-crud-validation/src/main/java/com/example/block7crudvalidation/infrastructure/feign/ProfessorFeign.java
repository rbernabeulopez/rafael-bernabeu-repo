package com.example.block7crudvalidation.infrastructure.feign;

import com.example.block7crudvalidation.infrastructure.controller.dto.output.ProfessorOutputDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "http://localhost:8080", name = "professorFeign")
public interface ProfessorFeign {
    @GetMapping("professor/{id}")
    ProfessorOutputDto searchById(@PathVariable String id,
                              @RequestParam(value = "ouputType", defaultValue = "simple") String ouputType);
}
