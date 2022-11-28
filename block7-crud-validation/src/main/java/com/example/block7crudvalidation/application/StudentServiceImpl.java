package com.example.block7crudvalidation.application;

import com.example.block7crudvalidation.domain.entity.Person;
import com.example.block7crudvalidation.domain.entity.Student;
import com.example.block7crudvalidation.domain.exception.EntityNotFoundException;
import com.example.block7crudvalidation.domain.exception.UnprocessableEntityException;
import com.example.block7crudvalidation.domain.repository.StudentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;
    private PersonService personService;

    private void validateStudentData(Student student) {
        try {
            Objects.requireNonNull(student.getBranch(), "Branch cannot be null");
        } catch (NullPointerException nullPointerException) {
            throw new UnprocessableEntityException(nullPointerException.getMessage());
        }
    }

    @Override
    public Student searchById(String id) {
        log.info("Searching student with given id {}", id);
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student with id " + id + " not found"));
    }

    @Override
    public List<Student> searchAll() {
        log.info("Searching all students");
        return studentRepository.findAll();
    }

    @Override
    public void save(Student student, int personId) {
        log.info("Saving user with data {}", student);
        validateStudentData(student);

        if(studentRepository.findByPerson_IdLike(personId) != null) {
            throw new UnprocessableEntityException("Already exists an student with personId " + personId + " assigned");
        }

        Person person = personService.searchById(personId);
        student.setPerson(person);

        studentRepository.save(student);
    }

    @Override
    public void deleteById(String id) {
        log.info("Deleting student with id {}", id);
        this.searchById(id);
        studentRepository.deleteById(id);
    }

    @Override
    public void updateById(String id, Student student) {
        log.info("Saving student with data {}", student);
        this.searchById(id);

        validateStudentData(student);

        student.setStudentId(id);
        studentRepository.save(student);
    }
}
