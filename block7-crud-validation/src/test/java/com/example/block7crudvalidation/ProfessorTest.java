package com.example.block7crudvalidation;

import com.example.block7crudvalidation.domain.entity.Person;
import com.example.block7crudvalidation.domain.entity.Professor;
import com.example.block7crudvalidation.domain.exception.EntityNotFoundException;
import com.example.block7crudvalidation.domain.repository.PersonRepository;
import com.example.block7crudvalidation.domain.repository.ProfessorRepository;
import com.example.block7crudvalidation.infrastructure.controller.dto.input.ProfessorInputDto;
import com.example.block7crudvalidation.infrastructure.controller.dto.output.ProfessorFullOutputDto;
import com.example.block7crudvalidation.infrastructure.controller.dto.output.ProfessorOutputDto;
import com.example.block7crudvalidation.infrastructure.mapper.PersonMapper;
import com.example.block7crudvalidation.infrastructure.mapper.ProfessorMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProfessorTest {
    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        personRepository.deleteAll();
        professorRepository.deleteAll();
    }

    Person savePerson() {
        Person person = Person.builder().name("Chuchi").password("1234").personalEmail("chuchi@mail.com").active(true)
                .city("Logroño").companyEmail("chuchi@bosonit.com").createdDate(LocalDate.EPOCH)
                .imageUrl("https://image.jpg").user("chuchi")
                .build();
        return personRepository.save(person);
    }

    Pair<Professor, Person> saveProfessor() {
        Person person = Person.builder().name("Rafa").password("5678").personalEmail("rafa@mail.com").active(true)
                .city("Alicante").companyEmail("rafa@bosonit.com").createdDate(LocalDate.EPOCH)
                .imageUrl("https://image2.jpg").user("rafa")
                .build();

        Professor professor = Professor.builder()
                .branch("Spring Boot")
                .comments("Comment 1")
                .students(new ArrayList<>())
                .build();

        person.setProfessor(professor);
        person = personRepository.save(person);

        professor.setPerson(person);
        professor = professorRepository.save(professor);

        return Pair.of(professor, person);
    }

    @Test
    void saveProfessorOK() throws Exception {
        // GIVEN
        Person person = savePerson();
        ProfessorInputDto professorInputDto = new ProfessorInputDto();
        professorInputDto.setBranch("React");
        professorInputDto.setComments("Comment 2");
        professorInputDto.setPersonId(person.getId());

        // WHEN
        mockMvc.perform(post("/professor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(professorInputDto))
        ).andExpect(status().isCreated());

        // THEN
        assertThat(professorRepository.findAll().size()).isOne();
        Professor actualProfessor = professorRepository.findAll().get(0);
        assertThat(professorInputDto.getBranch()).isEqualTo(actualProfessor.getBranch());
        assertThat(professorInputDto.getComments()).isEqualTo(actualProfessor.getComments());
    }

    @Test
    void saveProfessorWithNullBranch() throws Exception {
        // GIVEN
        Person person = savePerson();
        ProfessorInputDto professorInputDto = new ProfessorInputDto();
        professorInputDto.setBranch(null);
        professorInputDto.setComments("Comment 2");
        professorInputDto.setPersonId(person.getId());

        // WHEN
        mockMvc.perform(post("/professor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(professorInputDto))
        ).andExpect(status().isUnprocessableEntity()).andReturn();

        // THEN
        assertThat(professorRepository.findAll()).isEmpty();
    }

    @Test
    void saveProfessorWithInvalidPerson() throws Exception {
        // GIVEN
        Pair<Professor, Person> professorPersonPair = saveProfessor();
        ProfessorInputDto professorInputDto = new ProfessorInputDto();
        professorInputDto.setBranch("React");
        professorInputDto.setComments("Comment 2");
        professorInputDto.setPersonId(professorPersonPair.getSecond().getId());

        // WHEN
        mockMvc.perform(post("/professor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(professorInputDto))
        ).andExpect(status().isUnprocessableEntity()).andReturn();

        // THEN
        assertThat(professorRepository.findAll().size()).isOne();
    }

    @Test
    void searchAllOK() throws Exception {
        // GIVEN
        Pair<Professor, Person> professorPersonPair = saveProfessor();

        // WHEN
        MvcResult mvcResult = mockMvc.perform(get("/professor")).andExpect(status().isOk()).andReturn();

        // THEN
        List<ProfessorOutputDto> professors =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});

        assertThat(professors.size()).isOne();
        assertThat(professors.get(0)).isEqualTo(
            ProfessorMapper.INSTANCE.professorToProfessorOutputDto(professorPersonPair.getFirst())
        );
    }

    @Test
    void searchByIdSimpleOK() throws Exception {
        // GIVEN
        Pair<Professor, Person> professorPersonPair = saveProfessor();

        // WHEN
        MvcResult mvcResult = mockMvc.perform(get("/professor/" + professorPersonPair.getFirst().getProfessorId()))
                .andExpect(status().isOk()).andReturn();

        // THEN
        ProfessorOutputDto actualProfessor = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
            ProfessorOutputDto.class);

        assertThat(actualProfessor).isEqualTo(
            ProfessorMapper.INSTANCE.professorToProfessorOutputDto(professorPersonPair.getFirst())
        );
    }

    @Test
    void searchByIdNotFound() throws Exception {
        // GIVEN
        Pair<Professor, Person> professorPersonPair = saveProfessor();

        // WHEN
        MvcResult mvcResult = mockMvc.perform(get("/professor/" + professorPersonPair.getFirst().getProfessorId() + 1))
                .andExpect(status().isNotFound()).andReturn();

        // THEN
        EntityNotFoundException actualException = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                EntityNotFoundException.class);

        assertThat(actualException.getMessage()).isEqualTo(
                "Professor with id " + (professorPersonPair.getFirst().getProfessorId() + 1) + " not found"
        );
    }

    @Test
    void searchByIdFullOK() throws Exception {
        // GIVEN
        Pair<Professor, Person> professorPersonPair = saveProfessor();

        // WHEN
        MvcResult mvcResult = mockMvc.perform(get("/professor/" + professorPersonPair.getFirst().getProfessorId())
            .param("ouputType", "full")
        ).andExpect(status().isOk()).andReturn();

        // THEN
        ProfessorFullOutputDto actualProfessor = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                ProfessorFullOutputDto.class);

        assertThat(actualProfessor).isEqualTo(
                ProfessorMapper.INSTANCE.professorToProfessorFullOutputDto(professorPersonPair.getFirst())
        );
        assertThat(actualProfessor.getPerson()).isEqualTo(
                PersonMapper.INSTANCE.personToPersonOutputDto(professorPersonPair.getSecond())
        );
    }

    @Test
    void searchByIdInvalidOutputType() throws Exception {
        // GIVEN
        Pair<Professor, Person> professorPersonPair = saveProfessor();

        // WHEN
        MvcResult mvcResult = mockMvc.perform(get("/professor/" + professorPersonPair.getFirst().getProfessorId())
                .param("ouputType", "invalid")
        ).andExpect(status().isOk()).andReturn();

        // THEN
        ProfessorOutputDto actualProfessor = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                ProfessorOutputDto.class);

        assertThat(actualProfessor).isEqualTo(new ProfessorOutputDto());
    }

    @Test
    void deleteByIdOK() throws Exception {
        // GIVEN
        Pair<Professor, Person> professorPersonPair = saveProfessor();

        // WHEN
        mockMvc.perform(delete("/professor/" + professorPersonPair.getFirst().getProfessorId()))
                .andExpect(status().isNoContent());

        // THEN
        assertThat(professorRepository.findAll()).isEmpty();
    }


    @Test
    void updateProfessorOK() throws Exception {
        // GIVEN
        Pair<Professor, Person> professorPersonPair = saveProfessor();

        Person person = savePerson();
        ProfessorInputDto professorInputDto = new ProfessorInputDto();
        professorInputDto.setBranch("React");
        professorInputDto.setComments("Comment 2");
        professorInputDto.setPersonId(person.getId());

        // WHEN
        mockMvc.perform(put("/professor/" + professorPersonPair.getFirst().getProfessorId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(professorInputDto))
        ).andExpect(status().isOk());

        // THEN
        Optional<Professor> optionalActualProfessor = professorRepository.findById(professorPersonPair.getFirst().getProfessorId());
        assertThat(optionalActualProfessor).isPresent();

        Professor actualProfessor = optionalActualProfessor.get();
        assertThat(professorInputDto.getBranch()).isEqualTo(actualProfessor.getBranch());
        assertThat(professorInputDto.getComments()).isEqualTo(actualProfessor.getComments());
    }
}
