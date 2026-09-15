package com.aydsii.demo.service;

import com.aydsii.demo.dto.ConversionResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.Map;

@Service
public class DivisasService {
    private final RestClient restClient;

    public DivisasService() {
        // Configuramos la URL base de la API externa
        this.restClient = RestClient.builder().baseUrl("https://api.frankfurter.app").build();
    }

    public ConversionResponse convertir(double monto, String origen, String destino) {
        // Hacemos la llamada HTTP a Frankfurter[cite: 1, 2]
        Map respuestaApi = restClient.get()
                .uri("/latest?amount={monto}&from={origen}&to={destino}", monto, origen, destino)
                .retrieve()
                .body(Map.class);

        // Extraemos el mapa de tasas
        Map<String, Object> rates = (Map<String, Object>) respuestaApi.get("rates");
        
        // Usamos Number para evitar que Java falle si la API devuelve un entero
        Number tasa = (Number) rates.get(destino);
        double montoConvertido = tasa.doubleValue();
        
        // Calculamos la tasa de cambio individual
        double tasaCambio = montoConvertido / monto;
        String fecha = (String) respuestaApi.get("date");

        return new ConversionResponse(monto, origen, destino, tasaCambio, montoConvertido, fecha);
    }
}
