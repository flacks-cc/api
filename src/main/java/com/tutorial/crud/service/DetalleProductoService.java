package com.tutorial.crud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutorial.crud.entity.DetalleProducto;
import com.tutorial.crud.entity.Ticket;
import com.tutorial.crud.security.repository.DetalleProductoRepository;

@Service
@Transactional
public class DetalleProductoService {

    @Autowired
    private DetalleProductoRepository detalleProductoRepository;

    public List<DetalleProducto> list(){
        return detalleProductoRepository.findAll();
    }

    public Optional<DetalleProducto> getOne(int id){
        return detalleProductoRepository.findById(id);
    }

    public void save(DetalleProducto detalleProducto){
        detalleProductoRepository.save(detalleProducto);
    }

    public void delete(int id){
        detalleProductoRepository.deleteById(id);
    }

    public boolean existsById(int id){
        return detalleProductoRepository.existsById(id);
    }
    
    public Optional<DetalleProducto> findById(int id) {
        return detalleProductoRepository.findById(id);
    }
}
