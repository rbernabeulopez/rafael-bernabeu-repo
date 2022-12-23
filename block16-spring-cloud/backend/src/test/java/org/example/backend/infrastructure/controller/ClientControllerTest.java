package org.example.backend.infrastructure.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backend.domain.entity.Client;
import org.example.backend.domain.repository.ClientRepository;
import org.example.backend.infrastructure.controller.dto.ClientInputDto;
import org.example.backend.infrastructure.exceptionhandler.CustomError;
import org.example.backend.infrastructure.mapper.ClientMapper;
import org.example.openfeignclients.backend.dto.ClientOutputDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;

    private Client savedClient;

    @BeforeEach
    void setUp() {
        savedClient =
            clientRepository.save(Client.builder()
                    .name("Rafa").surname("Barnabe").age(30).email("rafa@gmail.com").phoneNumber(123456789)
                    .build());
    }

    @AfterEach
    void tearDown() {
        clientRepository.deleteAll();
    }

    @Test
    void saveReturnsCreated() throws Exception {
        ClientInputDto clientInputDto = ClientInputDto.builder()
                .name("Chuchi").surname("Professor").age(30).email("chuchi@gmail.com").phoneNumber(123456789)
                .build();

        mockMvc.perform(post("/client")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(clientInputDto))
        ).andExpect(status().isCreated());

        int numClients = clientRepository.findAll().size();
        assertThat(numClients).isEqualTo(2);
    }

    @Test
    void saveReturnsUnprocessableEntity() throws Exception {
        ClientInputDto clientInputDto = ClientInputDto.builder()
                .name("Chuchi").surname(null).age(30).email("chuchi@gmail.com").phoneNumber(123456789)
                .build();

        MvcResult mvcResult =
            mockMvc.perform(post("/client")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(clientInputDto))
            ).andExpect(status().isUnprocessableEntity()).andReturn();

        CustomError actualError =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CustomError.class);
        assertThat(actualError.getHttpCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
        assertThat(actualError.getMessage()).isEqualTo("Surname cannot be null");
    }

    @Test
    void deleteReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/client/" + savedClient.getId())).andExpect(status().isNoContent());

        int numClients = clientRepository.findAll().size();
        assertThat(numClients).isZero();
    }

    @Test
    void updateReturnsOK() throws Exception {
        ClientInputDto clientInputDto = ClientInputDto.builder()
                .name("Chuchi").surname("Professor").age(30).email("chuchi@gmail.com").phoneNumber(123456789)
                .build();
        Client client = ClientMapper.INSTANCE.clientInputDtoToClient(clientInputDto);

        mockMvc.perform(put("/client/" + savedClient.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(clientInputDto))
        ).andExpect(status().isOk());

        Optional<Client> clientOptional = clientRepository.findById(savedClient.getId());
        client.setId(savedClient.getId());
        assertThat(clientOptional).contains(client);
    }

    @Test
    void getAllReturnsOK() throws Exception {
        MvcResult mvcResult =
            mockMvc.perform(get("/client")).andExpect(status().isOk()).andReturn();

        List<ClientOutputDto> clientOutputDtos =
            objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(clientOutputDtos.size()).isOne();
        assertThat(clientOutputDtos.get(0)).isEqualTo(ClientMapper.INSTANCE.clientToClientOutputDto(savedClient));
    }

    @Test
    void getByIdReturnsOK() throws Exception {
        MvcResult mvcResult =
            mockMvc.perform(get("/client/" + savedClient.getId())).andExpect(status().isOk()).andReturn();

        ClientOutputDto clientOutputDto =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ClientOutputDto.class);
        assertThat(clientOutputDto).isEqualTo(ClientMapper.INSTANCE.clientToClientOutputDto(savedClient));
    }

    @Test
    void getByIdReturnsNotFound() throws Exception {
        long id = savedClient.getId() + 1;
        MvcResult mvcResult =
                mockMvc.perform(get("/client/" + id))
                    .andExpect(status().isNotFound()).andReturn();

        CustomError actualError =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CustomError.class);
        assertThat(actualError.getHttpCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(actualError.getMessage()).isEqualTo("Client with id '"+ id +"' not found");
    }
}