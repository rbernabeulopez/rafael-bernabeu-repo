package com.example.examen_jpa_cascada.application;

import com.example.examen_jpa_cascada.domain.entities.Cliente;

public interface ClienteService {
    Cliente saveCliente(Cliente cliente);

    Cliente findById(int id);
}
