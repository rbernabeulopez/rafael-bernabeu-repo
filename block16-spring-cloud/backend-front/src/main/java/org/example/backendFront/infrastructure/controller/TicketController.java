package org.example.backendFront.infrastructure.controller;

import lombok.AllArgsConstructor;
import org.example.backendFront.application.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TicketController {
    private TicketService ticketService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("generateTicket/{userId}/{tripId}")
    public void generateTicket(@PathVariable("userId") long clientId, @PathVariable("tripId") long tripId) {
        ticketService.generateTicket(clientId, tripId);
    }
}
