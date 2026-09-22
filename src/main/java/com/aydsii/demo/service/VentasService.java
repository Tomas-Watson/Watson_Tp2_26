package com.aydsii.demo.service;

import com.aydsii.demo.dto.VentasDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service // Esta anotación es clave para que Spring sepa que esta es la "cocina"
public class VentasService {

    public Map<String, Object> calcularEstadisticas(List<VentasDTO> ventas) {
        double totalFacturado = 0;
        double totalUnitario = 0;
        VentasDTO ventaMayor = null;
        VentasDTO ventaMenor = null;
        Map<String, Integer> cantidadesPorProducto = new HashMap<>();

        for(VentasDTO venta: ventas){
            totalUnitario = venta.getCantidad() * venta.getPrecioUnitario();
            totalFacturado += totalUnitario;

            if (ventaMayor == null || totalUnitario > (ventaMayor.getCantidad() * ventaMayor.getPrecioUnitario())) {
                ventaMayor = venta;
            }

            if (ventaMenor == null || totalUnitario < (ventaMenor.getCantidad() * ventaMenor.getPrecioUnitario())) {
                ventaMenor = venta;
            }

            cantidadesPorProducto.put(venta.getProducto(), cantidadesPorProducto.getOrDefault(venta.getProducto(), 0) + venta.getCantidad());
        }

        int cantidadVentas = ventas.size();
        double ticketPromedio = totalFacturado / cantidadVentas;

        String productoMasVendido = "";
        int maxCantidad = 0;
        for (Map.Entry<String, Integer> entry : cantidadesPorProducto.entrySet()) {
            if (entry.getValue() > maxCantidad) {
                maxCantidad = entry.getValue();
                productoMasVendido = entry.getKey();
            }
        }

        Map<String, Object> data = new HashMap<>();
        data.put("totalFacturado", totalFacturado);
        data.put("cantidadVentas", cantidadVentas);
        data.put("ticketPromedio", ticketPromedio);
        data.put("ventaMayor", ventaMayor);
        data.put("ventaMenor", ventaMenor);
        data.put("productoMasVendido", productoMasVendido);

        return data;
    }

    public Map<String, Object> procesarDescuento(List<VentasDTO> ventas, double porcentaje) {
        double totalConDescuento = 0;

        for(VentasDTO venta: ventas){
            double importeBase = venta.getCantidad() * venta.getPrecioUnitario();
            double descuento = importeBase * (porcentaje / 100);
            double montoFinal = importeBase - descuento;

            venta.setMontoConDescuento(montoFinal);
            totalConDescuento += montoFinal;
        }

        Map<String, Object> data = new HashMap<>();
        data.put("Ventas", ventas);
        data.put("Total con descuento", totalConDescuento);

        return data;
    }
}