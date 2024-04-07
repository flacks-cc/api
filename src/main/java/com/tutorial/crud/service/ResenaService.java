package com.tutorial.crud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutorial.crud.entity.Resenas;
import com.tutorial.crud.security.repository.ResenaRepository;

@Service
@Transactional
public class ResenaService {

    @Autowired
    private ResenaRepository resenaRepository;

    public List<Resenas> list(){
        return resenaRepository.findAll();
    }

    public Optional<Resenas> getOne(int id){
        return resenaRepository.findById(id);
    }
    
    public Optional<Resenas> findById(int id) {
        return resenaRepository.findById(id);
    }

    public void save(Resenas resenas){
    	resenaRepository.save(resenas);
    }

    public void delete(int id){
    	resenaRepository.deleteById(id);
    }

    public boolean existsById(int id){
        return resenaRepository.existsById(id);
    }

    public boolean existsByFechaResena(String fechaResena){
        return resenaRepository.existsByFechaResena(fechaResena);
    }
}
