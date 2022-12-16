package com.example.block7crudvalidation.application;

import com.example.block7crudvalidation.domain.entity.Person;
import com.example.block7crudvalidation.domain.exception.EntityNotFoundException;
import com.example.block7crudvalidation.domain.exception.UnprocessableEntityException;
import com.example.block7crudvalidation.domain.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @InjectMocks
    private PersonServiceImpl personService;

    @Mock
    private PersonRepository personRepository;

    @Test
    void searchByIdThatExists() {
        // GIVEN
        Person expectedPerson = Person.builder()
            .id(1)
            .name("Chuchi")
            .password("1234")
            .build();
        given(personRepository.findById(expectedPerson.getId())).willReturn(Optional.of(expectedPerson));

        // WHEN
        Person actualPerson =
                assertDoesNotThrow(() -> personService.searchById(expectedPerson.getId()));

        // THEN
        assertEquals(expectedPerson, actualPerson);
        verify(personRepository, times(1)).findById(expectedPerson.getId());
    }

    @Test
    void searchByIdThatDoesNotExist() {
        // GIVEN
        int id = 1;
        EntityNotFoundException expectedException = new EntityNotFoundException("Person with id " + id + " not found");
        given(personRepository.findById(id)).willReturn(Optional.empty());

        // WHEN
        EntityNotFoundException actualException =
            assertThrows(EntityNotFoundException.class, () -> personService.searchById(id));

        // THEN
        assertEquals(expectedException, actualException);
        verify(personRepository, times(1)).findById(id);
    }


    @Test
    void searchByName() {
        // GIVEN
        String name = "Chuchi";
        List<Person> expectedPersons = List.of(
                Person.builder().id(1).name(name).password("1234").build(),
                Person.builder().id(1).name(name).password("5678").build()
        );
        given(personRepository.findByName(name)).willReturn(expectedPersons);

        // WHEN
        List<Person> actualPersons = personService.searchByName(name);

        // THEN
        assertEquals(expectedPersons, actualPersons);
        verify(personRepository, times(1)).findByName(name);
    }

    @Test
    void searchAll() {
        // GIVEN
        List<Person> expectedPersons = List.of(
                Person.builder().id(1).name("Chuchi").password("1234").build(),
                Person.builder().id(1).name("Rafa").password("5678").build()
        );
        given(personRepository.findAll()).willReturn(expectedPersons);

        // WHEN
        List<Person> actualPersons = personService.searchAll();

        // THEN
        assertEquals(expectedPersons, actualPersons);
        verify(personRepository, times(1)).findAll();
    }

    @Test
    void savePersonOK() {
        // GIVEN
        Person expectedPerson = Person.builder()
            .name("Chuchi").password("1234").personalEmail("chuchi@mail.com").active(true).city("Logroño")
            .companyEmail("chuchi@bosonit.com").createdDate(LocalDate.EPOCH).imageUrl("https://image.jpg")
            .user("chuchi")
            .build();
        given(personRepository.save(expectedPerson)).willReturn(expectedPerson);

        // WHEN
        assertDoesNotThrow(() -> personService.save(expectedPerson));

        // THEN
        verify(personRepository, times(1)).save(expectedPerson);
    }

    @Test
    void savePersonWithNullCompanyEmail() {
        // GIVEN
        UnprocessableEntityException expectedException = new UnprocessableEntityException("Company email cannot be null");
        Person expectedPerson = Person.builder()
                .name("Chuchi").password("1234").personalEmail("chuchi@mail.com").active(true).city("Logroño")
                .companyEmail(null).createdDate(LocalDate.EPOCH).imageUrl("https://image.jpg")
                .user("chuchi")
                .build();

        // WHEN
        UnprocessableEntityException actualException =
            assertThrows(UnprocessableEntityException.class, () -> personService.save(expectedPerson));

        // THEN
        assertEquals(expectedException, actualException);
        verify(personRepository, never()).save(any());
    }

    @Test
    void savePersonWithUserLengthGreatherThan10() {
        // GIVEN
        UnprocessableEntityException expectedException =
                new UnprocessableEntityException("User length cannot exceed 10 characters");
        Person expectedPerson = Person.builder()
                .name("Chuchi").password("1234").personalEmail("chuchi@mail.com").active(true).city("Logroño")
                .companyEmail("chuchi@bosonit.com").createdDate(LocalDate.EPOCH).imageUrl("https://image.jpg")
                .user("chuchi12345")
                .build();

        // WHEN
        UnprocessableEntityException actualException =
                assertThrows(UnprocessableEntityException.class, () -> personService.save(expectedPerson));

        // THEN
        assertEquals(expectedException, actualException);
        verify(personRepository, never()).save(any());
    }

    @Test
    void savePersonWithUserLengthLowerThan6() {
        // GIVEN
        UnprocessableEntityException expectedException =
                new UnprocessableEntityException("User length cannot be less than 6 characters");
        Person expectedPerson = Person.builder()
                .name("Chuchi").password("1234").personalEmail("chuchi@mail.com").active(true).city("Logroño")
                .companyEmail("chuchi@bosonit.com").createdDate(LocalDate.EPOCH).imageUrl("https://image.jpg")
                .user("pepe")
                .build();

        // WHEN
        UnprocessableEntityException actualException =
                assertThrows(UnprocessableEntityException.class, () -> personService.save(expectedPerson));

        // THEN
        assertEquals(expectedException, actualException);
        verify(personRepository, never()).save(any());
    }

    @Test
    void deleteById() {
        // GIVEN
        int id = 1;
        Person expectedPerson = Person.builder()
                .id(1).name("Chuchi").password("1234").personalEmail("chuchi@mail.com").active(true).city("Logroño")
                .companyEmail("chuchi@bosonit.com").createdDate(LocalDate.EPOCH).imageUrl("https://image.jpg")
                .user("chuchi")
                .build();
        given(personRepository.findById(id)).willReturn(Optional.of(expectedPerson));
        doNothing().when(personRepository).deleteById(id);

        // WHEN
        assertDoesNotThrow(() -> personService.deleteById(id));

        // THEN
        verify(personRepository, times(1)).findById(id);
        verify(personRepository, times(1)).deleteById(id);
    }

    @Test
    void updateById() {
        // GIVEN
        int id = 1;
        Person expectedPerson = Person.builder()
                .name("Chuchi").password("1234").personalEmail("chuchi@mail.com").active(true).city("Logroño")
                .companyEmail("chuchi@bosonit.com").createdDate(LocalDate.EPOCH).imageUrl("https://image.jpg")
                .user("chuchi")
                .build();
        expectedPerson.setId(id);
        given(personRepository.findById(id)).willReturn(Optional.of(expectedPerson));
        given(personRepository.save(expectedPerson)).willReturn(expectedPerson);

        // WHEN
        expectedPerson.setId(0);
        assertDoesNotThrow(() -> personService.updateById(id, expectedPerson));

        // THEN
        expectedPerson.setId(id);
        verify(personRepository, times(1)).findById(id);
        verify(personRepository, times(1)).save(expectedPerson);
    }

    @Test
    void searchByFields() {
    }

    @Test
    void searchAllWithPagination() {
        // GIVEN
        int offset = 0;
        int pageSize = 10;
        Page<Person> expectedPersons = new PageImpl<>(List.of(
                Person.builder().id(1).name("Chuchi").password("1234").build(),
                Person.builder().id(1).name("Rafa").password("5678").build()
        ));
        given(personRepository.findAll(PageRequest.of(offset, pageSize))).willReturn(expectedPersons);

        // WHEN
        Page<Person> actualPersons = personService.searchAllWithPagination(offset, pageSize);

        // THEN
        assertEquals(expectedPersons, actualPersons);
        verify(personRepository, times(1)).findAll(PageRequest.of(offset, pageSize));
    }
}