package com.example.block7crudvalidation;

import com.example.block7crudvalidation.domain.entity.Person;
import com.example.block7crudvalidation.domain.repository.PersonRepository;
import com.example.block7crudvalidation.infrastructure.controller.dto.input.PersonInputDto;
import com.example.block7crudvalidation.infrastructure.controller.dto.output.PersonFullOutputDto;
import com.example.block7crudvalidation.infrastructure.controller.dto.output.PersonOutputDto;
import com.example.block7crudvalidation.infrastructure.mapper.PersonMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PersonTest {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        personRepository.deleteAll();
    }

    Person savePerson() {
        Person person = Person.builder().name("Chuchi").password("1234").personalEmail("chuchi@mail.com").active(true)
                .city("Logrono").companyEmail("chuchi@bosonit.com").createdDate(LocalDate.EPOCH)
                .imageUrl("https://image.jpg").user("chuchi").surname("Profe")
                .build();
        return personRepository.save(person);
    }

    @Test
    void savePersonOK() throws Exception {
        // GIVEN
        PersonInputDto personInputDto = new PersonInputDto();
        personInputDto.setName("Rafa");
        personInputDto.setPassword("5678");
        personInputDto.setPersonalEmail("rafa@gmail.com");
        personInputDto.setActive(false);
        personInputDto.setCity("Alicante");
        personInputDto.setCompanyEmail("rafa@bosonit.com");
        personInputDto.setCreatedDate(LocalDate.EPOCH);
        personInputDto.setImageUrl("https://image2.jpg");
        personInputDto.setUser("rafael");

        // WHEN
        mockMvc.perform(post("/addperson")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personInputDto))
        ).andExpect(status().isCreated());

        // THEN
        assertThat(personRepository.findAll().size()).isOne();
        Person actualPerson = personRepository.findAll().get(0);

    }

    @Test
    void searchAllOK() throws Exception {
        // GIVEN
        Person person = savePerson();

        // WHEN
        MvcResult mvcResult = mockMvc.perform(get("/getall")).andExpect(status().isOk()).andReturn();

        // THEN
        List<PersonOutputDto> persons =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});

        assertThat(persons.size()).isOne();
        assertThat(persons.get(0)).isEqualTo(PersonMapper.INSTANCE.personToPersonOutputDto(person));
    }

    @Test
    void searchByIdSimpleOK() throws Exception {
        // GIVEN
        Person person = savePerson();

        // WHEN
        MvcResult mvcResult = mockMvc.perform(get("/person/" + person.getId()))
                .andExpect(status().isOk()).andReturn();

        // THEN
        PersonOutputDto actualPerson = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
            PersonOutputDto.class);

        assertThat(actualPerson).isEqualTo(PersonMapper.INSTANCE.personToPersonOutputDto(person));
    }
    
    @Test
    void searchByIdFullOK() throws Exception {
        // GIVEN
        Person person = savePerson();

        // WHEN
        MvcResult mvcResult = mockMvc.perform(get("/person/" + person.getId())
            .param("ouputType", "full")
        ).andExpect(status().isOk()).andReturn();

        // THEN
        PersonFullOutputDto actualPerson = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                PersonFullOutputDto.class);

        assertThat(actualPerson).isEqualTo(PersonMapper.INSTANCE.personToPersonFullOutputDto(person));

    }

    @Test
    void searchByIdInvalidOutputType() throws Exception {
        // GIVEN
        Person person = savePerson();

        // WHEN
        MvcResult mvcResult = mockMvc.perform(get("/person/" + person.getId())
                .param("ouputType", "invalid")
        ).andExpect(status().isOk()).andReturn();

        // THEN
        PersonOutputDto actualPerson = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                PersonOutputDto.class);

        assertThat(actualPerson).isEqualTo(new PersonOutputDto());
    }

    @Test
    void searchByNameSimpleOK() throws Exception {
        // GIVEN
        Person person = savePerson();

        // WHEN
        MvcResult mvcResult = mockMvc.perform(get("/person/name/")
            .param("name", person.getName())
        ).andExpect(status().isOk()).andReturn();

        // THEN
        List<PersonOutputDto> actualPerson = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {});

        assertThat(actualPerson.size()).isOne();
        assertThat(actualPerson.get(0)).isEqualTo(PersonMapper.INSTANCE.personToPersonOutputDto(person));
    }

    @Test
    void searchAllWithPaginationOK() throws Exception {
        // GIVEN
        Person person = savePerson();

        // WHEN
        MvcResult mvcResult = mockMvc.perform(get("/person/paginated/")
            .param("offset", "0")
        ).andExpect(status().isOk()).andReturn();

        // THEN
        PersonOutputDto personOutputDto = PersonMapper.INSTANCE.personToPersonOutputDto(person);
        Page<PersonOutputDto> expectedPersonPage = new PageImpl<>(List.of(personOutputDto));
        String expectedJSONPerson = objectMapper.writeValueAsString(expectedPersonPage.getContent());
        String actualJSONPerson = mvcResult.getResponse().getContentAsString();

        assertThat(actualJSONPerson).contains(expectedJSONPerson);
    }

    @Test
    void searchByAllFieldsOK() throws Exception {
        // GIVEN
        Person person = savePerson();

        // WHEN
        MvcResult mvcResult = mockMvc.perform(get("/person/fields/")
            .param("user", person.getUser())
            .param("name", person.getName())
            .param("surname", person.getSurname())
            .param("creationDate", LocalDate.MAX.toString())
        ).andExpect(status().isOk()).andReturn();

        // THEN
        List<PersonOutputDto> actualPerson = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {});

        assertThat(actualPerson.size()).isOne();
        assertThat(actualPerson.get(0)).isEqualTo(PersonMapper.INSTANCE.personToPersonOutputDto(person));
    }

    @Test
    void searchByNoFieldsOK() throws Exception {
        // GIVEN
        Person person = savePerson();

        // WHEN
        MvcResult mvcResult = mockMvc.perform(get("/person/fields/")
                .param("orderBy", "name")
        ).andExpect(status().isOk()).andReturn();

        // THEN
        List<PersonOutputDto> actualPerson = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {});

        assertThat(actualPerson.size()).isOne();
        assertThat(actualPerson.get(0)).isEqualTo(PersonMapper.INSTANCE.personToPersonOutputDto(person));
    }

    @Test
    void deleteByIdOK() throws Exception {
        // GIVEN
        Person person = savePerson();

        // WHEN
        mockMvc.perform(delete("/person/" + person.getId()))
                .andExpect(status().isNoContent());

        // THEN
        assertThat(personRepository.findAll()).isEmpty();
    }


    @Test
    void updatePersonOK() throws Exception {
        // GIVEN
        Person person = savePerson();
        PersonInputDto personInputDto = new PersonInputDto();
        personInputDto.setName("Rafa");
        personInputDto.setPassword("5678");
        personInputDto.setPersonalEmail("rafa@gmail.com");
        personInputDto.setActive(false);
        personInputDto.setCity("Alicante");
        personInputDto.setCompanyEmail("rafa@bosonit.com");
        personInputDto.setCreatedDate(LocalDate.EPOCH);
        personInputDto.setImageUrl("https://image2.jpg");
        personInputDto.setUser("rafael");

        // WHEN
        mockMvc.perform(put("/person/" + person.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personInputDto))
        ).andExpect(status().isOk());

        // THEN
        Optional<Person> optionalActualPerson = personRepository.findById(person.getId());
        assertThat(optionalActualPerson).isPresent();

        Person actualPerson = optionalActualPerson.get();
        Person expectedPerson = PersonMapper.INSTANCE.personInputDtoToPerson(personInputDto);
        expectedPerson.setId(person.getId());
        assertThat(actualPerson).isEqualTo(expectedPerson);
    }
}
