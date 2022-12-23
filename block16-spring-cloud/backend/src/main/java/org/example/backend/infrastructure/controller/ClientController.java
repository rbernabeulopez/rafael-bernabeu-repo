package org.example.backend.infrastructure.controller;

import lombok.AllArgsConstructor;
import org.example.backend.application.ClientService;
import org.example.backend.domain.entity.Client;
import org.example.backend.infrastructure.controller.dto.ClientInputDto;
import org.example.backend.infrastructure.mapper.ClientMapper;
import org.example.openfeignclients.backend.dto.ClientOutputDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("client")
@AllArgsConstructor
public class ClientController {
    private ClientService clientService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void save(@RequestBody ClientInputDto clientInputDto) {
        Client client = ClientMapper.INSTANCE.clientInputDtoToClient(clientInputDto);
        clientService.saveClient(client);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    void delete(@PathVariable long id) {
        clientService.removeClientById(id);
    }

    @PutMapping("{id}")
    void update(@PathVariable long id, @RequestBody ClientInputDto clientInputDto) {
        Client client = ClientMapper.INSTANCE.clientInputDtoToClient(clientInputDto);
        clientService.updateClientById(id, client);
    }

    @GetMapping
    List<ClientOutputDto> getAll() {
        return clientService.retrieveAllClients()
            .stream().map(ClientMapper.INSTANCE::clientToClientOutputDto).toList();
    }

    @GetMapping("{id}")
    ClientOutputDto getById(@PathVariable long id) {
        return ClientMapper.INSTANCE.clientToClientOutputDto(clientService.retrieveClientById(id));
    }
}
