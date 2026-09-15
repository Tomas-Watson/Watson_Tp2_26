package com.aydsii.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import com.aydsii.demo.dto.VentasDTO;
import com.aydsii.demo.dto.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

@RequestMapping("/api/ventas")
@RestController
public class VentasController {

    /*
    *@Operation es la etiqueta de Swagger para documentar de que trata mi endpoint
    *
    *@PostMapping define que el metodo escuchara peticiones POST en la ruta indicada
    *en este caso la ruta es la que se pide en el tp2
    *
    *@Valid, con esta etiqueta Spring Boot revisara automaticamente todas las reglas 
    *
    *@RequestBody toma el JSON entrante y lo convierte en una lista JAVA
    */

    @Operation(summary = "Procesar ventas", description = "Calcula las estadisticas a partir de un lote de ventas")
    @PostMapping("/estadisticas")
    public ApiResponse<Map<String, Object>> procesarVentas(@NotEmpty(message = "La lista no puede estar vacía") @Valid @RequestBody List<VentasDTO> ventas) {
        //Aqui dentro debo calcular las estadisticas indicadas en el tp 
        
        double totalFacturado = 0;
        double totalUnitario = 0;
        VentasDTO ventaMayor = null;
        VentasDTO ventaMenor = null;

        //con este mapa voy sumando las cantidades de cada producto 
        Map<String, Integer> cantidadesPorProducto = new HashMap<>();

        //con este for each voy a poder manejar mejor las listas 
        //calculo el totalFacturado
        for(VentasDTO venta: ventas){
            totalUnitario = venta.getCantidad() * venta.getPrecioUnitario();
            totalFacturado = totalFacturado + totalUnitario;

            // Venta Mayor
            if (ventaMayor == null || totalUnitario > (ventaMayor.getCantidad() * ventaMayor.getPrecioUnitario())) {
                ventaMayor = venta;
            }

            // Venta Menor
            if (ventaMenor == null || totalUnitario < (ventaMenor.getCantidad() * ventaMenor.getPrecioUnitario())) {
                ventaMenor = venta;
            }

            cantidadesPorProducto.put(venta.getProducto(), cantidadesPorProducto.getOrDefault(venta.getProducto(), 0) + venta.getCantidad());
        }

        //Con esto calculo la cantidad de ventas en la lista
        int cantidadVentas = ventas.size();

        //ticketPromedio
        double ticketPromedio = totalFacturado / cantidadVentas;

        //ProductoMasVendido
        String productoMasVendido = "";
        int maxCantidad = 0;
        for (Map.Entry<String, Integer> entry : cantidadesPorProducto.entrySet()) {
            if (entry.getValue() > maxCantidad) {
                maxCantidad = entry.getValue();
                productoMasVendido = entry.getKey();
            }
        }

        //Empaqueto todo en el map
        Map<String, Object> data = new HashMap<>();
        data.put("totalFacturado", totalFacturado);
        data.put("cantidadVentas", cantidadVentas);
        data.put("ticketPromedio", ticketPromedio);
        data.put("ventaMayor", ventaMayor);
        data.put("ventaMenor", ventaMenor);
        data.put("productoMasVendido", productoMasVendido);

        //armo la respuesta
        ApiResponse<Map<String, Object>> respuesta = new ApiResponse<>();
        respuesta.setStatus(200);
        respuesta.setMessege("Operacion realizada con exito");
        respuesta.setData(data);

        return respuesta;
    }

    @Operation(summary = "Aplicar descuento" , description = "Calcular el monto con desceunto para una lista de ventas")
    @PostMapping("/aplicar-descuento")
    public ApiResponse<Map<String, Object>> aplicarDescuento(@NotEmpty(message = "La lista no puede estar vacía") @Valid @RequestBody List<VentasDTO> ventas, @RequestParam double porcentaje){
        
        //Valido el porcentaje entre 0 y 100 con un ApiResponse
        if(porcentaje < 0 || porcentaje > 100){
            ApiResponse<Map<String, Object>> error = new ApiResponse<>();
            error.setStatus(400);
            error.setMessege("El porcentaje no es valido debe estar entre 0 y 100");
            error.setData(null);
            return error;
        }

        double totalConDescuento = 0;

        //Aqui calculo el desceunto para cada venta en el caso de que el porcentaje sea valido
        for(VentasDTO venta: ventas){
            double importeBase = venta.getCantidad() * venta.getPrecioUnitario();
            double descuento = importeBase * (porcentaje / 100);
            double montoFinal = importeBase - descuento;

            venta.setMontoConDescuento(montoFinal);
            totalConDescuento = totalConDescuento + montoFinal;
        }

        //Si la respuesta feu exitosa, la empaqueto en un hashMap
        Map<String, Object> data = new HashMap<>();
        data.put("Ventas", ventas);
        data.put("Total con decuento", totalConDescuento);

        ApiResponse<Map<String , Object>> respuesta = new ApiResponse<>();
        respuesta.setStatus(200);
        respuesta.setMessege("Operación realizada con exito");
        respuesta.setData(data);

        return respuesta;
    }
    
}
