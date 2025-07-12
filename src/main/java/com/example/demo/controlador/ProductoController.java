// Paquete donde se ubicarán tus controladores
package com.example.demo.controlador;

import com.example.demo.modelo.Producto;
import com.example.demo.modelo.Usuario;
import com.example.demo.servicio.ProductoService;
import com.example.demo.servicio.CarritoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CarritoService carritoService;

    /**
     * Muestra la página de productos para usuarios autenticados.
     * Permite filtrar productos por categoría, género, talla y una cadena de búsqueda.
     * También carga el contador del carrito para la barra de navegación.
     *
     * @param categoria El filtro de categoría (opcional).
     * @param genero El filtro de género (opcional).
     * @param talla El filtro de talla (opcional).
     * @param search La cadena de búsqueda para nombre o descripción (opcional).
     * @param model El objeto Model para pasar datos a la vista.
     * @param session La sesión HTTP para obtener el usuario autenticado.
     * @return El nombre de la plantilla Thymeleaf para la página de productos.
     */
    @GetMapping("/productos")
    public String verProductos(
            @RequestParam(value = "categoria", required = false) String categoria,
            @RequestParam(value = "genero", required = false) String genero,
            @RequestParam(value = "talla", required = false) String talla,
            @RequestParam(value = "search", required = false) String search,
            Model model,
            HttpSession session) {

        // Verificar si el usuario está autenticado
        Usuario usuarioAutenticado = (Usuario) session.getAttribute("usuarioAutenticado");
        if (usuarioAutenticado == null) {
            // Si no está autenticado, redirigir al login
            return "redirect:/login";
        }

        // Obtener todos los productos para luego aplicar filtros en memoria
        List<Producto> productos = productoService.findAllProductos();

        // Aplicar filtros
        if (categoria != null && !categoria.isEmpty()) {
            productos = productos.stream()
                    .filter(p -> p.getCategoria() != null && p.getCategoria().equalsIgnoreCase(categoria))
                    .collect(Collectors.toList());
        }
        if (genero != null && !genero.isEmpty()) {
            try {
                // Usar el Enum GeneroProducto directamente
                Producto.GeneroProducto generoEnum = Producto.GeneroProducto.valueOf(genero.toLowerCase());
                productos = productos.stream()
                        .filter(p -> p.getGenero() == generoEnum)
                        .collect(Collectors.toList());
            } catch (IllegalArgumentException e) {
                System.err.println("Género no válido: " + genero);
            }
        }
        if (talla != null && !talla.isEmpty()) {
            try {
                // Usar el Enum TallaProducto directamente
                Producto.TallaProducto tallaEnum = Producto.TallaProducto.valueOf(talla.toUpperCase());
                productos = productos.stream()
                        .filter(p -> p.getTalla() == tallaEnum)
                        .collect(Collectors.toList());
            } catch (IllegalArgumentException e) {
                System.err.println("Talla no válida: " + talla);
            }
        }
        if (search != null && !search.isEmpty()) {
            String lowerCaseSearch = search.toLowerCase();
            productos = productos.stream()
                    .filter(p -> (p.getNombre() != null && p.getNombre().toLowerCase().contains(lowerCaseSearch)) ||
                                 (p.getDescripcion() != null && p.getDescripcion().toLowerCase().contains(lowerCaseSearch)))
                    .collect(Collectors.toList());
        }

        // Obtener todas las categorías únicas para el filtro
        Set<String> categoriasUnicas = productoService.findAllProductos().stream()
                .map(Producto::getCategoria) // CORREGIDO: getCategoria() ya existe en Producto
                .filter(c -> c != null && !c.isEmpty())
                .collect(Collectors.toSet());

        // Añadir los productos filtrados y los filtros seleccionados al modelo
        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categoriasUnicas);
        model.addAttribute("selectedCategoria", categoria);
        model.addAttribute("selectedGenero", genero);
        model.addAttribute("selectedTalla", talla);
        model.addAttribute("searchTerm", search);

        // Obtener la cantidad de ítems en el carrito del usuario autenticado
        int carritoCount = carritoService.countItemsInCarrito(usuarioAutenticado);
        model.addAttribute("carritoCount", carritoCount);

        return "productos"; // Esto buscará src/main/resources/templates/productos.html
    }
}
