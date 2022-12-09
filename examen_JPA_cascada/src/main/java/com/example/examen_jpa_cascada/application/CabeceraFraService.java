package com.example.examen_jpa_cascada.application;

import com.example.examen_jpa_cascada.domain.entities.CabeceraFra;
import com.example.examen_jpa_cascada.domain.entities.LineaFra;

import java.util.List;

public interface CabeceraFraService {
    CabeceraFra saveInvoiceHeader(CabeceraFra cabeceraFra, int clientId);

    List<CabeceraFra> findAllInvoices();

    void deleteInvoiceById(int idFra);

    CabeceraFra addLinea(LineaFra lineaFra, int idFra);
}
