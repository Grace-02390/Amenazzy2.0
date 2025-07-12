// Paquete donde se ubicarán tus controladores
package com.example.demo.controlador; // Asegúrate de cambiar 'com.tuproyecto.controlador' a tu paquete real

import com.example.demo.modelo.Producto;
import com.example.demo.servicio.ProductoService; // Necesitaremos crear este servicio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ProductoPublicoController {

    @Autowired
    private ProductoService productoService; // Inyectamos el servicio de productos

    /**
     * Maneja las solicitudes GET para la página de productos públicos.
     * Permite filtrar productos por categoría, género, talla y una cadena de búsqueda.
     *
     * @param categoria El filtro de categoría (opcional).
     * @param genero El filtro de género (opcional).
     * @param talla El filtro de talla (opcional).
     * @param search La cadena de búsqueda para nombre o descripción (opcional).
     * @param model El objeto Model para pasar datos a la vista.
     * @return El nombre de la plantilla Thymeleaf para la página de productos públicos.
     */
    @GetMapping("/productos-publicos")
    public String verProductosPublicos(
            @RequestParam(value = "categoria", required = false) String categoria,
            @RequestParam(value = "genero", required = false) String genero,
            @RequestParam(value = "talla", required = false) String talla,
            @RequestParam(value = "search", required = false) String search,
            Model model) {

        // Obtener todos los productos para luego aplicar filtros en memoria
        // En una aplicación más grande, se optimizaría la consulta a la base de datos
        List<Producto> productos = productoService.findAllProductos();

        // Aplicar filtros
        if (categoria != null && !categoria.isEmpty()) {
            productos = productos.stream()
                    .filter(p -> p.getCategoria() != null && p.getCategoria().equalsIgnoreCase(categoria))
                    .collect(Collectors.toList());
        }
        if (genero != null && !genero.isEmpty()) {
            try {
                Producto.GeneroProducto generoEnum = Producto.GeneroProducto.valueOf(genero.toLowerCase());
                productos = productos.stream()
                        .filter(p -> p.getGenero() == generoEnum)
                        .collect(Collectors.toList());
            } catch (IllegalArgumentException e) {
                // Manejar error si el género no es válido
                System.err.println("Género no válido: " + genero);
            }
        }
        if (talla != null && !talla.isEmpty()) {
            try {
                Producto.TallaProducto tallaEnum = Producto.TallaProducto.valueOf(talla.toUpperCase());
                productos = productos.stream()
                        .filter(p -> p.getTalla() == tallaEnum)
                        .collect(Collectors.toList());
            } catch (IllegalArgumentException e) {
                // Manejar error si la talla no es válida
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

        // Obtener todas las categorías únicas para el filtro (para que se muestren en el dropdown)
        Set<String> categoriasUnicas = productoService.findAllProductos().stream()
                .map(Producto::getCategoria) // CORREGIDO: getCategoria() ya existe en Producto
                .filter(c -> c != null && !c.isEmpty())
                .collect(Collectors.toSet());

        // Añadir los productos filtrados y los filtros seleccionados al modelo
        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categoriasUnicas); // Para el dropdown de categorías
        model.addAttribute("selectedCategoria", categoria);
        model.addAttribute("selectedGenero", genero);
        model.addAttribute("selectedTalla", talla);
        model.addAttribute("searchTerm", search);

        return "productos_publicos"; // Esto buscará src/main/resources/templates/productos_publicos.html
    }
}
