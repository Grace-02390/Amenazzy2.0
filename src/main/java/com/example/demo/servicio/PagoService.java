// Paquete donde se ubicarán tus servicios
package com.example.demo.servicio;

import com.example.demo.modelo.Pago;
import com.example.demo.modelo.Usuario;
import com.example.demo.modelo.Carrito;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PagoService {

    Pago procesarPago(Usuario usuario, Pago.MetodoPago metodoPago, BigDecimal totalPagar, String numeroBoleta, List<Carrito> carritoItems);
    Optional<Pago> findPagoById(Long id);
    List<Pago> findPagosByUsuario(Usuario usuario);

    /**
     * Obtiene el total de ventas por día en un rango de fechas.
     * @param startDate Fecha de inicio del rango.
     * @param endDate Fecha de fin del rango.
     * @return Un mapa donde la clave es la fecha (LocalDate) y el valor es el total de ventas (BigDecimal).
     */
    Map<LocalDate, BigDecimal> getTotalSalesByDay(LocalDate startDate, LocalDate endDate);

    /**
     * Obtiene el total de ventas por mes en un año específico.
     * @param year El año para el que se desean las ventas.
     * @return Un mapa donde la clave es el número del mes (Integer, 1-12) y el valor es el total de ventas (BigDecimal).
     */
    Map<Integer, BigDecimal> getTotalSalesByMonth(int year);

    /**
     * Obtiene los productos más vendidos (top N) en un período.
     * @param limit El número máximo de productos a retornar.
     * @return Una lista de objetos que representan los productos más vendidos (ej. Producto con cantidad vendida).
     */
    List<Object[]> getMostSoldProducts(int limit);

    /**
     * Obtiene todas las ventas.
     * @return Una lista de todos los objetos Pago.
     */
    List<Pago> findAllPagos();
    
    /**
     * Obtiene pagos por estado.
     * @param estado El estado de los pagos a buscar.
     * @return Una lista de pagos con el estado especificado.
     */
    List<Pago> findPagosByEstado(Pago.EstadoPago estado);
    
    /**
     * Obtiene pagos por usuario y estado.
     * @param usuario El usuario.
     * @param estado El estado de los pagos a buscar.
     * @return Una lista de pagos del usuario con el estado especificado.
     */
    List<Pago> findPagosByUsuarioAndEstado(Usuario usuario, Pago.EstadoPago estado);
    
    /**
     * Cambia el estado de un pago.
     * @param pagoId El ID del pago.
     * @param nuevoEstado El nuevo estado.
     * @return El pago actualizado.
     */
    Pago cambiarEstadoPago(Long pagoId, Pago.EstadoPago nuevoEstado);
    
    /**
     * Busca un pago por número de boleta.
     * @param numeroBoleta El número de boleta.
     * @return Un Optional que contiene el pago si se encuentra.
     */
    Optional<Pago> findPagoByNumeroBoleta(String numeroBoleta);
}
