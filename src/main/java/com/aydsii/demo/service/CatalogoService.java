package com.aydsii.demo.service;

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
        productos.add(new Producto(1L, "Mouse Inalambrico", "Perifericos", 15000, 40));
        productos.add(new Producto(2L, "Teclado Mecanico", "Perifericos", 45000, 59));
        productos.add(new Producto(3L, "Monitor 24 pulgadas", "Monitores", 150000, 12));
        productos.add(new Producto(4L, "Auriculares Gaming", "Perifericos", 50000, 63));
        productos.add(new Producto(5L, "Memoria RAM 16GB", "Componentes", 84000, 27));
        productos.add(new Producto(6L, "Disco SSD 1TB", "Componentes", 145000, 27));
        productos.add(new Producto(7L, "Gabinete ATX", "Componentes", 30000, 31));
        productos.add(new Producto(8L, "Silla Ergonomica", "Mueble", 200000, 48));
    }

    public List<Producto> obtenerTodos() {
        return productos;
    }

    public List<Producto> buscarProductos(String categoria, Double precioMin, Double precioMax){
        return productos.stream()
            //Si la categoria, precio minimo o precio maximo son nulos, entonces no filtra
            //Si viene texto buscara coincidencias excatas
            .filter(p-> categoria == null || p.getCategoria().equalsIgnoreCase(categoria))
            //si viene un numero buscara que se mayor o igual
            .filter(p-> precioMin == null || p.getPrecio() >= precioMin)
            //si viene un numero buscara que sea menor o igual
            .filter(p-> precioMax == null || p.getPrecio() <= precioMax)
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
