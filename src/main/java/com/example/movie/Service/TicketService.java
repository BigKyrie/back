package com.example.movie.Service;

import com.example.movie.Entity.Ticket;
import com.example.movie.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    public List<Ticket> find_ticket_of_a_screening(Integer id){
        return ticketRepository.find_ticket_of_a_screening(id);
    }
}
