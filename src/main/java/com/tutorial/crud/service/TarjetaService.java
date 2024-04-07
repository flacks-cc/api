package com.tutorial.crud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutorial.crud.entity.Tarjeta;
import com.tutorial.crud.security.repository.TarjetaRepository;

@Service
@Transactional
public class TarjetaService {

    @Autowired
    TarjetaRepository tarjetaRepository;

    public Optional<Tarjeta> findByUid(String uid) {
        return tarjetaRepository.findByUid(uid);
    }
    public Optional<Tarjeta> getByUid(String uid) {
        return tarjetaRepository.findByUid(uid);
    }
    public Optional<Tarjeta> findById(int id) {
        return tarjetaRepository.findById(id);
    }

    public boolean existsByUid(String uid) {
        return tarjetaRepository.existsByUid(uid);
    }
    
    public List<Tarjeta> list() {
        return tarjetaRepository.findAll();
    }

    public Optional<Tarjeta> getOne(int id) {
        return tarjetaRepository.findById(id);
    }

    public void save(Tarjeta tarjeta) {
        tarjetaRepository.save(tarjeta);
    }

    public void delete(int id) {
        tarjetaRepository.deleteById(id);
    }

    public boolean existsById(int id) {
        return tarjetaRepository.existsById(id);
    }
}
