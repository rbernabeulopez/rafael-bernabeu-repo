package com.example.block6personcontrollers;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * El ejercicio pide obtener uno de los beans creados (bean1, bean2, bean3)
 * según el nombre que le pasemos por el path
 */
@RestController
@RequestMapping("controlador")
public class Controlador {
    @Autowired // otra opción encontrada por google :)
    private BeanFactory beanFactory;

    @Autowired
    private Persona bean1;

    @Autowired
    private Persona bean2;

    @Autowired
    private Persona bean3;


    @GetMapping("bean/{beanName}")
    Object getPersonaByBeanName(@PathVariable String beanName) {
        return switch (beanName) {
            case "bean1" -> bean1;
            case "bean2" -> bean2;
            case "bean3" -> bean3;
            default -> null;
        };
    }
}
