package com.example.block7crudvalidation.application;

import com.example.block7crudvalidation.domain.entity.Person;

import java.util.List;

public interface PersonService {
    Person searchById(int id);
    List<Person> searchByName(String name);
    List<Person> searchAll();

    void save(Person person);

    void deleteById(int id);

    void updateById(int id, Person person);
}
