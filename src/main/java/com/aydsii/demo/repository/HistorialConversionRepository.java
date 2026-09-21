package com.aydsii.demo.repository;


import com.aydsii.demo.model.HistorialConversion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository 
public interface HistorialConversionRepository extends JpaRepository<HistorialConversion, Long>{
    List<HistorialConversion> findByMonedaOrigenAndMonedaDestinoOrderByFechaConsultaDesc(String origen, String destino);
}
