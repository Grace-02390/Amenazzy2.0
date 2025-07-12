// Paquete donde se ubicarán tus modelos
package com.example.demo.modelo;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String categoria; // Nuevo campo

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GeneroProducto genero; // Nuevo campo

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TallaProducto talla; // Nuevo campo

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(precision = 5, scale = 2)
    private BigDecimal descuento = BigDecimal.ZERO; // Descuento en porcentaje (ej. 10.00 para 10%)

    @Column(nullable = false)
    private int stock; // Nuevo campo

    @Column(nullable = false)
    private boolean disponible = true; // Nuevo campo, por defecto disponible

    @Column(nullable = false)
    private boolean promocion = false; // Nuevo campo, por defecto sin promoción

    private String imagenUrl; // URL de la imagen del producto

    private String tela; // Nuevo campo

    @OneToMany(mappedBy = "producto")
    private List<Carrito> carritos;

    @OneToMany(mappedBy = "producto")
    private List<DetalleVenta> detallesVenta;

    // Enums para Género y Talla
    public enum GeneroProducto {
        hombre,
        mujer,
        niño,
        niña
    }

    public enum TallaProducto {
        S,
        M,
        L,
        XL
    }

    // Constructores
    public Producto() {
    }

    public Producto(String nombre, String descripcion, String categoria, GeneroProducto genero, TallaProducto talla, BigDecimal precio, BigDecimal descuento, int stock, boolean disponible, boolean promocion, String imagenUrl, String tela) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.genero = genero;
        this.talla = talla;
        this.precio = precio;
        this.descuento = descuento;
        this.stock = stock;
        this.disponible = disponible;
        this.promocion = promocion;
        this.imagenUrl = imagenUrl;
        this.tela = tela;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public GeneroProducto getGenero() {
        return genero;
    }

    public void setGenero(GeneroProducto genero) {
        this.genero = genero;
    }

    public TallaProducto getTalla() {
        return talla;
    }

    public void setTalla(TallaProducto talla) {
        this.talla = talla;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public boolean getPromocion() { // Cambiado a getPromocion para consistencia con isPromocion
        return promocion;
    }

    public void setPromocion(boolean promocion) {
        this.promocion = promocion;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getTela() {
        return tela;
    }

    public void setTela(String tela) {
        this.tela = tela;
    }

    public List<Carrito> getCarritos() {
        return carritos;
    }

    public void setCarritos(List<Carrito> carritos) {
        this.carritos = carritos;
    }

    public List<DetalleVenta> getDetallesVenta() {
        return detallesVenta;
    }

    public void setDetallesVenta(List<DetalleVenta> detallesVenta) {
        this.detallesVenta = detallesVenta;
    }

    @Override
    public String toString() {
        return "Producto{" +
               "id=" + id +
               ", nombre='" + nombre + '\'' +
               ", descripcion='" + descripcion + '\'' +
               ", categoria='" + categoria + '\'' +
               ", genero=" + genero +
               ", talla=" + talla +
               ", precio=" + precio +
               ", descuento=" + descuento +
               ", stock=" + stock +
               ", disponible=" + disponible +
               ", promocion=" + promocion +
               ", imagenUrl='" + imagenUrl + '\'' +
               ", tela='" + tela + '\'' +
               '}';
    }
}