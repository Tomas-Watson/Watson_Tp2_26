package com.aydsii.demo.dto;

public class ConversionResponse {
    private double montoOriginal;
    private String monedaOrigen;
    private String monedaDestino;
    private double tasaCambio;
    private double montoConvertido;
    private String fecha;

    public ConversionResponse(double montoOriginal, String monedaOrigen, String monedaDestino, double tasaCambio, double montoConvertido, String fecha){
        this.montoOriginal = montoOriginal;
        this.monedaOrigen = monedaOrigen;
        this.monedaDestino = monedaDestino;
        this.tasaCambio = tasaCambio;
        this.montoConvertido = montoConvertido;
        this.fecha = fecha;
    }

    //Setters
    public void setMontoOriginal(double montoOriginal){this.montoOriginal = montoOriginal;}
    public void setMonedaOrigen(String monedaOrigen){this.monedaOrigen = monedaOrigen;}
    public void setMonedaDestino(String monedaDestino){this.monedaDestino = monedaDestino;}
    public void setTasaCambio(double tasaCambio){this.tasaCambio = tasaCambio;}
    public void setMontoConvertido(double montoConvertido){this.montoConvertido = montoConvertido;}
    public void setFecha(String fecha){this.fecha = fecha;}

    //Getters 
    public double getMontoOriginal(){return this.montoOriginal;}
    public String getMonedaOrigen(){return this.monedaOrigen;}
    public String getMonedaDestino(){return this.monedaDestino;}
    public double getTasaCambio(){return this.tasaCambio;}
    public double getMontoConvertido(){return montoConvertido;}
    public String getFecha(){return this.fecha;}
}
