package com.tutorial.crud.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tutorial.crud.entity.Mensaje_Contacto;
import com.tutorial.crud.security.repository.Mensajes_ContactoRepository;

@Service
@Transactional
public class Mensaje_ContactoService {

    @Autowired
    private Mensajes_ContactoRepository mensajes_ContactoRepository;

    public List<Mensaje_Contacto> list(){
        return mensajes_ContactoRepository.findAll();
    }

    public Optional<Mensaje_Contacto> getOne(int idMensaje){
        return mensajes_ContactoRepository.findById(idMensaje);
    }
    
    public Optional<Mensaje_Contacto> findById(int idMensaje) {
        return mensajes_ContactoRepository.findById(idMensaje);
    }

    public void save(Mensaje_Contacto mensaje){
    	mensajes_ContactoRepository.save(mensaje);
    }

    public void delete(int idMensaje){
    	mensajes_ContactoRepository.deleteById(idMensaje);
    }

    public boolean existsById(int idMensaje){
        return mensajes_ContactoRepository.existsById(idMensaje);
    }

    public boolean existsByFechaHora(String fechaHora){
        return mensajes_ContactoRepository.existsByFechaHora(fechaHora);
    }
}
