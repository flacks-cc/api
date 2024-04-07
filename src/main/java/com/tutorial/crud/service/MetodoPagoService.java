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

    public List<MetodoPago> list(){
        return metodoPagoRepository.findAll();
    }

    public Optional<MetodoPago> getOne(int id){
        return metodoPagoRepository.findById(id);
    }

    public Optional<MetodoPago> getByMetodoNombre(String nombre){
        return metodoPagoRepository.findByMetodoNombre(nombre);
    }
    
    public List<MetodoPago> findAll() {
        return metodoPagoRepository.findAll();
    }

    public void save(MetodoPago metodoPago){
        metodoPagoRepository.save(metodoPago);
    }

    public void delete(int id){
        metodoPagoRepository.deleteById(id);
    }

    public boolean existsById(int id){
        return metodoPagoRepository.existsById(id);
    }
    
    public Optional<MetodoPago> findById(int id) {
        return metodoPagoRepository.findById(id);
    }

    public boolean existsByMetodoNombre(String nombre){
        return metodoPagoRepository.existsByMetodoNombre(nombre);
    }
}