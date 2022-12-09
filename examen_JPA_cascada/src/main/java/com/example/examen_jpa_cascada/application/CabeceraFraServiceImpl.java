package com.example.examen_jpa_cascada.application;

import com.example.examen_jpa_cascada.domain.entities.Cliente;
import com.example.examen_jpa_cascada.domain.entities.CabeceraFra;
import com.example.examen_jpa_cascada.domain.entities.LineaFra;
import com.example.examen_jpa_cascada.domain.exception.InvoiceNotFoundException;
import com.example.examen_jpa_cascada.domain.repository.CabeceraFraRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CabeceraFraServiceImpl implements CabeceraFraService {
    private CabeceraFraRepository cabeceraFraRepository;

    private ClienteService clienteService;

    private CabeceraFra findById(int id) {
        return cabeceraFraRepository.findById(id).orElseThrow(InvoiceNotFoundException::new);
    }

    private double calculateImport(List<LineaFra> lineasFra) {
        return lineasFra.stream().mapToDouble(linea -> linea.getPrecio() * linea.getCantidad()).sum();
    }

    @Override
    public CabeceraFra saveInvoiceHeader(CabeceraFra cabeceraFra, int clientId) {
        log.info("Saving invoice header with data {} for client with id {}", cabeceraFra, clientId);

        Cliente cliente = clienteService.findById(clientId);
        cabeceraFra.setCliente(cliente);
        cabeceraFra.setImporteFra(calculateImport(cabeceraFra.getLineasFra()));
        return cabeceraFraRepository.save(cabeceraFra);
    }

    @Override
    public List<CabeceraFra> findAllInvoices() {
        log.info("Finding all invoices");

        return cabeceraFraRepository.findAll();
    }

    @Override
    public void deleteInvoiceById(int idFra) {
        log.info("Deleting invoice with id {}", idFra);

        findById(idFra);
        cabeceraFraRepository.deleteById(idFra);
    }

    @Override
    public CabeceraFra addLinea(LineaFra lineaFra, int idFra) {
        log.info("Adding line with data {} to invoice with id {}", lineaFra, idFra);

        CabeceraFra cabeceraFra = findById(idFra);
        lineaFra.setCabeceraFra(cabeceraFra);
        List<LineaFra> lineasFras = cabeceraFra.getLineasFra();
        lineasFras.add(lineaFra);
        cabeceraFra.setLineasFra(lineasFras);

        cabeceraFra.setImporteFra(calculateImport(cabeceraFra.getLineasFra()));

        return cabeceraFraRepository.save(cabeceraFra);
    }
}
