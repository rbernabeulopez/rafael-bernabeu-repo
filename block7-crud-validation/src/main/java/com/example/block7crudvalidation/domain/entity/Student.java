package com.example.block7crudvalidation.domain.entity;

import javax.persistence.*;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Student {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "student_id")
    private String studentId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Person person;

    @Column(name = "num_hours_week", nullable = false)
    private int numHoursWeek;

    private String comments;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @Column(nullable = false)
    private String branch;

    @OneToMany(mappedBy = "student")
    private List<StudentsStudies> studies = new ArrayList<>();
}
