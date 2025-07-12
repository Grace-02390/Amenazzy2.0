package com.example.demo.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * Maneja las solicitudes GET a la ruta raíz ("/").
     * Retorna el nombre de la plantilla Thymeleaf para la página de inicio.
     * @return El nombre de la vista "index".
     */
    @GetMapping("/")
    public String home() {
        return "index"; // Esto buscará src/main/resources/templates/index.html
    }
}
