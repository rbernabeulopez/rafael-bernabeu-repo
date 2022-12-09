package com.example.examen_jpa_cascada.domain.repository;

import com.example.examen_jpa_cascada.domain.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
