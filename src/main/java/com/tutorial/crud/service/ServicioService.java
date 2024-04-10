package com.tutorial.crud.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tutorial.crud.entity.Servicio;
import com.tutorial.crud.security.repository.ServicioRepository;

@Service
@Transactional
public class ServicioService {

    @Autowired
    ServicioRepository servicioRepository;

    // Lista todos los servicios
    public List<Servicio> findAll() {
        return servicioRepository.findAll();
    }

    // Encuentra un servicio por su ID
    public Optional<Servicio> findById(Long idServicio) {
    	return servicioRepository.findById(idServicio);
    }
    
    // Obtiene un servicio por su nombre
    public Optional<Servicio> getByNombre(String nombre) {
        return servicioRepository.findByNombre(nombre);
    }
    
    // Guarda un servicio
    public void save(Servicio servicio) {
        servicioRepository.save(servicio);
    }

    // Elimina un servicio por su ID
    public void deleteById(Long idServicio) {
        servicioRepository.deleteById(idServicio);
    }

    // Verifica si un servicio existe por su ID
    public boolean existsById(Long idServicio) {
        return servicioRepository.existsById(idServicio);
    }

    // Verifica si un servicio existe por su nombre
    public boolean existsByNombre(String nombre) {
        return servicioRepository.existsByNombre(nombre);
    }
}
