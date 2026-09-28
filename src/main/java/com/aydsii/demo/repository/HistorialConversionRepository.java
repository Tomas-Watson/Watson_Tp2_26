package com.aydsii.demo.repository;


import com.aydsii.demo.model.HistorialConversion;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface HistorialConversionRepository extends JpaRepository<HistorialConversion, Long>{
    List<HistorialConversion> findByMonedaOrigenAndMonedaDestinoOrderByFechaConsultaDesc(String origen, String destino);
}
