package com.example.block7crudvalidation.application;

import com.example.block7crudvalidation.domain.entity.Person;
import com.example.block7crudvalidation.domain.repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService {
    private PersonRepository personRepository;

    private void validatePersonData(Person person) {
        Objects.requireNonNull(person.getUser());
        Objects.requireNonNull(person.getPassword());
        Objects.requireNonNull(person.getName());
        Objects.requireNonNull(person.getCompanyEmail());
        Objects.requireNonNull(person.getPersonalEmail());
        Objects.requireNonNull(person.getCity());
        if (person.getUser().length() > 10) {
            throw new RuntimeException("User length cannot exceed 10 characters");
        }
        if (person.getUser().length() < 6) {
            throw new RuntimeException("User length cannot be less than 6 characters");
        }
    }

    @Override
    public Person searchById(int id) {
        log.info("Searching user with given id {}", id);
        return personRepository.findById(id).orElse(null);
    }

    @Override
    public List<Person> searchByName(String name) {
        log.info("Searching user with given name {}", name);
        return personRepository.findByName(name);
    }

    @Override
    public List<Person> searchAll() {
        log.info("Searching all users");
        return personRepository.findAll();
    }

    @Override
    public void save(Person person) {
        log.info("Saving user with data {}", person);
        validatePersonData(person);
        person.setCreatedDate(LocalDate.now());
        person.setActive(false);
        personRepository.save(person);
    }

    @Override
    public void deleteById(int id) {
        log.info("Deleting user with id {}", id);
        personRepository.deleteById(id);
    }

    @Override
    public void updateById(int id, Person person) {
        log.info("Saving user with data {}", person);
        validatePersonData(person);
        Person personDB = searchById(id);
        person.setId(id);
        person.setCreatedDate(personDB.getCreatedDate());
        person.setActive(person.isActive());
        personRepository.save(person);
    }
}
