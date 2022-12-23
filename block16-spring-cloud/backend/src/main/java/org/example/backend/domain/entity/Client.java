package org.example.backend.domain.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode
public class Client {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String surname;

    private int age;

    private String email;

    private int phoneNumber;
}
