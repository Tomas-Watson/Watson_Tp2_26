package com.aydsii.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ClienteDTO {

    private Long id;

    @NotBlank(message = "El nombre no debe estar vacio")
    @Size(min = 2, message = "El nombre debe tener al menos dos caracteres")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacio")
    @Size(min = 2, message = "El apellido debe tener al emnos dos caracteres")
    private String apellido;

   @Pattern(regexp = "\\d*", message = "El teléfono solo debe contener dígitos")
    private String telefono; 

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un email válido")
    private String email;

    public void setId(Long id){this.id = id;}
    public void setNombre(String nombre){this.nombre = nombre;}
    public void setApellido(String apellido){this.apellido = apellido;}
    public void setTelefono(String telefono){this.telefono = telefono;}
    public void setEmail(String email){this.email = email;}

    public Long getId(){return this.id;}
    public String getNombre(){return this.nombre;}
    public String getApellido(){return this.apellido;}
    public String getTelefono(){return this.telefono;}
    public String getEmail(){return this.email;}
}
