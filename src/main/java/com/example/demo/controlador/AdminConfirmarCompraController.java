package com.example.demo.controlador;

import com.example.demo.modelo.Pago;
import com.example.demo.modelo.Usuario;
import com.example.demo.servicio.PagoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminConfirmarCompraController {

    @Autowired
    private PagoService pagoService;

    /**
     * Muestra la página de confirmación de compras pendientes para el administrador.
     * @param model El objeto Model para pasar datos a la vista.
     * @param session La sesión HTTP para obtener el nombre del administrador.
     * @return El nombre de la plantilla Thymeleaf para la página de confirmación de compras.
     */
    @GetMapping("/confirmar-compras")
    public String mostrarConfirmarCompras(Model model, HttpSession session) {
        Usuario usuarioAutenticado = (Usuario) session.getAttribute("usuarioAutenticado");
        if (usuarioAutenticado != null) {
            model.addAttribute("nombreAdmin", usuarioAutenticado.getNombre());
        }

        // Obtener todas las compras pendientes
        List<Pago> comprasPendientes = pagoService.findPagosByEstado(Pago.EstadoPago.pendiente);
        model.addAttribute("comprasPendientes", comprasPendientes);

        return "admin_confirmar_compras";
    }

    /**
     * Confirma una compra pendiente.
     * @param pagoId El ID del pago a confirmar.
     * @param redirectAttributes Para añadir mensajes flash.
     * @return Redirección a la página de confirmación de compras.
     */
    @PostMapping("/confirmar-compra")
    public String confirmarCompra(
            @RequestParam("pagoId") Long pagoId,
            RedirectAttributes redirectAttributes) {
        
        try {
            pagoService.cambiarEstadoPago(pagoId, Pago.EstadoPago.completado);
            redirectAttributes.addFlashAttribute("mensaje", "Compra confirmada exitosamente.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Error al confirmar la compra: " + e.getMessage());
        }
        
        return "redirect:/admin/confirmar-compras";
    }

    /**
     * Cancela una compra pendiente.
     * @param pagoId El ID del pago a cancelar.
     * @param redirectAttributes Para añadir mensajes flash.
     * @return Redirección a la página de confirmación de compras.
     */
    @PostMapping("/cancelar-compra")
    public String cancelarCompra(
            @RequestParam("pagoId") Long pagoId,
            RedirectAttributes redirectAttributes) {
        
        try {
            pagoService.cambiarEstadoPago(pagoId, Pago.EstadoPago.rechazado);
            redirectAttributes.addFlashAttribute("mensaje", "Compra rechazada exitosamente.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Error al rechazar la compra: " + e.getMessage());
        }
        
        return "redirect:/admin/confirmar-compras";
    }
} 