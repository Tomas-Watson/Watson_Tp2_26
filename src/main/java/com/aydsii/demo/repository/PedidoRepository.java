package com.aydsii.demo.repository;


import com.aydsii.demo.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    /*
    *
    * La anotacion @Query se usa para definir consultas personalizadas
    * directamente en los metodos de las interfaces de un repositorio
    * 
    */
    @Query("""
        SELECT DISTINCT p 
        FROM Pedido p 
        JOIN p.cliente c 
        JOIN p.detalles d 
        JOIN d.producto pr 
        JOIN pr.categoria cat 
        WHERE (:clienteId IS NULL OR c.id = :clienteId) 
        AND (:categoria IS NULL OR cat.nombre = :categoria) 
        AND (:fechaDesde IS NULL OR p.fechaPedido >= :fechaDesde) 
        AND (:fechaHasta IS NULL OR p.fechaPedido <= :fechaHasta) 
        AND (:estado IS NULL OR p.estado = :estado)
    """)
    List<Pedido> buscarPedidos(


        /*
        *
        * La anotacion @Param se usa para vincular los argumentos de un 
        * metodo de un repositorio con los parametros con nombre en una 
        * consulta personalizada escrita en la anotacion
        * 
        */
        @Param("clienteId") Long clienteId,
        @Param("categoria") String categoria,
        @Param("fechaDesde") LocalDate fechaDesde,
        @Param("fechaHasta") LocalDate fechaHasta,
        @Param("estado") String estado
    );
}
