package org.example.backendFront.domain.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode
public class Ticket {
    @Id
    @GeneratedValue
    private long id;

    private long passengerId;
    private String passengerName;
    private String passengerLastname;
    private String passengerEmail;
    private String tripOrigin;
    private String tripDestination;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
}
