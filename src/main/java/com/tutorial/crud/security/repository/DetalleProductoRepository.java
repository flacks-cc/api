package com.tutorial.crud.security.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tutorial.crud.entity.DetalleProducto;
import com.tutorial.crud.entity.Reserva;

public interface DetalleProductoRepository extends JpaRepository<DetalleProducto, Long> {
	
    List<DetalleProducto> findByReserva(Reserva reserva);
}
