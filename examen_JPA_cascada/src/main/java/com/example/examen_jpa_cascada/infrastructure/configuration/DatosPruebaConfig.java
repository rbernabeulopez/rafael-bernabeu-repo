package com.example.examen_jpa_cascada.infrastructure.configuration;

import com.example.examen_jpa_cascada.application.ClienteService;
import com.example.examen_jpa_cascada.application.CabeceraFraService;
import com.example.examen_jpa_cascada.domain.entities.Cliente;
import com.example.examen_jpa_cascada.domain.entities.CabeceraFra;
import com.example.examen_jpa_cascada.domain.entities.LineaFra;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@AllArgsConstructor
public class DatosPruebaConfig {
    private ClienteService clienteService;

    private CabeceraFraService cabeceraFraService;

    @Bean
    public void insertData() {
        Cliente cliente = new Cliente();
        cliente.setNombre("Chuchi");
        cliente = clienteService.saveCliente(cliente);

        LineaFra lineaFra1 = new LineaFra();
        lineaFra1.setProNomb("Patatas");
        lineaFra1.setPrecio(2.5);
        lineaFra1.setCantidad(3);

        LineaFra lineaFra2 = new LineaFra();
        lineaFra2.setProNomb("Helado");
        lineaFra2.setPrecio(1.5);
        lineaFra2.setCantidad(2);

        CabeceraFra cabeceraFra = new CabeceraFra();
        lineaFra1.setCabeceraFra(cabeceraFra);
        lineaFra2.setCabeceraFra(cabeceraFra);
        cabeceraFra.setLineasFra(List.of(lineaFra1, lineaFra2));
        cabeceraFraService.saveInvoiceHeader(cabeceraFra, cliente.getId());
    }
}
