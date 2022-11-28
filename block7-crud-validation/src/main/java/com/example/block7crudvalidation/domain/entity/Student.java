package com.example.block7crudvalidation.domain.entity;

import jakarta.persistence.*;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Data
public class Student {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "student_id")
    private String studentId;

    @OneToOne
    @JoinColumn(name = "id")
    private Person person;

    @Column(name = "num_hours_week", nullable = false)
    private int numHoursWeek;

    private String comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @Column(nullable = false)
    private String branch;
}
