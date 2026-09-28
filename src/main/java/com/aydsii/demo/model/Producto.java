package com.aydsii.demo.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import jakarta.persistence.*;

/*
*Con @Entity le estoy indicando la clase Java, en este caso "Producto"
*representa una tabla en la base de datos.
*
*@Table se usa para vincular una clase de entidad Java con una tabla 
*especifica en la base de datos relacional. Por lo tanto, debo usa dicha
*anotacion luego de haber declarado la anotacion @Entity
*/
@Entity
@Table(name = "productos") //name indica el nombre que tendra la tabla
public class Producto {

    /*
    *La anotacion @Id marca un campo como la clave primaria de una entidad
    *identificando de forma unica cada registro en la tabla de la base de datos
    *
    *La anotacion @GeneratedValue se usa la anotacion @Id para indicar que el 
    *valor de la clave primaria se generara de forma automatica por la base de 
    *datos
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    *Con @NotBlank le estoy indicando al atributo que no debe estar vacio
    *No debo declara nada dentro de los parentesis, mas que un mensaje 
    */
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    /*
    *Con @ManyToOne le estoy indicando la cardinalidad que tendra la tabla 
    *en la base de datos. La tabla sera el atributo que declare debajo del @
    *
    */
    @ManyToOne
    @JoinColumn(name = "categoria_id") // Clave foránea en la tabla productos
    private Categoria categoria;

    /*
    *Con @Positive le indico al atributo que debe ser positivo 
    *No es necesario declarar un valor y ademas puede lanzar 
    *un mensaje 
    */
    @Positive(message = "El precio debe ser mayor a 0")
    private double precio;

    /*
    *Con @Min verifico que el atributo contenga un valor minimo que yo le especifique 
    *Ademas puede lanzar un mensaje
    */
    @Min(value = 0, message = "El stock debe ser mayor o igual a 0")
    private int stock;

    private String descripcion;

    public Producto(){

    }

    public Producto(Long id, String nombre, Categoria categoria, double precio, int stock){
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    public void setId(Long id){this.id = id;}
    public void setNombre(String nombre){this.nombre = nombre;}
    public void setCategoria(Categoria categoria){this.categoria = categoria;}
    public void setPrecio(double precio){this.precio = precio;}
    public void setStock(int stock){this.stock = stock;}
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Long getId(){return this.id;}
    public String getNombre(){return this.nombre;}
    public Categoria getCategoria(){return this.categoria;}
    public double getPrecio(){return this.precio;}
    public int getStock(){return this.stock;}
    public String getDescripcion() { return descripcion; }
}
