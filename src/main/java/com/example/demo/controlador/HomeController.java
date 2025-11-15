package com.example.demo.controlador;

import jakarta.servlet.http.HttpSession;
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
    public String home(HttpSession session) {
        // Si el usuario ya está autenticado, redirigir a /productos
        if (session.getAttribute("usuarioAutenticado") != null) {
            return "redirect:/productos";
        }
        return "index"; // Esto buscará src/main/resources/templates/index.html
    }
}
