package com.example.demo.controlador;

import com.example.demo.modelo.Pago;
import com.example.demo.modelo.Usuario;
import com.example.demo.servicio.PagoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cliente")
public class ClientePagoController {

    @Autowired
    private PagoService pagoService;

    /**
     * Permite al cliente cancelar su propia compra pendiente.
     * @param numeroBoleta El número de boleta a cancelar.
     * @param session La sesión HTTP para obtener el usuario autenticado.
     * @param redirectAttributes Para añadir mensajes flash.
     * @return Redirección a la página de historial de boletas.
     */
    @PostMapping("/cancelar-compra")
    public String cancelarCompraCliente(
            @RequestParam("numeroBoleta") String numeroBoleta,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        Usuario usuarioAutenticado = (Usuario) session.getAttribute("usuarioAutenticado");
        
        if (usuarioAutenticado == null) {
            return "redirect:/login";
        }

        try {
            // Buscar el pago por número de boleta
            var pagoOptional = pagoService.findPagoByNumeroBoleta(numeroBoleta);
            
            if (pagoOptional.isPresent()) {
                Pago pago = pagoOptional.get();
                
                // Verificar que el pago pertenece al usuario autenticado
                if (!pago.getUsuario().getId().equals(usuarioAutenticado.getId())) {
                    redirectAttributes.addFlashAttribute("error", "No tienes permisos para cancelar esta compra.");
                    return "redirect:/cliente/historial-boletas";
                }
                
                // Verificar que el pago esté en estado pendiente
                if (pago.getEstado() != Pago.EstadoPago.pendiente) {
                    redirectAttributes.addFlashAttribute("error", "Solo se pueden cancelar compras pendientes.");
                    return "redirect:/cliente/historial-boletas";
                }
                
                // Cambiar estado a rechazado
                pagoService.cambiarEstadoPago(pago.getId(), Pago.EstadoPago.rechazado);
                
                redirectAttributes.addFlashAttribute("mensaje", "Compra rechazada exitosamente.");
            } else {
                redirectAttributes.addFlashAttribute("error", "Boleta no encontrada.");
            }
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al cancelar la compra: " + e.getMessage());
        }
        
        return "redirect:/cliente/historial-boletas";
    }
} 