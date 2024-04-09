//package com.tutorial.crud.util;
//
//import com.tutorial.crud.security.entity.Rol;
//import com.tutorial.crud.security.enums.RolNombre;
//import com.tutorial.crud.security.service.RolService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CreateRoles implements CommandLineRunner {
//
//    @Autowired
//    RolService rolService;
//
//    // Esta clase solo debe se ejecutada una vez, despues debes comentar este código
//    @Override
//    public void run(String... args) throws Exception {
//        Rol rolAdmin = new Rol(RolNombre.ROLE_ADMIN);
//        Rol rolUser = new Rol(RolNombre.ROLE_USER);
//        Rol rolEmpleado = new Rol(RolNombre.ROLE_EMPLEADO);
//        rolService.save(rolAdmin);
//        rolService.save(rolUser);
//        rolService.save(rolEmpleado);
//    }
//}