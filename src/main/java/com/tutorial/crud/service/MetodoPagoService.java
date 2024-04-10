package com.tutorial.crud.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tutorial.crud.entity.MetodoPago;
import com.tutorial.crud.security.repository.MetodoPagoRepository;

@Service
@Transactional
public class MetodoPagoService {

	@Autowired
	private MetodoPagoRepository metodoPagoRepository;

	public List<MetodoPago> findAll() {
		return metodoPagoRepository.findAll();
	}

	public Optional<MetodoPago> findById(Long idMetodoPago) {
		return metodoPagoRepository.findById(idMetodoPago);
	}

	public Optional<MetodoPago> getByNombre(String nombre) {
		return metodoPagoRepository.findByNombre(nombre);
	}

	public void save(MetodoPago metodoPago) {
		metodoPagoRepository.save(metodoPago);
	}

	public void deleteById(Long idMetodoPago) {
		metodoPagoRepository.deleteById(idMetodoPago);
	}

	public boolean existsById(Long idMetodoPago) {
		return metodoPagoRepository.existsById(idMetodoPago);
	}

	public boolean existsByNombre(String nombre) {
		return metodoPagoRepository.existsByNombre(nombre);
	}
}