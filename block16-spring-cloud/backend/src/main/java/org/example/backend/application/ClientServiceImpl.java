package org.example.backend.application;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.example.backend.domain.entity.Client;
import org.example.backend.domain.exception.EntityNotFoundException;
import org.example.backend.domain.exception.InvalidDataException;
import org.example.backend.domain.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;

    private void checkClientData(Client client) {
        if(!EmailValidator.getInstance().isValid(client.getEmail())) {
            throw new InvalidDataException("Invalid email format '" + client.getEmail() + "'");
        }

        if(Integer.toString(client.getPhoneNumber()).length() != 9) {
            throw new InvalidDataException("Invalid phone number format '" + client.getPhoneNumber() + "'");
        }

        try {
            Objects.requireNonNull(client.getName(), "Name cannot be null");
            Objects.requireNonNull(client.getSurname(), "Surname cannot be null");
        } catch (NullPointerException nullPointerException) {
            throw new InvalidDataException(nullPointerException.getMessage());
        }

        if(client.getId() == 0 && clientRepository.findByEmail(client.getEmail()).isPresent()) {
            throw new InvalidDataException("A client already has email '" + client.getEmail() + "'");
        }
    }

    @Override
    public void saveClient(Client client) {
        log.info("Creating client with credentials {}", client);
        checkClientData(client);
        clientRepository.save(client);
    }

    @Override
    public void removeClientById(long id) {
        log.info("Removing client with id {}", id);
        retrieveClientById(id);
        clientRepository.deleteById(id);
    }

    @Override
    public void updateClientById(long id, Client client) {
        log.info("Updating client with id {} with data {}", id, client);
        checkClientData(client);
        retrieveClientById(id);

        client.setId(id);
        clientRepository.save(client);
    }

    @Override
    public Client retrieveClientById(long id) {
        log.info("Retrieving client with id {}", id);
        return clientRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Client with id '"+ id +"' not found")
        );
    }

    @Override
    public List<Client> retrieveAllClients() {
        log.info("Retrieving all clients");
        return clientRepository.findAll();
    }
}
