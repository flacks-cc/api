package com.tutorial.crud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutorial.crud.entity.Reservacion;
import com.tutorial.crud.entity.Ticket;
import com.tutorial.crud.security.repository.TicketRepository;

@Service
@Transactional
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> list(){
        return ticketRepository.findAll();
    }

    public Optional<Ticket> getOne(int id){
        return ticketRepository.findById(id);
    }

    public void save(Ticket ticket){
        ticketRepository.save(ticket);
    }

    public void delete(int id){
        ticketRepository.deleteById(id);
    }

    public boolean existsById(int id){
        return ticketRepository.existsById(id);
    }
    
    public Optional<Ticket> findById(int id) {
        return ticketRepository.findById(id);
    }
}
