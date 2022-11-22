package com.example.block6simplecontrollers;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Persona {
    private String nombre;
    private String ciudad;
    private String edad;
}
