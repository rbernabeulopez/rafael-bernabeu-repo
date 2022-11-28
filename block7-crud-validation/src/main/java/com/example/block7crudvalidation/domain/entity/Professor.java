package com.example.block7crudvalidation.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Data
public class Professor {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "professor_id")
    private String professorId;

    @OneToOne
    @JoinColumn(name = "id")
    private Person person;

    private String comments;

    @Column(nullable = false)
    private String branch;

    @OneToMany
    private List<Student> students;
}
