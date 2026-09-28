package com.aydsii.demo.model;

import jakarta.persistence.*;

@Entity 
@Table(name = "detalles_pedidos")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    /*
    *
    *La anotacion @ManyToOne sirve para mapear uan relacion de muchos
    * a uno entre dos entidades de la base de datos 
    */
    @ManyToOne 
    @JoinColumn(name = "producto_id")
    private Producto producto; 

    private Integer cantidad;

    @Column(name = "precio_unitario") //Con el name, declaro el nombre de tendra la columna en la BD
    private Double precioUnitario;

    public Double getSubtotal() {
        return this.cantidad * this.precioUnitario;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public Double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(Double precioUnitario) { this.precioUnitario = precioUnitario; }
}


