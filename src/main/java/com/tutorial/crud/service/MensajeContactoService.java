package com.tutorial.crud.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutorial.crud.entity.MensajeContacto;
import com.tutorial.crud.security.repository.MensajeContactoRepository;

@Service
@Transactional
public class MensajeContactoService {

    @Autowired
    private MensajeContactoRepository mensajeContactoRepository;

    public List<MensajeContacto> list(){
        return mensajeContactoRepository.findAll();
    }

    public Optional<MensajeContacto> getOne(int id){
        return mensajeContactoRepository.findById(id);
    }
    
    public Optional<MensajeContacto> findById(int id) {
        return mensajeContactoRepository.findById(id);
    }

    public void save(MensajeContacto mensajeContacto){
    	mensajeContactoRepository.save(mensajeContacto);
    }

    public void delete(int id){
    	mensajeContactoRepository.deleteById(id);
    }

    public boolean existsById(int id){
        return mensajeContactoRepository.existsById(id);
    }

    public boolean existsByFechaMensaje(String fechaMensaje){
        return mensajeContactoRepository.existsByFechaMensaje(fechaMensaje);
    }
}
