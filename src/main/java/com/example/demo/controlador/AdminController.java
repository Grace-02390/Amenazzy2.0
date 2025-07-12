package com.example.demo.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/bienvenida")
    public String bienvenidaAdmin(Model model) {
        model.addAttribute("nombreAdmin", "Administrador"); // Ejemplo de dato pasado a la plantilla
        return "admin_bienvenida"; // Nombre del archivo HTML sin la extensión .html
    }

    // Aquí irían otros métodos para las demás páginas de administración
}