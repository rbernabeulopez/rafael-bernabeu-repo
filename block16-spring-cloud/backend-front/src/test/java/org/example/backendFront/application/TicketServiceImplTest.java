package org.example.backendFront.application;

import feign.FeignException;
import org.example.backendFront.domain.entity.Ticket;
import org.example.backendFront.domain.exception.RestException;
import org.example.backendFront.domain.repository.TicketRepository;
import org.example.openfeignclients.backend.ClientFeign;
import org.example.openfeignclients.backend.TripFeign;
import org.example.openfeignclients.backend.dto.ClientOutputDto;
import org.example.openfeignclients.backend.dto.TripOutputDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {

    @InjectMocks
    private TicketServiceImpl ticketService;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private ClientFeign clientFeign;

    @Mock
    private TripFeign tripFeign;

    @Test
    void generateTicketOK() {
        // GIVEN
        ClientOutputDto expectedClient = new ClientOutputDto();
        expectedClient.setId(1);
        expectedClient.setName("Rafa");
        expectedClient.setSurname("Barnabe");
        expectedClient.setAge(30);
        expectedClient.setEmail("rafa@gmail.com");
        expectedClient.setPhoneNumber(123456789);

        TripOutputDto expectedTrip = new TripOutputDto();
        expectedTrip.setId(1);
        expectedTrip.setOrigin("Alicante");
        expectedTrip.setDestination("Madrid");
        expectedTrip.setDepartureDate(LocalDate.EPOCH);
        expectedTrip.setArrivalDate(LocalDate.now());
        expectedTrip.setStatus("OK");

        Ticket ticket = Ticket.builder()
            .passengerId(expectedClient.getId())
            .passengerName(expectedClient.getName())
            .passengerLastname(expectedClient.getSurname())
            .passengerEmail(expectedClient.getEmail())
            .tripOrigin(expectedTrip.getOrigin())
            .tripDestination(expectedTrip.getDestination())
            .departureDate(expectedTrip.getDepartureDate())
            .arrivalDate(expectedTrip.getArrivalDate())
            .build();

        when(clientFeign.getById(expectedClient.getId())).thenReturn(expectedClient);
        when(tripFeign.getById(expectedTrip.getId())).thenReturn(expectedTrip);
        when(ticketRepository.save(ticket)).thenReturn(ticket);

        // WHEN

        assertDoesNotThrow(() -> ticketService.generateTicket(expectedClient.getId(), expectedTrip.getId()));

        // THEN
        verify(clientFeign, times(1)).getById(expectedClient.getId());
        verify(tripFeign, times(1)).getById(expectedTrip.getId());
        verify(ticketRepository, times(1)).save(ticket);
    }

    @Test
    void generateTicketThrowsRestException() {
        // GIVEN
        int clientId = 2;
        when(clientFeign.getById(clientId)).thenThrow(FeignException.class);

        // WHEN
        RestException actualException =
            assertThrows(RestException.class, () -> ticketService.generateTicket(clientId, 1));

        // THEN
        assertThat(actualException.getMessage()).isEqualTo("Failed request to backend service");
        verify(clientFeign, times(1)).getById(clientId);
        verify(tripFeign, never()).getById(anyLong());
        verify(ticketRepository, never()).save(any());
    }
}