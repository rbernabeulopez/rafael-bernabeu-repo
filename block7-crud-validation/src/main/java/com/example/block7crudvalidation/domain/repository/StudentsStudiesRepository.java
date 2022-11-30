package com.example.block7crudvalidation.domain.repository;

import com.example.block7crudvalidation.domain.entity.StudentsStudies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StudentsStudiesRepository extends JpaRepository<StudentsStudies, String> {
    List<StudentsStudies> findByStudent_StudentId(String id);
}
