package com.tutorial.crud.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tutorial.crud.entity.DetalleTicketProducto;
import com.tutorial.crud.security.repository.DetalleTicketProductoRepository;

@Service
@Transactional
public class DetalleTicketProductoService {

	@Autowired
	DetalleTicketProductoRepository detalleTicketProductoRepository;

	// Lista todos los detalles de tickets de productos
	public List<DetalleTicketProducto> findAll() {
		return detalleTicketProductoRepository.findAll();
	}

	// Obtiene un detalle de ticket de producto por su ID
	public Optional<DetalleTicketProducto> findById(Long idDetalleTicketProducto) {
		return detalleTicketProductoRepository.findById(idDetalleTicketProducto);
	}

	// Guarda un detalle de ticket de producto
	public void save(DetalleTicketProducto detalleTicketProducto) {
		detalleTicketProductoRepository.save(detalleTicketProducto);
	}

	// Elimina un detalle de ticket de producto por su ID
	public void deleteById(Long idDetalleTicketProducto) {
		detalleTicketProductoRepository.deleteById(idDetalleTicketProducto);
	}

	// Verifica si un detalle de ticket de producto existe por su ID
	public boolean existsById(Long idDetalleTicketProducto) {
		return detalleTicketProductoRepository.existsById(idDetalleTicketProducto);
	}
}
