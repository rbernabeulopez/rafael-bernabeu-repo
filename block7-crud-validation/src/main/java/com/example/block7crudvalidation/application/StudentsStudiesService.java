package com.example.block7crudvalidation.application;

import com.example.block7crudvalidation.domain.entity.StudentsStudies;

import java.util.List;

public interface StudentsStudiesService {
    StudentsStudies searchById(String id);
    List<StudentsStudies> searchAll();
    void save(StudentsStudies studentsStudies, String studentId);
    void deleteById(String id);

    void deleteByStudentId(String studentId);

    void updateById(String id, StudentsStudies studentsStudies);

    List<StudentsStudies> findByStudentId(String studentId);
}
