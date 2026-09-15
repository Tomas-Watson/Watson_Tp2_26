package com.aydsii.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/*
*Para el texto no vacío usamos @NotBlank, este se asegura de que el string no sea nulo, no este vacío
*y no sean solo espacios en blanco.
*Para los numeros positivos usamos @Positive el cual verifica que los números sean mayores a cero
*/

public class VentasDTO {

    //Con esto cumplo las validaciones requeridas
    @Positive(message = "La cantidad debe ser amyor a cero")
    private int cantidad;

    @NotBlank(message = "El producto no puede estar vacío")
    private String producto;

    @Positive(message = "El precio unitario debe ser mayor a 0(cero)")
    private double precioUnitario;

    //Ejercicio 1 - Campo para el endpoint 2
    private double montoConDescuento;

    public VentasDTO() {
    }

    //Constructor
    public VentasDTO(int cantidad, String producto, double precioUnitario){
        this.cantidad = cantidad;
        this.producto = producto;
        this.precioUnitario = precioUnitario;
    }

    //Setters
    public void setCantidad(int cantidad){this.cantidad = cantidad;}
    public void setProducto(String producto){this.producto = producto;}
    public void setPrecioUnitario(double precioUnitario){this.precioUnitario = precioUnitario;}
    public void setMontoConDescuento(double montoConDescuento){this.montoConDescuento = montoConDescuento;}

    //Getters
    public int getCantidad(){return this.cantidad;}
    public double getPrecioUnitario(){return this.precioUnitario;}
    public String getProducto(){return this.producto;}
    public double getMontoConDescuento(){return this. montoConDescuento;}


}
