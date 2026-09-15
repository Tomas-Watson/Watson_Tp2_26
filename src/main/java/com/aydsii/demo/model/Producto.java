package com.aydsii.demo.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class Producto {
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    private String categoria;

    @Positive(message = "El precio debe ser mayor a 0")
    private double precio;

    @Min(value = 0, message = "El stock debe ser mayor o igual a 0")
    private int stock;

    public Producto(){

    }

    public Producto(Long id, String nombre, String categoria, double precio, int stock){
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    public void setId(Long id){this.id = id;}
    public void setNombre(String nombre){this.nombre = nombre;}
    public void setCategoria(String categoria){this.categoria = categoria;}
    public void setPrecio(double precio){this.precio = precio;}
    public void setStock(int stock){this.stock = stock;}

    public Long getId(){return this.id;}
    public String getNombre(){return this.nombre;}
    public String getCategoria(){return this.categoria;}
    public double getPrecio(){return this.precio;}
    public int getStock(){return this.stock;}
}
