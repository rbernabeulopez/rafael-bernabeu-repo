package com.example.examen_jpa_cascada.infrastructure.controller;

import com.example.examen_jpa_cascada.application.CabeceraFraService;
import com.example.examen_jpa_cascada.domain.entities.LineaFra;
import com.example.examen_jpa_cascada.infrastructure.controller.dto.FacturaOutputDto;
import com.example.examen_jpa_cascada.infrastructure.controller.dto.LineaInputDto;
import com.example.examen_jpa_cascada.infrastructure.mapper.FacturaMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("factura")
public class FacturaController {
    private CabeceraFraService cabeceraFraService;

    @GetMapping
    public List<FacturaOutputDto> searchAllInvoices() {
        return cabeceraFraService.findAllInvoices().stream().map(FacturaMapper.INSTANCE::cabeceraFraToFacturaOutputDto)
                .toList();
    }

    @DeleteMapping("{idFra}")
    public void deleteInvoiceById(@PathVariable int idFra) {
        cabeceraFraService.deleteInvoiceById(idFra);
    }

    @PostMapping("linea/{idFra}")
    public FacturaOutputDto addLinea(@RequestBody LineaInputDto lineaInputDto, @PathVariable int idFra) {
        LineaFra lineaFra = FacturaMapper.INSTANCE.lineaInputDtoToLineasFra(lineaInputDto);
        return FacturaMapper.INSTANCE.cabeceraFraToFacturaOutputDto(cabeceraFraService.addLinea(lineaFra, idFra));
    }
}
