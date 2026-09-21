package com.aydsii.demo.controller;

import com.aydsii.demo.dto.ApiResponse;
import com.aydsii.demo.dto.PedidoResponseDTO;
import com.aydsii.demo.service.PedidoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<List<PedidoResponseDTO>>> buscarPedidos(
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) String categoria,
            // @DateTimeFormat asegura que Spring entienda el formato yyyy-MM-dd
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,
            @RequestParam(required = false) String estado) {

        List<PedidoResponseDTO> pedidos = pedidoService.buscarPedidos(clienteId, categoria, fechaDesde, fechaHasta, estado);

        // Armamos la respuesta respetando el formato del TP
        ApiResponse<List<PedidoResponseDTO>> response = new ApiResponse<>();
        response.setStatus(200);
        response.setMessege("Consulta realizada correctamente");
        response.setData(pedidos);

        return ResponseEntity.ok(response);
    }
}
