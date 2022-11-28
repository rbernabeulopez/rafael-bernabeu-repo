package com.example.block7crudvalidation.application;

import com.example.block7crudvalidation.domain.entity.Student;

import java.util.List;

public interface StudentService {
    Student searchById(String id);
    List<Student> searchAll();
    void save(Student person, int personId);
    void deleteById(String id);
    void updateById(String id, Student person);
}
