package org.example.backendFront.application;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backendFront.domain.entity.Ticket;
import org.example.backendFront.domain.exception.RestException;
import org.example.backendFront.domain.repository.TicketRepository;
import org.example.openfeignclients.backend.ClientFeign;
import org.example.openfeignclients.backend.dto.ClientOutputDto;
import org.example.openfeignclients.backend.TripFeign;
import org.example.openfeignclients.backend.dto.TripOutputDto;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService {

    private TicketRepository ticketRepository;

    private ClientFeign clientFeign;

    private TripFeign tripFeign;

    @Override
    public void generateTicket(long clientId, long tripId) {
        ClientOutputDto clientOutputDto;
        TripOutputDto tripOutputDto;
        try {
            clientOutputDto = clientFeign.getById(clientId);
            tripOutputDto = tripFeign.getById(tripId);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new RestException("Failed request to backend service");
        }

        log.info("Saving ticket for client {} in trip {}", clientOutputDto, tripOutputDto);

        Ticket ticket = Ticket.builder()
            .passengerId(clientOutputDto.getId())
            .passengerName(clientOutputDto.getName())
            .passengerLastname(clientOutputDto.getSurname())
            .passengerEmail(clientOutputDto.getEmail())
            .tripOrigin(tripOutputDto.getOrigin())
            .tripDestination(tripOutputDto.getDestination())
            .departureDate(tripOutputDto.getDepartureDate())
            .arrivalDate(tripOutputDto.getArrivalDate())
            .build();

        ticketRepository.save(ticket);
    }
}
