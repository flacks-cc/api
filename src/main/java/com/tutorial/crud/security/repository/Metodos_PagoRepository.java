package com.tutorial.crud.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorial.crud.entity.Metodo_Pago;

@Repository
public interface Metodos_PagoRepository extends JpaRepository<Metodo_Pago, Integer> {

    Optional<Metodo_Pago> findByMetodoNombre(String nombre);

    boolean existsByMetodoNombre(String nombre);
    
    List<Metodo_Pago> findAll();

}
