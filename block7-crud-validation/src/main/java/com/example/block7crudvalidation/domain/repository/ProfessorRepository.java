package com.example.block7crudvalidation.domain.repository;

import com.example.block7crudvalidation.domain.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, String> {

}
