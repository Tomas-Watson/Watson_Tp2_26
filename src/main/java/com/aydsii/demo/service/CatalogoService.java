package com.aydsii.demo.service;

import com.aydsii.demo.model.Categoria;
import com.aydsii.demo.model.Producto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;
import java.util.Comparator;


/*
*La anotacion @Service sirve par aque SpringBoot lo detecte automaticamente.
*/
@Service
public class CatalogoService {
    private List<Producto> productos = new ArrayList<>();

    public CatalogoService(){

        Categoria catPerifericos = new Categoria();
        catPerifericos.setNombre("Perifericos");

        Categoria catMonitores = new Categoria();
        catMonitores.setNombre("Monitores");

        Categoria catComponentes = new Categoria();
        catComponentes.setNombre("Componentes");

        Categoria catMueble = new Categoria();
        catMueble.setNombre("Mueble");

        
        productos.add(new Producto(1L, "Mouse Inalambrico", catPerifericos, 15000.0, 40));
        productos.add(new Producto(2L, "Teclado Mecanico", catPerifericos, 45000.0, 59));
        productos.add(new Producto(3L, "Monitor 24 pulgadas", catMonitores, 150000.0, 12));
        productos.add(new Producto(4L, "Auriculares Gaming", catPerifericos, 50000.0, 63));
        productos.add(new Producto(5L, "Memoria RAM 16GB", catComponentes, 84000.0, 27));
        productos.add(new Producto(6L, "Disco SSD 1TB", catComponentes, 145000.0, 27));
        productos.add(new Producto(7L, "Gabinete ATX", catComponentes, 30000.0, 31));
        productos.add(new Producto(8L, "Silla Ergonomica", catMueble, 200000.0, 48));
    }

    public List<Producto> obtenerTodos() {
        return productos;
    }

    public List<Producto> buscarProductos(String categoria, Double precioMin, Double precioMax){
        return productos.stream()
            //Si la categoria, precio minimo o precio maximo son nulos, entonces no filtra
            //Si viene texto buscara coincidencias excatas
            .filter(p -> categoria == null || (p.getCategoria() != null && p.getCategoria().getNombre().equalsIgnoreCase(categoria)))
                .filter(p -> precioMin == null || p.getPrecio() >= precioMin)
                .filter(p -> precioMax == null || p.getPrecio() <= precioMax)
                .collect(Collectors.toList());
    }

    public List<Producto> ordenarProductos(String criterio, String orden){
        Comparator<Producto> comparador;

        if("nombre".equalsIgnoreCase(criterio)){
            comparador = Comparator.comparing(Producto::getNombre);

        } else {
            comparador = Comparator.comparing(Producto::getPrecio);
        }

        if ("desc".equalsIgnoreCase(orden)) {
            comparador = comparador.reversed();
        }

        // Aplicamos el sort usando Streams
        return productos.stream().sorted(comparador).collect(Collectors.toList());
    }

    public Producto agregarProducto(Producto nuevo){
        long nuevoId = productos.stream().mapToLong(Producto::getId).max().orElse(0) + 1;

        nuevo.setId(nuevoId);
        productos.add(nuevo);
        return nuevo;
    }

    public Producto buscarPorId(Long id){
        return productos.stream().filter(p-> p.getId().equals(id)).findFirst().orElse(null);
    }

    public boolean eliminarProducto(long id){
        return productos.removeIf(p->p.getId().equals(id));
    }

}
