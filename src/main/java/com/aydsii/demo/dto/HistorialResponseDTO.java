package com.aydsii.demo.dto;

import java.time.LocalDateTime;

public class HistorialResponseDTO {
    private LocalDateTime fecha;
    private Double tasaCambio;

    public HistorialResponseDTO(LocalDateTime fecha, Double tasaCambio) {
        this.fecha = fecha;
        this.tasaCambio = tasaCambio;
    }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public Double getTasaCambio() { return tasaCambio; }
    public void setTasaCambio(Double tasaCambio) { this.tasaCambio = tasaCambio; }
}