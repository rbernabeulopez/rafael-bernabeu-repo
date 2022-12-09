package com.example.examen_jpa_cascada.application;

import com.example.examen_jpa_cascada.domain.entities.Cliente;
import com.example.examen_jpa_cascada.domain.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ClienteServiceImpl implements ClienteService {
    private ClienteRepository clienteRepository;

    @Override
    public Cliente saveCliente(Cliente cliente) {
        log.info("Saving client with data {}", cliente);

        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente findById(int id) {
        log.info("Finding client with id {}", id);

        return clienteRepository.findById(id).orElseThrow();
    }
}
