package com.tutorial.crud.security.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorial.crud.security.entity.Rol;
import com.tutorial.crud.security.enums.RolNombre;
import com.tutorial.crud.security.repository.RolesRepository;

@Service
@Transactional
public class RolService {

    @Autowired
    RolesRepository rolesRepository;

    public Optional<Rol> getByRolNombre(RolNombre nombre) {
        return rolesRepository.findByRolNombre(nombre);
    }

    public void save(Rol rol) {
        rolesRepository.save(rol);
    }
}
