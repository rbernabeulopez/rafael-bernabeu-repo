package com.example.block13mongodb.application;

import com.example.block13mongodb.domain.entities.Person;
import org.springframework.data.domain.Page;

public interface PersonService {
    Page<Person> findAll(int offset, int pageSize);
    Person findById(String id);
    void create(Person person);
    void deleteById(String id);
    void modifyById(String id, Person person);
}
