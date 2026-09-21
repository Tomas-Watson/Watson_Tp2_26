package com.aydsii.demo.controller;

import com.aydsii.demo.dto.ApiResponse;
import com.aydsii.demo.dto.ConversionResponse;
import com.aydsii.demo.dto.HistorialResponseDTO;
import com.aydsii.demo.service.DivisasService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/divisas")
@Validated // Necesario para validar los @RequestParam
public class DivisasController {

    private final DivisasService divisasService;

    public DivisasController(DivisasService divisasService) {
        this.divisasService = divisasService;
    }

    @PostMapping("/consultar")
    public ResponseEntity<ApiResponse<ConversionResponse>> consultarCotizacion(
            @RequestParam @NotBlank @Size(min = 3, max = 3) String origen,
            @RequestParam @NotBlank @Size(min = 3, max = 3) String destino,
            @RequestParam @Positive Double monto) {

        ConversionResponse conversion = divisasService.consultarYGuardar(origen, destino, monto);

        ApiResponse<ConversionResponse> response = new ApiResponse<>();
        response.setStatus(200);
        response.setMessege("Cotización obtenida y guardada exitosamente");
        response.setData(conversion);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/historial")
    public ResponseEntity<ApiResponse<List<HistorialResponseDTO>>> obtenerHistorial(
            @RequestParam @NotBlank @Size(min = 3, max = 3) String origen,
            @RequestParam @NotBlank @Size(min = 3, max = 3) String destino) {

        List<HistorialResponseDTO> historial = divisasService.obtenerHistorial(origen, destino);

        ApiResponse<List<HistorialResponseDTO>> response = new ApiResponse<>();
        response.setStatus(200);
        response.setMessege("Historial recuperado correctamente");
        response.setData(historial);

        return ResponseEntity.ok(response);
    }
}