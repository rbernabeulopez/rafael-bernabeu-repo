package com.example.block7crudvalidation.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "Students_Studies")
public class StudentsStudies {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "subject_id")
    private String subjectId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private String subject;

    private String comments;

    @Column(name = "initial_date")
    private Date initialDate;

    @Column(name = "finish_date")
    private Date finishDate;
}
