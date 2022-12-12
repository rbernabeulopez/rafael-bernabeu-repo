package com.example.block7crudvalidation.application;

import com.example.block7crudvalidation.domain.entity.Person;
import com.example.block7crudvalidation.domain.exception.EntityNotFoundException;
import com.example.block7crudvalidation.domain.exception.UnprocessableEntityException;
import com.example.block7crudvalidation.domain.repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService {
    private PersonRepository personRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private void validatePersonData(Person person) {
        try {
            Objects.requireNonNull(person.getUser(), "User cannot be null");
            Objects.requireNonNull(person.getPassword(), "Password cannot be null");
            Objects.requireNonNull(person.getName(), "Name cannot be null");
            Objects.requireNonNull(person.getCompanyEmail(), "Company email cannot be null");
            Objects.requireNonNull(person.getPersonalEmail(), "Personal email cannot be null");
            Objects.requireNonNull(person.getCity(), "City cannot be null");
            Objects.requireNonNull(person.getCreatedDate(), "Creation date cannot be null");
        } catch (NullPointerException nullPointerException) {
            throw new UnprocessableEntityException(nullPointerException.getMessage());
        }
        if (person.getUser().length() > 10) {
            throw new UnprocessableEntityException("User length cannot exceed 10 characters");
        }
        if (person.getUser().length() < 6) {
            throw new UnprocessableEntityException("User length cannot be less than 6 characters");
        }
    }

    @Override
    public Person searchById(int id) {
        log.info("Searching user with given id {}", id);
        return personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person with id " + id + " not found"));
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
        personRepository.save(person);
    }

    @Override
    public void deleteById(int id) {
        log.info("Deleting user with id {}", id);
        this.searchById(id);
        personRepository.deleteById(id);
    }

    @Override
    public void updateById(int id, Person person) {
        log.info("Saving user with data {}", person);
        Person personDB = this.searchById(id);

        validatePersonData(person);

        person.setId(id);
        person.setCreatedDate(personDB.getCreatedDate());
        person.setActive(person.isActive());
        personRepository.save(person);
    }

    @Override
    public List<Person> searchByFields(String user, String name, String surname, LocalDate creationDate, String orderBy) {
        log.info("Searching person by fields: user {}, name {}, surname {}, creationDate {}, orderBy {}", user, name, surname, creationDate, orderBy);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> query = criteriaBuilder.createQuery(Person.class);
        Root<Person> root = query.from(Person.class);

        List<Predicate> predicates = new ArrayList<>();
        if(user != null) {
            predicates.add(criteriaBuilder.like(root.get("user"), user));
        }

        if(name != null) {
            predicates.add(criteriaBuilder.like(root.get("name"), name));
        }

        if(surname != null) {
            predicates.add(criteriaBuilder.like(root.get("surname"), surname));
        }

        if(creationDate != null) {
            predicates.add(criteriaBuilder.greaterThan(root.get("createdDate"), creationDate));
        }

        query.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
        if(Objects.equals(orderBy, "user")) {
            query.orderBy(criteriaBuilder.asc(root.get("user")));
        }
        if(Objects.equals(orderBy, "name")) {
            query.orderBy(criteriaBuilder.asc(root.get("name")));
        }
        return entityManager.createQuery(query)
                .getResultList()
                .stream()
                .toList();
    }

    @Override
    public Page<Person> searchAllWithPagination(int offset, int pageSize) {
        log.info("Searching all users with pagination: offset {} & pageSize {}", offset, pageSize);
        return personRepository.findAll(PageRequest.of(offset, pageSize));
    }
}
