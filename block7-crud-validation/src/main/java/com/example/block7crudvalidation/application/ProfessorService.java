package com.example.block7crudvalidation.application;

import com.example.block7crudvalidation.domain.entity.Professor;

import java.util.List;

public interface ProfessorService {
    Professor searchById(String id);
    List<Professor> searchAll();
    void save(Professor professor, int personId);
    void deleteById(String id);
    void updateById(String id, Professor professor);
}
