package com.aydsii.demo.controller;

import com.aydsii.demo.dto.ApiResponse;
import com.aydsii.demo.model.Producto;
import com.aydsii.demo.service.CatalogoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

@RestController 
public class CatalogoController {
    private final CatalogoService catalogoService;

    //Con esto inyecto dependencias para acceder al servicio
    public CatalogoController(CatalogoService catalogoService){
        this.catalogoService = catalogoService;
    }

    @Operation(summary = "Obtener catálogo", description = "Devuelve todos los productos disponibles.")
    @GetMapping("/api/catalogo")
    public ApiResponse<List<Producto>> obtenerCatalogo(){

        //Con esto solicito la lista al servicio
        List<Producto> productos = catalogoService.obtenerTodos();

        //Empaqueto todo en el formato estandar
        ApiResponse<List<Producto>> respuesta = new ApiResponse<>();
        respuesta.setStatus(200);
        respuesta.setMessege("Operación realizada con exito");
        respuesta.setData(productos);

        return respuesta;
    }

    @Operation(summary = "Buscar productos", description = "Filtra el catalogo por categoria y rango de precios")
    @GetMapping("/api/catalogo/buscar")
    public ApiResponse <List<Producto>> buscarProductos(@RequestParam(required = false) String categoria,@RequestParam(required = false) Double precioMin,@RequestParam(required = false) Double precioMax){

            
        List<Producto> filtrados = catalogoService.buscarProductos(categoria, precioMin, precioMax);

        ApiResponse <List<Producto>> respuesta = new ApiResponse<>();
        respuesta.setStatus(200);
        respuesta.setMessege("Operacción realizada con exito");
        respuesta.setData(filtrados);

        return respuesta;
    }

    @Operation(summary = "Ordenar productos", description = "Ordena el catálogo por precio o nombre")
    @GetMapping("/api/catalogo/ordenar")
    public ApiResponse<List<Producto>> ordenarProducto(@RequestParam String criterio, @RequestParam(required = false, defaultValue = "asc") String orden){
        List<Producto> ordenados = catalogoService.ordenarProductos(criterio, orden);

        ApiResponse<List<Producto>> respuesta = new ApiResponse<>();
        respuesta.setStatus(200);
        respuesta.setMessege("Operación realizada con exito");
        respuesta.setData(ordenados);

        return respuesta;
    }
    

    @Operation(summary = "Agregar producto", description = "Añade un nuevo producto al catalogo")
    @PostMapping("/api/catalogo")
    public ApiResponse<Producto> agregarProducto(@Valid @RequestBody Producto producto){
        Producto nuevo = catalogoService.agregarProducto(producto);

        ApiResponse<Producto> respuesta = new ApiResponse<>();
        respuesta.setStatus(201);
        respuesta.setMessege("Operación realizada con exito");
        respuesta.setData(nuevo);
        return respuesta;
    }

    @Operation(summary = "Modificar stock", description = "Suma o resta stock a un producto existente")
@PutMapping("/api/catalogo/{id}/stock")
    public ApiResponse<Producto> modificarStock(@PathVariable Long id, @RequestParam int cantidad){
        Producto producto = catalogoService.buscarPorId(id);

        ApiResponse<Producto> respuesta = new ApiResponse<>();

        if(producto == null){
            respuesta.setStatus(404);
            respuesta.setMessege("Producto no encontrado");
            return respuesta;
        }

        if(producto.getStock() + cantidad < 0){
            respuesta.setStatus(400);
            respuesta.setMessege("El stock no puede quedar debajo de 0");
            return respuesta;
        }

        producto.setStock(producto.getStock() + cantidad);

        respuesta.setStatus(200);
        respuesta.setMessege("Stock actualizado con exito");
        respuesta.setData(producto);
        return respuesta;
    }

    @Operation(summary = "Eliminar producto", description = "Eliminar un producto por su Id")
    @DeleteMapping("/api/catalogo/{id}")
    public ApiResponse<Object> eliminarProducto(@PathVariable Long id){
        boolean eliminado = catalogoService.eliminarProducto(id);
        ApiResponse<Object> respuesta = new ApiResponse<>();

        if(eliminado){
            respuesta.setStatus(200);
            respuesta.setMessege("Operacion realizada con exito");
        } else {
            respuesta.setStatus(404);
            respuesta.setMessege("Producto no encontrado");
        }
        return respuesta;
    }
}
