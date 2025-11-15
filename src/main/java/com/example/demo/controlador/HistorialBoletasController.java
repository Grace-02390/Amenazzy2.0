package com.example.demo.controlador;

import com.example.demo.modelo.Pago;
import com.example.demo.modelo.Usuario;
import com.example.demo.servicio.PagoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/cliente")
public class HistorialBoletasController {

    @Autowired
    private PagoService pagoService;

    /**
     * Muestra el historial de boletas del cliente.
     * @param model El objeto Model para pasar datos a la vista.
     * @param session La sesión HTTP para obtener el usuario autenticado.
     * @param estado Filtro opcional por estado (pendiente, completado, cancelado).
     * @return El nombre de la plantilla Thymeleaf para la página de historial.
     */
    @GetMapping("/historial-boletas")
    public String mostrarHistorialBoletas(
            Model model, 
            HttpSession session,
            @RequestParam(value = "estado", required = false) String estado) {
        
        Usuario usuarioAutenticado = (Usuario) session.getAttribute("usuarioAutenticado");
        
        if (usuarioAutenticado == null) {
            return "redirect:/login";
        }

        List<Pago> boletas;
        
        if (estado != null && !estado.isEmpty()) {
            try {
                Pago.EstadoPago estadoEnum = Pago.EstadoPago.valueOf(estado.toLowerCase());
                boletas = pagoService.findPagosByUsuarioAndEstado(usuarioAutenticado, estadoEnum);
            } catch (IllegalArgumentException e) {
                // Si el estado no es válido, mostrar todas las boletas
                boletas = pagoService.findPagosByUsuario(usuarioAutenticado);
            }
        } else {
            // Mostrar todas las boletas del usuario
            boletas = pagoService.findPagosByUsuario(usuarioAutenticado);
        }

        model.addAttribute("boletas", boletas);
        model.addAttribute("estadoFiltro", estado);
        model.addAttribute("usuario", usuarioAutenticado);
        
        return "historial_boletas";
    }
} 