package com.tutorial.crud.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tutorial.crud.entity.Producto;
import com.tutorial.crud.security.repository.ProductoRepository;

@Service
@Transactional
public class ProductoService {

	@Autowired
	ProductoRepository productoRepository;

	// Lista todos los productos disponibles
	public List<Producto> findAll() {
		return productoRepository.findAll();
	}

	// Obtiene un producto por su ID
	public Optional<Producto> findById(Long idProducto) {
		return productoRepository.findById(idProducto);
	}

	// Obtiene un producto por su nombre
	public Optional<Producto> findByNombre(String nombre) {
		return productoRepository.findByNombre(nombre);
	}

	// Guarda un nuevo producto o actualiza uno existente
	public void save(Producto producto) {
		productoRepository.save(producto);
	}

	// Elimina un producto por su ID
	public void deleteById(Long idProducto) {
		productoRepository.deleteById(idProducto);
	}

	// Verifica si un producto existe por su ID
	public boolean existsById(Long idProducto) {
		return productoRepository.existsById(idProducto);
	}

	// Verifica si un producto existe por su nombre
	public boolean existsByNombre(String nombre) {
		return productoRepository.existsByNombre(nombre);
	}
}
