package com.example.block7crudvalidation.application;

import com.example.block7crudvalidation.domain.entity.Person;
import com.example.block7crudvalidation.domain.entity.Professor;
import com.example.block7crudvalidation.domain.entity.Student;
import com.example.block7crudvalidation.domain.entity.StudentsStudies;
import com.example.block7crudvalidation.domain.exception.EntityNotFoundException;
import com.example.block7crudvalidation.domain.exception.UnprocessableEntityException;
import com.example.block7crudvalidation.domain.repository.StudentRepository;
import com.example.block7crudvalidation.domain.repository.StudentsStudiesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;
    private PersonService personService;
    private ProfessorService professorService;
    private StudentsStudiesRepository studentsStudiesRepository;
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
        Objects.requireNonNull(id);
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student with id " + id + " not found"));
    }

    @Override
    public List<Student> searchAll() {
        log.info("Searching all students");
        return studentRepository.findAll();
    }

    @Override
    public void save(Student student, int personId, String professorId) {
        log.info("Saving user with data {}", student);
        validateStudentData(student);

        Person person = personService.searchById(personId);
        if(person.getStudent() != null || person.getProfessor() != null) {
            throw new UnprocessableEntityException(
                    "Person with id " + personId + " has a professor or an student assigned");
        }
        student.setPerson(person);
        person.setStudent(student);
        student.setPerson(person);

        Professor professor = professorService.searchById(professorId);
        student.setProfessor(professor);
        List<Student> students = professor.getStudents();
        students.add(student);
        professor.setStudents(students);

        studentRepository.save(student);
        professorService.updateById(professorId, professor);
    }

    @Override
    public void deleteById(String id) {
        log.info("Deleting student with id {}", id);
        this.searchById(id);
        List<StudentsStudies> studies = studentsStudiesRepository.findByStudent_StudentId(id);
        studies.forEach(study -> studentsStudiesRepository.deleteById(study.getSubjectId()));
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

    @Override
    public void unassignStudies(List<String> studiesIds) {
        studiesIds.forEach(studyId -> {
            studentsStudiesRepository.deleteById(studyId);
        });
    }
}
