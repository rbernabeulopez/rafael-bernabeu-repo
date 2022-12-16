package com.example.block7crudvalidation.domain.entity;

import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Professor {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "professor_id")
    private String professorId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Person person;

    private String comments;

    @Column(nullable = false)
    private String branch;

    @OneToMany(mappedBy = "professor", fetch = FetchType.EAGER)
    private List<Student> students = new ArrayList<>();
}
