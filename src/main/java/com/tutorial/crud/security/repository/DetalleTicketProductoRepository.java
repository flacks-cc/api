package com.tutorial.crud.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tutorial.crud.entity.DetalleTicketProducto;

public interface DetalleTicketProductoRepository extends JpaRepository<DetalleTicketProducto, Long> {
}
