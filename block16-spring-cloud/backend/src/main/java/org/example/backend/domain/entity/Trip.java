package org.example.backend.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode
@ToString
public class Trip {
    @Id
    @GeneratedValue
    private long id;

    private String origin;

    private String destination;

    private LocalDate departureDate;

    private LocalDate arrivalDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    private List<Client> passengers;

    private String status;
}
