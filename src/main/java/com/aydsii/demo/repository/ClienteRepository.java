package com.aydsii.demo.repository;

import com.aydsii.demo.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    /*
    *Metodo personalizado: Spring hace un "SELECT count(*) > 0
    *FROM clientes WHERE email = ?" automaticamente
     */
    boolean existsByEmail(String email);
}
