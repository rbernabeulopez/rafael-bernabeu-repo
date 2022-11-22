package com.example.block6simplecontrollers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void saludar() throws Exception {
        MvcResult mvcResult =
        mockMvc.perform(get("/user/rafa")).andExpect(status().isOk()).andReturn();
        String realResponse = mvcResult.getResponse().getContentAsString();
        assertEquals("Hola rafa", realResponse);
    }

    @Test
    void addEdad() throws Exception {
        Persona persona = Persona.builder().nombre("Chuchi").edad("20").ciudad("Madrid").build();
        Persona personaEsperada = Persona.builder().nombre("Chuchi").edad("21").ciudad("Madrid").build();
        MvcResult mvcResult =
                mockMvc.perform(post("/useradd")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(persona)))
                    .andExpect(status().isOk()).andReturn();

        Persona personaReal = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Persona.class);
        assertEquals(personaEsperada, personaReal);
    }
}