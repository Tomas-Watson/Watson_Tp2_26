package com.aydsii.demo.service;

import com.aydsii.demo.dto.PedidoResponseDTO;
import com.aydsii.demo.dto.ProductoPedidoDTO;
import com.aydsii.demo.model.DetallePedido;
import com.aydsii.demo.model.Pedido;
import com.aydsii.demo.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<PedidoResponseDTO> buscarPedidos(Long clienteId, String categoria, LocalDate fechaDesde, LocalDate fechaHasta, String estado) {
        // 1. Buscamos en la base de datos con los filtros (si son nulos, el repositorio los ignora)
        List<Pedido> pedidos = pedidoRepository.buscarPedidos(clienteId, categoria, fechaDesde, fechaHasta, estado);

        // 2. Transformamos la lista de Entidades a DTOs
        return pedidos.stream()
                .map(this::mapearAPedidoResponseDTO)
                .collect(Collectors.toList());
    }

    // Método auxiliar para convertir un Pedido en PedidoResponseDTO
    private PedidoResponseDTO mapearAPedidoResponseDTO(Pedido pedido) {
        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setPedidoId(pedido.getId());
        
        // El TP pide el nombre completo del cliente
        dto.setCliente(pedido.getCliente().getNombre() + " " + pedido.getCliente().getApellido());
        dto.setFecha(pedido.getFechaPedido());
        dto.setEstado(pedido.getEstado());
        dto.setTotalPedido(pedido.getTotal());

        // Transformamos los detalles del pedido a ProductoPedidoDTO
        List<ProductoPedidoDTO> productosDTO = pedido.getDetalles().stream()
                .map(this::mapearAProductoPedidoDTO)
                .collect(Collectors.toList());
        dto.setProductos(productosDTO);

        return dto;
    }

    // Método auxiliar para convertir DetallePedido en ProductoPedidoDTO
    private ProductoPedidoDTO mapearAProductoPedidoDTO(DetallePedido detalle) {
        ProductoPedidoDTO dto = new ProductoPedidoDTO();
        dto.setNombre(detalle.getProducto().getNombre());
        dto.setCategoria(detalle.getProducto().getCategoria().getNombre());
        dto.setCantidad(detalle.getCantidad());
        dto.setSubtotal(detalle.getSubtotal());
        return dto;
    }
}
