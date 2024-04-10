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

    public List<MensajeContacto> findAll(){
        return mensajeContactoRepository.findAll();
    }

    public Optional<MensajeContacto> findById(Long idMensaje) {
        return mensajeContactoRepository.findById(idMensaje);
    }

    public void save(MensajeContacto mensaje){
    	mensajeContactoRepository.save(mensaje);
    }

    public void deleteById(Long idMensaje){
    	mensajeContactoRepository.deleteById(idMensaje);
    }

    public boolean existsById(Long idMensaje){
        return mensajeContactoRepository.existsById(idMensaje);
    }

    public boolean existsByFechaHora(String fechaHora){
        return mensajeContactoRepository.existsByFechaHora(fechaHora);
    }
}
