package com.tutorial.crud.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutorial.crud.entity.Metodo_Pago;
import com.tutorial.crud.security.repository.Metodos_PagoRepository;

@Service
@Transactional
public class Metodo_PagoService {

    @Autowired
    private Metodos_PagoRepository metodos_PagoRepository;

    public List<Metodo_Pago> list(){
        return metodos_PagoRepository.findAll();
    }

    public Optional<Metodo_Pago> getOne(int idMetodoPago){
        return metodos_PagoRepository.findById(idMetodoPago);
    }

    public Optional<Metodo_Pago> getByMetodoNombre(String nombre){
        return metodos_PagoRepository.findByMetodoNombre(nombre);
    }
    
    public List<Metodo_Pago> findAll() {
        return metodos_PagoRepository.findAll();
    }

    public void save(Metodo_Pago metodoPago){
        metodos_PagoRepository.save(metodoPago);
    }

    public void delete(int idMetodoPago){
        metodos_PagoRepository.deleteById(idMetodoPago);
    }

    public boolean existsById(int idMetodoPago){
        return metodos_PagoRepository.existsById(idMetodoPago);
    }
    
    public Optional<Metodo_Pago> findById(int idMetodoPago) {
        return metodos_PagoRepository.findById(idMetodoPago);
    }
    
    En especifico, es importante que exista este método:
    	// Encuentra un servicio por su ID
    	    public Optional<Servicio> findByServicio(Servicio servicio) {
    	        Long idServicio = servicio.getIdServicio();
    	        return servicioRepository.findById(idServicio);
    	    }

    public boolean existsByMetodoNombre(String nombre){
        return metodos_PagoRepository.existsByMetodoNombre(nombre);
    }
}