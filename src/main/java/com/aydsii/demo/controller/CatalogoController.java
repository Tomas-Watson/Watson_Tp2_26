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

/*
*
* La anotacion @RestController marca una clase  como un controlador web
* donde los metodos devuelven datos directamente (como JSON o XML) en 
* lugar de una vista HTML
*
* 
*/
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
    /*
    *
    * La anotacion @RequestParam en JAVA sirve para extraer parametros de una solicitud HTTP
    * (Como los valores que van en la URL despues del signo ? o datos enviados en un form)
    * y asignarlos directamente a los argumentos de un metodo o controlador
    */
    public ApiResponse <List<Producto>> buscarProductos(@RequestParam(required = false) String categoria,@RequestParam(required = false) Double precioMin,@RequestParam(required = false) Double precioMax){

            
        List<Producto> filtrados = catalogoService.buscarProductos(categoria, precioMin, precioMax);

        ApiResponse <List<Producto>> respuesta = new ApiResponse<>();
        respuesta.setStatus(200);
        respuesta.setMessege("Operacción realizada con exito");
        respuesta.setData(filtrados);

        return respuesta;
    }

    /*
    *
    * La anotacion @GetMapping sirve para mappear solicitudes HTTP del tipo 
    * GET a un metodo especifico dentro del controlador
    * 
    * Tambien se le debe pasar una URI como a toda solicitud HTTP
    *  
    */
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
    
    /*
    *
    * La anotacion @PostMapping sirve para mappear solicitudes HTTP del tipo 
    * POST a un metodo especifico dentro del controlador
    * 
    * Tambien se le debe pasar una URI como a toda solicitud HTTP
    *  
    */
    @Operation(summary = "Agregar producto", description = "Añade un nuevo producto al catalogo")
    @PostMapping("/api/catalogo")

    /*
    * 
    * Las anotaciones de @RequestBody y @Valid se usan juntas para recibir y validar datos
    * enviados por el cliente en formato JSON dentro de una peticion HTTP
    * 
    * Por si solas : 
    * 
    * @ResquestBody convierte el cuerpo HTTP, es decir, toma los datos en formato JSON
    * que viajan en el cuerpo de una peticion como un POST o un PUT y los transforma
    * automaticamente en un objeto JAVA (un DTO o un BEAN)
    * 
    * Deserializa, es decir, mapea las propiedades del JSON con los atributos de la clase 
    * JAVA correspondiente
    * 
    * @Valid activa las validaciones, es decir, le indica a Spring Boot que debe revisar 
    * si el objeto recibido cumple con las reglas de validacion definidas en la clase
    * Dichas reglas son (@NotNull, @Size, @Email, etc ... )
    * 
    * Maneja errores, es decir, si algun dato no cumple con las reglas, se detiene la 
    * ejecucion del metodo y se lanza una exception que genera automaticamente una 
    * respuesta HTTP 400 (Bad Request).
    * 
    */
    public ApiResponse<Producto> agregarProducto(@Valid @RequestBody Producto producto){
        Producto nuevo = catalogoService.agregarProducto(producto);

        ApiResponse<Producto> respuesta = new ApiResponse<>();
        respuesta.setStatus(201);
        respuesta.setMessege("Operación realizada con exito");
        respuesta.setData(nuevo);
        return respuesta;
    }

    /*
    *
    * La anotacion @Operation sirve para documentar y describir el endpoint
    * individual de una API REST mediantemetadatos de la especificacion OpenAPI
    * 
    * Summary es un atributo de la anotacion que me resume lo que hara el metodo
    * del controlador
    * 
    * Description es un atributo que sirve para describir lo que hara el metodo
    * del controlador 
    * 
    * La anotacion @PutMapping se utiliza para mappear solictudes HTTP del tipo
    * PUT hacia un metodo en especifico en un controlador
    * En la anotacion debo pasarle la URI
    * 
    * Una URI (Identificador Uniforme de Recursos), es una cadena corta de 
    * caracteres que identifica de forma unica un recurso fisico o abstracto
    * en una red
    * 
    * Ejemplo : @PutMapping("/api/catalogo/{id}/stock"), la URI : "/api/catalogo/{id}/stock"
    */
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

    /*
    *
    * La anotacion @PathVariable se utiliza para extraer valores directamentede la URL
    * de una peticion HTTP y asignarlos a los parametros de una metodo en un controlador
    * 
    * La anotacion @DeleteMapping se utiliza para mapear solicitudes HTTP DELETE a 
    * metodos especificos de un controlador
    */
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
