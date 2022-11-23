package com.example.block6personcontrollers;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Persona {
    private String nombre;
    private String ciudad;
    private String edad;
}
