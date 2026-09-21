package com.aydsii.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity 
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;    

    @Column(name = "fecha_pedido")
    private LocalDate fechaPedido;

    private String estado; 

    @OneToMany(mappedBy = "pedido")
    private List<DetallePedido> detalles;

    public Double getTotal() {
        return detalles.stream().mapToDouble(DetallePedido::getSubtotal).sum();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public LocalDate getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(LocalDate fechaPedido) { this.fechaPedido = fechaPedido; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public List<DetallePedido> getDetalles() { return detalles; }
    public void setDetalles(List<DetallePedido> detalles) { this.detalles = detalles; }
}
