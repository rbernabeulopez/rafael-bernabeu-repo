package org.example.backend.infrastructure.controller.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripInputDto {
    private String origin;

    private String destination;

    private LocalDate departureDate;

    private LocalDate arrivalDate;

    private String status;
}
