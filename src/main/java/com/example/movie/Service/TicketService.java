package com.example.movie.Service;

import com.example.movie.Entity.Screening;
import com.example.movie.Entity.Seat;
import com.example.movie.Entity.Ticket;
import com.example.movie.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private SeatService seatService;
    @Autowired
    private ScreeningService screeningService;

    public List<Ticket> find_ticket_of_a_screening(Integer id){
        return ticketRepository.find_ticket_of_a_screening(id);
    }

    public Ticket generate_a_ticket(Integer screening_id,Integer seat_id,String type) {
        Ticket ticket=new Ticket();
        ticket.setType(type);
        Date date = new Date(System.currentTimeMillis());
        ticket.setCreate_time(date);
        Seat seat=seatService.find_seat_by_id(seat_id);
        Screening screening=screeningService.find_screening_by_screening_id(screening_id);
        ticket.setScreening(screening);
        ticket.setSeat(seat);
        float screeningPrice=screening.getPrice();
        if(type.equals("Adult Ticket")) {
            ticket.setPrice(screeningPrice);
        }
        else if(type.equals("Child Ticket")) {
            ticket.setPrice((float) (screeningPrice*0.5));
        }
        else if(type.equals("Elderly Ticket")) {
            ticket.setPrice((float) (screeningPrice*0.7));
        }
        ticketRepository.save(ticket);
        return ticket;
    }

}
