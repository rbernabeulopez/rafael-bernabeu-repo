package com.example.block13mongodb.domain.repository;

import com.example.block13mongodb.domain.entities.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, String> {
}
