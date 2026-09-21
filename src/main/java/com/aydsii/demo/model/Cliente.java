package com.aydsii.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "clientes") //Le indica a Hibernate el nombre exacto de la tabla de SQL
public class Cliente {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Con esto la base de datos generará el ID automaticamente
    private Long id;

    private String nombre;
    private String apellido;
    private String email;
    private String telefono;

    @Column(name = "fecha_registro") //Relaciona el atributo JAVA con el nombre de la columna en SQL
    private LocalDateTime fechaRegistro;

    public Cliente (){

    }

    public Cliente(String nombre, String apellido, String email, String telefono){
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
    }

    //Setters
    public void setId(Long id){this.id = id;}
    public void setNombre(String nombre){this.nombre = nombre;}
    public void setApellido(String apellido){this.apellido = apellido;}
    public void setTelefono(String telefono){this.telefono = telefono;}
    public void setEmail(String email){this.email = email;}
    public void setFechaRegistro(LocalDateTime fechaRegistro){this.fechaRegistro = fechaRegistro;}

    //Getters
    public String getNombre(){return this.nombre;}
    public String getApellido(){return this.apellido;}
    public String getTelefono(){return this.telefono;}
    public String getEmail(){return this.email;}
    public Long getId(){return this.id;}
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
}
