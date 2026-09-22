package com.aydsii.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.List;

import com.aydsii.demo.dto.VentasDTO;
import com.aydsii.demo.dto.ApiResponse;
import com.aydsii.demo.service.VentasService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

@RequestMapping("/api/ventas")
@RestController
public class VentasController {

    // Inyectamos el servicio
    private final VentasService ventasService;

    public VentasController(VentasService ventasService) {
        this.ventasService = ventasService;
    }

    @Operation(summary = "Procesar ventas", description = "Calcula las estadisticas a partir de un lote de ventas")
    @PostMapping("/estadisticas")
    public ResponseEntity<ApiResponse<Map<String, Object>>> procesarVentas(@NotEmpty(message = "La lista no puede estar vacía") @Valid @RequestBody List<VentasDTO> ventas) {
        
       
        Map<String, Object> resultado = ventasService.calcularEstadisticas(ventas);

        // Armamos la respuesta y la servimos
        ApiResponse<Map<String, Object>> respuesta = new ApiResponse<>();
        respuesta.setStatus(200);
        respuesta.setMessege("Operacion realizada con exito");
        respuesta.setData(resultado);

        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Aplicar descuento" , description = "Calcular el monto con descuento para una lista de ventas")
    @PostMapping("/aplicar-descuento")
    public ResponseEntity<ApiResponse<Map<String, Object>>> aplicarDescuento(@NotEmpty(message = "La lista no puede estar vacía") @Valid @RequestBody List<VentasDTO> ventas, @RequestParam double porcentaje) {
        
        // Validación rápida del porcentaje antes de molestar al Service
        if (porcentaje < 0 || porcentaje > 100) {
            ApiResponse<Map<String, Object>> error = new ApiResponse<>();
            error.setStatus(400);
            error.setMessege("El porcentaje no es valido debe estar entre 0 y 100");
            error.setData(null);
            return ResponseEntity.badRequest().body(error);
        }

        // 1. Delegamos el cálculo
        Map<String, Object> resultado = ventasService.procesarDescuento(ventas, porcentaje);

        // 2. Armamos y servimos la respuesta
        ApiResponse<Map<String, Object>> respuesta = new ApiResponse<>();
        respuesta.setStatus(200);
        respuesta.setMessege("Operacion realizada con exito");
        respuesta.setData(resultado);

        return ResponseEntity.ok(respuesta);
    }
}