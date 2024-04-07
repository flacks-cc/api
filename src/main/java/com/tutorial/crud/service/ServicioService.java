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

    public List<Servicio> list() {
        return servicioRepository.findAll();
    }

    public Optional<Servicio> getOne(int id) {
        return servicioRepository.findById(id);
    }

    public Optional<Servicio> getByNombre(String nombre) {
        return servicioRepository.findByNombre(nombre);
    }
    
    public Optional<Servicio> findById(int id) {
        return servicioRepository.findById(id);
    }

    public void save(Servicio servicio) {
        servicioRepository.save(servicio);
    }

    public void delete(int id) {
        servicioRepository.deleteById(id);
    }

    public boolean existsById(int id) {
        return servicioRepository.existsById(id);
    }

    public boolean existsByNombre(String nombre) {
        return servicioRepository.existsByNombre(nombre);
    }
}
