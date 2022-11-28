package com.example.block7crudvalidation.domain.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "Alumnos_Estudios")
public class StudentsStudies {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "subject_id")
    private String subjectId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private Student student;

    private String subject;

    private String comments;

    @Column(name = "initial_date")
    private Date initialDate;

    @Column(name = "finish_date")
    private Date finishDate;
}
