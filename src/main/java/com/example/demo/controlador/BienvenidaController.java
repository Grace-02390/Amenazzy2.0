// Paquete donde se ubicarán tus controladores
package com.example.demo.controlador;

import com.example.demo.modelo.Producto;
import com.example.demo.modelo.Usuario;
import com.example.demo.servicio.ProductoService;
import com.example.demo.servicio.CarritoService; // Importar CarritoService
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BienvenidaController {

    @Autowired
    private ProductoService productoService;

    @Autowired // Inyectar CarritoService
    private CarritoService carritoService;

    /**
     * Muestra la página de bienvenida del usuario.
     * Obtiene los productos en promoción para mostrarlos.
     * @param model El objeto Model para pasar datos a la vista.
     * @param session La sesión HTTP para obtener información del usuario autenticado.
     * @return El nombre de la plantilla Thymeleaf para la página de bienvenida.
     */
    @GetMapping("/bienvenida")
    public String bienvenidaUsuario(Model model, HttpSession session) {
        // Obtener productos en oferta
        List<Producto> productosEnOferta = productoService.findAllProductos().stream()
                .filter(producto -> producto.getPromocion()) // CORREGIDO: Usar lambda para el filtro
                .collect(Collectors.toList());
        model.addAttribute("productosEnOferta", productosEnOferta);

        // Opcional: Obtener la cantidad de items en el carrito para mostrar en el icono
        if (session.getAttribute("usuarioAutenticado") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuarioAutenticado");
            int carritoCount = carritoService.countItemsInCarrito(usuario); // Usar el servicio de carrito
            model.addAttribute("carritoCount", carritoCount);
        } else {
            model.addAttribute("carritoCount", 0);
        }

        return "bienvenida"; // Esto buscará src/main/resources/templates/bienvenida.html
    }
}
