package com.example.movie.Controller;

import com.example.movie.Entity.Screening;
import com.example.movie.Entity.Ticket;
import com.example.movie.Service.SeatService;
import com.example.movie.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path = "/ticket")
public class TicketController {


//    @Autowired
//    private TicketService ticketService;
//    @GetMapping(path = "/{screening_id}/{seat_id}")
//    public @ResponseBody
//    List<Ticket> display_ticket
//            (@PathVariable(name = "screening_id") Integer screening_id, @PathVariable(name = "seat_id") Integer seat_id)
//    {return screeningService.display_screenings_filterby_cinema_id_and_movie_id(movie_id,cinema_id);}



}
