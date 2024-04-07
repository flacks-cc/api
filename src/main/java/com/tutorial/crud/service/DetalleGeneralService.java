package com.tutorial.crud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutorial.crud.entity.DetalleGeneral;
import com.tutorial.crud.entity.Ticket;
import com.tutorial.crud.security.repository.DetalleGeneralRepository;

@Service
@Transactional
public class DetalleGeneralService {

    @Autowired
    private DetalleGeneralRepository detalleGeneralRepository;

    public List<DetalleGeneral> list(){
        return detalleGeneralRepository.findAll();
    }

    public Optional<DetalleGeneral> getOne(int id){
        return detalleGeneralRepository.findById(id);
    }

    public void save(DetalleGeneral detalleGeneral){
        detalleGeneralRepository.save(detalleGeneral);
    }

    public void delete(int id){
        detalleGeneralRepository.deleteById(id);
    }

    public boolean existsById(int id){
        return detalleGeneralRepository.existsById(id);
    }
    
    public Optional<DetalleGeneral> findById(int id) {
        return detalleGeneralRepository.findById(id);
    }
}
