package org.example.backend.application;

import org.example.backend.domain.entity.Client;

import java.util.List;

public interface ClientService {
    void saveClient(Client client);

    void removeClientById(long id);

    void updateClientById(long id, Client client);

    Client retrieveClientById(long id);

    List<Client> retrieveAllClients();
}
