// Paquete donde se ubicarán tus servicios
package com.example.demo.servicio;

import com.example.demo.modelo.Producto;
import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<Producto> findAllProductos();
    Optional<Producto> findProductoById(Long id);

    /**
     * Guarda un producto nuevo o actualiza uno existente.
     * @param producto El objeto Producto a guardar.
     * @return El producto guardado.
     */
    Producto saveProducto(Producto producto);

    /**
     * Elimina un producto por su ID.
     * @param id El ID del producto a eliminar.
     */
    void deleteProducto(Long id);
}