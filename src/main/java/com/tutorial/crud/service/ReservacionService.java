package com.tutorial.crud.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutorial.crud.entity.Producto;
import com.tutorial.crud.entity.Reservacion;
import com.tutorial.crud.security.repository.ReservacionRepository;

@Service
@Transactional
public class ReservacionService {

    @Autowired
    private ReservacionRepository reservacionRepository;

    public List<Reservacion> list(){
        return reservacionRepository.findAll();
    }

    public Optional<Reservacion> getOne(int id){
        return reservacionRepository.findById(id);
    }
    
    public Optional<Reservacion> findById(int id) {
        return reservacionRepository.findById(id);
    }

    public void save(Reservacion reservacion){
        reservacionRepository.save(reservacion);
    }

    public void delete(int id){
        reservacionRepository.deleteById(id);
    }

    public boolean existsById(int id){
        return reservacionRepository.existsById(id);
    }

    public boolean existsByFechaReserva(String fechaReserva){
        return reservacionRepository.existsByFechaReserva(fechaReserva);
    }
    
    // Método para verificar si hay reservas existentes que se superpongan con el horario deseado
    public boolean existeReservaEnIntervalo(LocalTime horaInicio, LocalTime horaFin) {
        List<Reservacion> reservaciones = reservacionRepository.findByHoraInicioBetweenOrHoraFinBetween(
            horaInicio, horaFin, horaInicio, horaFin);
        return !reservaciones.isEmpty();
    }

}
