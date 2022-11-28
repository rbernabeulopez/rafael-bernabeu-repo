package com.example.block7crudvalidation.domain.repository;

import com.example.block7crudvalidation.domain.entity.Person;
import com.example.block7crudvalidation.domain.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    Student findByPerson_IdLike(int personId);
}
