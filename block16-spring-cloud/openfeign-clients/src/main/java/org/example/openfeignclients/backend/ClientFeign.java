package org.example.openfeignclients.backend;

import org.example.openfeignclients.backend.dto.ClientOutputDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("backend")
public interface ClientFeign {
    @GetMapping("client/{id}")
    ClientOutputDto getById(@PathVariable("id") long id);
}
