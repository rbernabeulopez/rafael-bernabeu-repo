package com.example.block13mongodb.application;

import com.example.block13mongodb.domain.entities.Person;
import com.example.block13mongodb.domain.repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService {
    private PersonRepository personRepository;

    @Override
    public Page<Person> findAll(int offset, int pageSize) {
        log.info("Finding all persons with offset {} and pageSize {}", offset, pageSize);
        return personRepository.findAll(PageRequest.of(offset, pageSize));
    }

    @Override
    public Person findById(String id) {
        log.info("Finding person with id {}", id);
        return personRepository.findById(id).orElseThrow();
    }

    @Override
    public void create(Person person) {
        log.info("Creating person with data {}", person);
        personRepository.save(person);
    }

    @Override
    public void deleteById(String id) {
        log.info("Deleting person with id {}", id);
        findById(id);
        personRepository.deleteById(id);
    }

    @Override
    public void modifyById(String id, Person person) {
        log.info("Updating person with id {} & data {}", id, person);
        findById(id);
        person.setId(id);
        personRepository.save(person);
    }
}
