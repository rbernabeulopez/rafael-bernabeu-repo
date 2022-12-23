package org.example.openfeignclients.backend;

import org.example.openfeignclients.backend.dto.TripOutputDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("backend")
public interface TripFeign {
    @GetMapping("trip/{id}")
    TripOutputDto getById(@PathVariable("id") long id);
}
