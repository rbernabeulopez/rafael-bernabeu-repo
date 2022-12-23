package org.example.backend.application;


import org.example.backend.domain.entity.Client;
import org.example.backend.domain.exception.EntityNotFoundException;
import org.example.backend.domain.exception.InvalidDataException;
import org.example.backend.domain.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @InjectMocks
    private ClientServiceImpl clientService;

    @Mock
    private ClientRepository clientRepository;


    @Test
    void saveClientOK() {
        // GIVEN
        Client client = Client.builder()
                .name("Rafa").surname("Barnabe").age(30).email("rafa@gmail.com").phoneNumber(123456789)
                .build();
        when(clientRepository.findByEmail(client.getEmail())).thenReturn(Optional.empty());
        when(clientRepository.save(client)).thenReturn(client);

        // WHEN
        assertDoesNotThrow(() -> clientService.saveClient(client));

        // THEN
        verify(clientRepository, times(1)).findByEmail(client.getEmail());
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void saveClientWithEmailAlreadyExisting() {
        // GIVEN
        Client client = Client.builder()
                .name("Rafa").surname("Barnabe").age(30).email("rafa@gmail.com").phoneNumber(123456789)
                .build();
        when(clientRepository.findByEmail(client.getEmail())).thenReturn(Optional.of(new Client()));

        // WHEN
        InvalidDataException invalidDataException =
            assertThrows(InvalidDataException.class, () -> clientService.saveClient(client));

        // THEN
        assertThat(invalidDataException.getMessage()).isEqualTo("A client already has email '" + client.getEmail() + "'");
        verify(clientRepository, times(1)).findByEmail(client.getEmail());
        verify(clientRepository, never()).save(any());
    }


    @Test
    void saveClientWithNullName() {
        // GIVEN
        Client client = Client.builder()
                .name(null).surname("Barnabe").age(30).email("rafa@gmail.com").phoneNumber(123456789)
                .build();

        // WHEN
        InvalidDataException invalidDataException =
                assertThrows(InvalidDataException.class, () -> clientService.saveClient(client));

        // THEN
        assertThat(invalidDataException.getMessage()).isEqualTo("Name cannot be null");
        verify(clientRepository, never()).findByEmail(any());
        verify(clientRepository, never()).save(any());
    }

    @Test
    void saveClientWithNullSurname() {
        // GIVEN
        Client client = Client.builder()
                .name("Rafa").surname(null).age(30).email("rafa@gmail.com").phoneNumber(123456789)
                .build();

        // WHEN
        InvalidDataException invalidDataException =
                assertThrows(InvalidDataException.class, () -> clientService.saveClient(client));

        // THEN
        assertThat(invalidDataException.getMessage()).isEqualTo("Surname cannot be null");
        verify(clientRepository, never()).findByEmail(any());
        verify(clientRepository, never()).save(any());
    }

    @Test
    void saveClientWithInvalidPhoneNumber() {
        // GIVEN
        Client client = Client.builder()
                .name("Rafa").surname("Barnabe").age(30).email("rafa@gmail.com").phoneNumber(12345678)
                .build();

        // WHEN
        InvalidDataException invalidDataException =
                assertThrows(InvalidDataException.class, () -> clientService.saveClient(client));

        // THEN
        assertThat(invalidDataException.getMessage()).isEqualTo("Invalid phone number format '" + client.getPhoneNumber() + "'");
        verify(clientRepository, never()).findByEmail(any());
        verify(clientRepository, never()).save(any());
    }


    @Test
    void saveClientWithInvalidEmail() {
        // GIVEN
        Client client = Client.builder()
                .name("Rafa").surname("Barnabe").age(30).email("rafa@gmail").phoneNumber(123456789)
                .build();

        // WHEN
        InvalidDataException invalidDataException =
                assertThrows(InvalidDataException.class, () -> clientService.saveClient(client));

        // THEN
        assertThat(invalidDataException.getMessage()).isEqualTo("Invalid email format '" + client.getEmail() + "'");
        verify(clientRepository, never()).findByEmail(any());
        verify(clientRepository, never()).save(any());
    }

    @Test
    void removeClientByIdOK() {
        // GIVEN
        long clientId = 1;
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(new Client()));
        doNothing().when(clientRepository).deleteById(clientId);

        // WHEN
        assertDoesNotThrow(() -> clientService.removeClientById(clientId));

        // THEN
        verify(clientRepository, times(1)).findById(clientId);
        verify(clientRepository, times(1)).deleteById(clientId);
    }

    @Test
    void updateClientByIdOK() {
        // GIVEN
        long clientId = 1;
        Client client = Client.builder()
                .name("Rafa").surname("Barnabe").age(30).email("rafa@gmail.com").phoneNumber(123456789)
                .build();
        client.setId(clientId);
        when(clientRepository.findByEmail(client.getEmail())).thenReturn(Optional.empty());
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(clientRepository.save(client)).thenReturn(client);
        client.setId(0);


        // WHEN
        assertDoesNotThrow(() -> clientService.updateClientById(clientId, client));

        // THEN
        client.setId(clientId);
        verify(clientRepository, times(1)).findByEmail(client.getEmail());
        verify(clientRepository, times(1)).findById(clientId);
        verify(clientRepository, times(1)).save(client);

    }

    @Test
    void retrieveClientByIdOK() {
        // GIVEN
        long clientId = 1;
        Client expectedClient = Client.builder()
                .name("Rafa").surname("Barnabe").age(30).email("rafa@gmail.com").phoneNumber(123456789)
                .build();
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(expectedClient));

        // WHEN
        Client actualClient =
            assertDoesNotThrow(() -> clientService.retrieveClientById(clientId));

        // THEN
        assertThat(actualClient).isEqualTo(expectedClient);
        verify(clientRepository, times(1)).findById(clientId);
    }

    @Test
    void retrieveClientByIdNotExists() {
        // GIVEN
        long clientId = 1;
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        // WHEN
        EntityNotFoundException entityNotFoundException =
                assertThrows(EntityNotFoundException.class, () -> clientService.retrieveClientById(clientId));

        // THEN
        assertThat(entityNotFoundException.getMessage()).isEqualTo("Client with id '"+ clientId +"' not found");
        verify(clientRepository, times(1)).findById(clientId);
    }

    @Test
    void retrieveAllClients() {
        // GIVEN
        Client expectedClient = Client.builder()
                .name("Rafa").surname("Barnabe").age(30).email("rafa@gmail.com").phoneNumber(123456789)
                .build();
        when(clientRepository.findAll()).thenReturn(List.of(expectedClient));

        // WHEN
        List<Client> actualClients = clientService.retrieveAllClients();

        // THEN
        assertThat(actualClients).isEqualTo(List.of(expectedClient));
        verify(clientRepository, times(1)).findAll();
    }
}