package com.tutorial.crud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutorial.crud.entity.Resena;
import com.tutorial.crud.security.repository.ResenasRepository;

@Service
@Transactional
public class ResenaService {

    @Autowired
    private ResenasRepository resenasRepository;

    public List<Resena> findAll(){
        return resenasRepository.findAll();
    }
    
    public Optional<Resena> findById(Long idResena) {
        return resenasRepository.findById(idResena);
    }

    public void save(Resena resenas){
    	resenasRepository.save(resenas);
    }

    public void deleteById(Long idResena){
    	resenasRepository.deleteById(idResena);
    }
    
    // Eliminar un servicio existe
    public void delete(Long idResena) {
    	resenasRepository.deleteById(idResena);
    }
    
    public boolean existsById(Long idResena){
        return resenasRepository.existsById(idResena);
    }

    public boolean existsByFechaHora(String fechaHora){
        return resenasRepository.existsByFechaHora(fechaHora);
    }
}
