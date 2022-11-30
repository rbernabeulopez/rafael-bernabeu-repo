package com.example.block7crudvalidation.application;

import com.example.block7crudvalidation.domain.entity.Student;
import com.example.block7crudvalidation.domain.entity.StudentsStudies;
import com.example.block7crudvalidation.domain.exception.EntityNotFoundException;
import com.example.block7crudvalidation.domain.exception.UnprocessableEntityException;
import com.example.block7crudvalidation.domain.repository.StudentsStudiesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class StudentsStudiesStudiesServiceImpl implements StudentsStudiesService {
    private StudentsStudiesRepository studentsStudiesRepository;

    private StudentService studentService;

    private void validateStudentsStudiesData(StudentsStudies studentsStudies) {
        try {
            Objects.requireNonNull(studentsStudies.getInitialDate(), "Initial date cannot be null");
        } catch (NullPointerException nullPointerException) {
            throw new UnprocessableEntityException(nullPointerException.getMessage());
        }
    }

    @Override
    public StudentsStudies searchById(String id) {
        log.info("Searching students studies with given id {}", id);
        Objects.requireNonNull(id);
        return studentsStudiesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StudentsStudies with id " + id + " not found"));
    }

    @Override
    public List<StudentsStudies> searchAll() {
        log.info("Searching all students studies");
        return studentsStudiesRepository.findAll();
    }

    @Override
    public void save(StudentsStudies studentsStudies, String studentId) {
        log.info("Saving students studies with data {}", studentsStudies);
        validateStudentsStudiesData(studentsStudies);

        Student student = studentService.searchById(studentId);
        studentsStudies.setStudent(student);
        List<StudentsStudies> studies = student.getStudies();
        studies.add(studentsStudies);
        student.setStudies(studies);
        studentsStudiesRepository.save(studentsStudies);
        studentService.updateById(student.getStudentId(), student);
    }

    @Override
    public void deleteById(String id) {
        log.info("Deleting students studies with id {}", id);
        this.searchById(id);

        studentsStudiesRepository.deleteById(id);
    }

    @Override
    public void deleteByStudentId(String studentId) {
        log.info("Deleting students studies from student with id {}", studentId);
        List<StudentsStudies> studies = studentsStudiesRepository.findByStudent_StudentId(studentId);
        studies.forEach(study -> studentsStudiesRepository.deleteById(study.getSubjectId()));
    }

    @Override
    public void updateById(String id, StudentsStudies studentsStudies) {
        log.info("Saving students studies with data {}", studentsStudies);
        this.searchById(id);

        validateStudentsStudiesData(studentsStudies);

        studentsStudiesRepository.save(studentsStudies);
    }

    @Override
    public List<StudentsStudies> findByStudentId(String studentId) {
        log.info("Searching students studies with given student id {}", studentId);
        Objects.requireNonNull(studentId);
        return studentsStudiesRepository.findByStudent_StudentId(studentId);
    }
}
