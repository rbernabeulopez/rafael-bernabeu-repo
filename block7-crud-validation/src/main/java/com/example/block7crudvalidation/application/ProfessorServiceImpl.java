package com.example.block7crudvalidation.application;

import com.example.block7crudvalidation.domain.entity.Person;
import com.example.block7crudvalidation.domain.entity.Professor;
import com.example.block7crudvalidation.domain.exception.EntityNotFoundException;
import com.example.block7crudvalidation.domain.exception.UnprocessableEntityException;
import com.example.block7crudvalidation.domain.repository.ProfessorRepository;
import com.example.block7crudvalidation.domain.repository.StudentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class ProfessorServiceImpl implements ProfessorService {
    private ProfessorRepository professorRepository;

    private PersonService personService;

    private StudentRepository studentRepository;

    private void validateProfessorData(Professor professor) {
        try {
            Objects.requireNonNull(professor.getBranch(), "Branch cannot be null");
        } catch (NullPointerException nullPointerException) {
            throw new UnprocessableEntityException(nullPointerException.getMessage());
        }
    }

    @Override
    public Professor searchById(String id) {
        log.info("Searching professor with given id {}", id);
        Objects.requireNonNull(id);
        return professorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Professor with id " + id + " not found"));
    }

    @Override
    public List<Professor> searchAll() {
        log.info("Searching all professors");
        return professorRepository.findAll();
    }

    @Override
    public void save(Professor professor, int personId) {
        log.info("Saving professor with data {}", professor);
        validateProfessorData(professor);

        Person person = personService.searchById(personId);
        if(person.getStudent() != null || person.getProfessor() != null) {
            throw new UnprocessableEntityException(
                    "Person with id " + personId + " has a professor or an student assigned");
        }
        professor.setPerson(person);
        person.setProfessor(professor);

        professorRepository.save(professor);
    }

    @Override
    public void deleteById(String id) {
        log.info("Deleting professor with id {}", id);
        Professor professor = this.searchById(id);
        professor.getStudents().forEach(student -> {
            student.setProfessor(null);
            studentRepository.save(student);
        });
        professorRepository.deleteById(id);
    }

    @Override
    public void updateById(String id, Professor professor) {
        log.info("Saving professor with data {}", professor);
        this.searchById(id);

        validateProfessorData(professor);

        professorRepository.save(professor);
    }
}
