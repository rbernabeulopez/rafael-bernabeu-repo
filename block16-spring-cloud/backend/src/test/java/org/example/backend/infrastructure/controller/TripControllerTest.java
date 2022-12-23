package org.example.backend.infrastructure.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backend.domain.entity.Client;
import org.example.backend.domain.entity.Trip;
import org.example.backend.domain.repository.ClientRepository;
import org.example.backend.domain.repository.TripRepository;
import org.example.backend.infrastructure.controller.dto.TripInputDto;
import org.example.backend.infrastructure.mapper.TripMapper;
import org.example.openfeignclients.backend.dto.TripOutputDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TripControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private ClientRepository clientRepository;

    private Trip savedTrip;

    private Client savedClient;

    @BeforeEach
    void setUp() {
        savedTrip =
            tripRepository.save(Trip.builder()
                .origin("Alicante").destination("Madrid").departureDate(LocalDate.EPOCH)
                .arrivalDate(LocalDate.now()).status("OK").passengers(new ArrayList<>()).build());

        savedClient =
            clientRepository.save(Client.builder()
                .name("Rafa").surname("Barnabe").age(30).email("rafa@gmail.com")
                .phoneNumber(12345678).build());
    }

    @AfterEach
    void tearDown() {
        savedTrip.setPassengers(new ArrayList<>());

        tripRepository.save(savedTrip);

        clientRepository.deleteAll();
        tripRepository.deleteAll();
    }

    @Test
    void saveReturnsCreated() throws Exception {
        TripInputDto tripInputDto = TripInputDto.builder()
            .origin("Barcelona").destination("Palma").departureDate(LocalDate.now())
            .arrivalDate(LocalDate.now().plusDays(1)).status("OK").build();

        mockMvc.perform(post("/trip")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tripInputDto))
        ).andExpect(status().isCreated());

        int numTrips = tripRepository.findAll().size();
        assertThat(numTrips).isEqualTo(2);
    }

    @Test
    void deleteReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/trip/" + savedTrip.getId())).andExpect(status().isNoContent());

        int numTrips = tripRepository.findAll().size();
        assertThat(numTrips).isZero();
    }

    @Test
    void updateReturnsOK() throws Exception {
        TripInputDto tripInputDto = TripInputDto.builder()
            .origin("Barcelona").destination("Palma").departureDate(LocalDate.now())
            .arrivalDate(LocalDate.now().plusDays(1)).status("OK").build();

        Trip trip = TripMapper.INSTANCE.tripInputDtoToTrip(tripInputDto);

        mockMvc.perform(put("/trip/" + savedTrip.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(tripInputDto))
        ).andExpect(status().isOk());

        Optional<Trip> tripOptional = tripRepository.findById(savedTrip.getId());
        trip.setId(savedTrip.getId());
        trip.setPassengers(new ArrayList<>());
        assertThat(tripOptional).contains(trip);
    }

    @Test
    void getAllReturnsOK() throws Exception {
        MvcResult mvcResult =
            mockMvc.perform(get("/trip")).andExpect(status().isOk()).andReturn();

        List<TripOutputDto> tripOutputDtos =
            objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(tripOutputDtos.size()).isOne();
        assertThat(tripOutputDtos.get(0)).isEqualTo(TripMapper.INSTANCE.tripToTripOutputDto(savedTrip));
    }

    @Test
    void getByIdReturnsOK() throws Exception {
        MvcResult mvcResult =
            mockMvc.perform(get("/trip/" + savedTrip.getId())).andExpect(status().isOk()).andReturn();

        TripOutputDto tripOutputDto =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TripOutputDto.class);
        assertThat(tripOutputDto).isEqualTo(TripMapper.INSTANCE.tripToTripOutputDto(savedTrip));
    }

    @Test
    void addPassenger() throws Exception {
        mockMvc.perform(post("/trip/addPassenger/" + savedTrip.getId() + "/" + savedClient.getId()))
            .andExpect(status().isOk());

        Optional<Trip> trip = tripRepository.findById(savedTrip.getId());
        assertThat(trip).isPresent();
        List<Client> clients = trip.get().getPassengers();
        assertThat(clients.size()).isOne();
        assertThat(clients.get(0)).isEqualTo(savedClient);
    }

    @Test
    void countPassengersReturnsOK() throws Exception {
        MvcResult mvcResult =
            mockMvc.perform(get("/passenger/count/" + savedTrip.getId()))
                .andExpect(status().isOk()).andReturn();

        int actualNumPassengers = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Integer.class);
        assertThat(actualNumPassengers).isZero();
    }

    @Test
    void updateStatusReturnsOK() throws Exception {
        String expectedStatus = "FINISHED";

        mockMvc.perform(put("/trip/" + savedTrip.getId() + "/" + expectedStatus))
            .andExpect(status().isOk());

        Optional<Trip> trip = tripRepository.findById(savedTrip.getId());
        assertThat(trip).isPresent();
        assertThat(trip.get().getStatus()).isEqualTo(expectedStatus);
    }

    @Test
    void getStatusReturnsOK() throws Exception {
        MvcResult mvcResult =
            mockMvc.perform(get("/trip/verify/" + savedTrip.getId()))
                .andExpect(status().isOk()).andReturn();

        String actualStatus = mvcResult.getResponse().getContentAsString();
        assertThat(actualStatus).isEqualTo(savedTrip.getStatus());
    }
}