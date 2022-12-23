package org.example.openfeignclients.backend.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class TripOutputDto {
    private long id;

    private String origin;

    private String destination;

    private LocalDate departureDate;

    private LocalDate arrivalDate;

    private List<ClientOutputDto> passengers;

    private String status;
}
