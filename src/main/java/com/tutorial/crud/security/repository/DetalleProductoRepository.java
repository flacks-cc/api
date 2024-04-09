package com.tutorial.crud.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tutorial.crud.entity.DetalleProducto;

@Repository
public interface DetalleProductoRepository extends JpaRepository<DetalleProducto, Integer> {
}
