// Paquete donde se ubicarán tus servicios
package com.example.demo.servicio;

import com.example.demo.modelo.Producto;
import com.example.demo.repositorio.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> findAllProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Optional<Producto> findProductoById(Long id) {
        return productoRepository.findById(id);
    }

    /**
     * Guarda un producto nuevo o actualiza uno existente.
     * @param producto El objeto Producto a guardar.
     * @return El producto guardado.
     */
    @Override
    public Producto saveProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    /**
     * Elimina un producto por su ID.
     * @param id El ID del producto a eliminar.
     */
    @Override
    public void deleteProducto(Long id) {
        productoRepository.deleteById(id);
    }
}