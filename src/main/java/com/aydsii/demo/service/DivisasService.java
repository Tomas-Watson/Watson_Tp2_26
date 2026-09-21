package com.aydsii.demo.service;

import com.aydsii.demo.dto.ConversionResponse;
import com.aydsii.demo.dto.HistorialResponseDTO;
import com.aydsii.demo.model.HistorialConversion;
import com.aydsii.demo.repository.HistorialConversionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DivisasService {
    private final HistorialConversionRepository repository;
    private final RestClient restClient;

    public DivisasService(HistorialConversionRepository repository) {
        this.repository = repository;
        // Inicializamos el cliente HTTP apuntando a Frankfurter
        this.restClient = RestClient.builder().baseUrl("https://api.frankfurter.app").build();
    }

    public ConversionResponse consultarYGuardar(String origen, String destino, Double monto) {
        // 1. Llamar a la API externa
        Map respuestaApi = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/latest")
                        .queryParam("amount", monto)
                        .queryParam("from", origen)
                        .queryParam("to", destino)
                        .build())
                .retrieve()
                .body(Map.class);

        // 2. Extraer los datos
        Map<String, Double> rates = (Map<String, Double>) respuestaApi.get("rates");
        Double montoConvertido = rates.get(destino);
        Double tasaCambio = montoConvertido / monto; 

        // 3. Guardar en MySQL
        HistorialConversion historial = new HistorialConversion();
        historial.setMonedaOrigen(origen);
        historial.setMonedaDestino(destino);
        historial.setMonto(monto);
        historial.setMontoConvertido(montoConvertido);
        historial.setTasa(tasaCambio);
        historial.setFechaConsulta(LocalDateTime.now());
        repository.save(historial);

        // 4. Armar el DTO de respuesta
        ConversionResponse response = new ConversionResponse();
        response.setMontoOriginal(monto);
        response.setMonedaOrigen(origen);
        response.setMonedaDestino(destino);
        response.setTasaCambio(tasaCambio);
        response.setMontoConvertido(montoConvertido);
        response.setFecha(historial.getFechaConsulta().toLocalDate().toString());

        return response;
    }

    public List<HistorialResponseDTO> obtenerHistorial(String origen, String destino) {
        List<HistorialConversion> lista = repository.findByMonedaOrigenAndMonedaDestinoOrderByFechaConsultaDesc(origen, destino);
        
        return lista.stream()
                .map(h -> new HistorialResponseDTO(h.getFechaConsulta(), h.getTasa()))
                .collect(Collectors.toList());
    }
}
