package com.aydsii.demo.controller;

import com.aydsii.demo.dto.ApiResponse;
import com.aydsii.demo.dto.ConversionResponse;
import com.aydsii.demo.service.DivisasService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DivisasController {
    private final DivisasService divisasService;

    public DivisasController(DivisasService divisasService) {
        this.divisasService = divisasService;
    }

    @Operation(summary = "Convertir divisas", description = "Consulta la API de Frankfurter para convertir monedas")
    @GetMapping("/api/divisas/convertir")
    public ApiResponse<Object> convertirDivisas(
            @RequestParam double monto,
            @RequestParam String origen,
            @RequestParam String destino) {

        ApiResponse<Object> respuesta = new ApiResponse<>();

        // Validaciones requeridas[cite: 1, 2]
        if (monto <= 0 || origen.length() != 3 || destino.length() != 3) {
            respuesta.setStatus(400); // Bad Request[cite: 1, 2]
            respuesta.setMessege("Datos invalidos. El monto debe ser mayor a 0 y las monedas de 3 letras.");
            return respuesta;
        }

        try {
            // Aseguramos que los códigos estén en mayúsculas
            ConversionResponse conversion = divisasService.convertir(monto, origen.toUpperCase(), destino.toUpperCase());
            
            respuesta.setStatus(200);
            respuesta.setMessege("Operacion realizada con exito");
            respuesta.setData(conversion);
            
        } catch (Exception e) {
            // Imprime el error real en la terminal (letras rojas) para que podamos depurar
            e.printStackTrace(); 
            
            // Si Frankfurter no responde o da error, devolvemos 502
            respuesta.setStatus(502); 
            respuesta.setMessege("Error al comunicarse con el servicio externo de divisas.");
        }

        return respuesta;
    }
}
