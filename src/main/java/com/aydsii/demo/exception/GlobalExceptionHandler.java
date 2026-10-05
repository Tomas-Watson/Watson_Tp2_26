package com.aydsii.demo.exception;

import com.aydsii.demo.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
*Con @RestControllerAdvice le estoy diciendo a Spring Boot 
*que esta clase se encargara de interceptar errores globales
*para todos mis endpoints, respondiendo en formato JSON 
*/
@RestControllerAdvice 
public class GlobalExceptionHandler {

    /*
    *@exceptionHandler captura especificamente el error MethodArgumentNotValid
    *que se genera cuando falla una anotación @Valid
    */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // <-- Asegura que Swagger y Postman lean un HTTP 400
    public ApiResponse<List<Map<String, String>>> manejarErroresDeValidacion(MethodArgumentNotValidException ex){

        List<Map<String, String>> listaDeErrores = new ArrayList<>();

        for(FieldError error : ex.getBindingResult().getFieldErrors()){
            Map<String, String> detalleError = new HashMap<>();

            detalleError.put("campo_y_posicion", error.getField());
            detalleError.put("motivo", error.getDefaultMessage());

            listaDeErrores.add(detalleError);
        }

        ApiResponse<List<Map<String, String>>> respuesta = new ApiResponse<>();
        respuesta.setStatus(400);
        respuesta.setMessege("Error de validacion en los datos ingresados");
        respuesta.setData(listaDeErrores);

        return respuesta;
    }

    
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) 
    public ApiResponse<Object> manejarArgumentosInvalidos(IllegalArgumentException ex) {
        
        ApiResponse<Object> respuesta = new ApiResponse<>();
        respuesta.setStatus(400);
        respuesta.setMessege(ex.getMessage()); // Captura automáticamente "El email ya esta registrado"
        respuesta.setData(null); // El TP pide que data sea null en este caso específico
        
        return respuesta;
    }
}