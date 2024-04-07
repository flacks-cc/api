package com.tutorial.crud.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutorial.crud.entity.Contacto;
import com.tutorial.crud.security.repository.ContactoRepository;

@Service
@Transactional
public class ContactoService {

    @Autowired
    private ContactoRepository contactoRepository;

    public List<Contacto> list(){
        return contactoRepository.findAll();
    }

    public Optional<Contacto> getOne(int id){
        return contactoRepository.findById(id);
    }
    
    public Optional<Contacto> findById(int id) {
        return contactoRepository.findById(id);
    }

    public void save(Contacto contacto){
    	contactoRepository.save(contacto);
    }

    public void delete(int id){
    	contactoRepository.deleteById(id);
    }

    public boolean existsById(int id){
        return contactoRepository.existsById(id);
    }

    public boolean existsByFechaMensaje(String fechaMensaje){
        return contactoRepository.existsByFechaMensaje(fechaMensaje);
    }
}
