package com.example.movie.Controller;

import com.example.movie.Entity.Screening;
import com.example.movie.Entity.Seat;
import com.example.movie.Entity.Ticket;
import com.example.movie.Entity.User;
import com.example.movie.Service.ScreeningService;
import com.example.movie.Service.SeatService;
import com.example.movie.Service.TicketService;
import com.example.movie.Service.UserService;
import com.example.movie.Session.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path = "/ticket")
public class TicketController {


    @Autowired
    private TicketService ticketService;


    @PostMapping(path = "/{screening_id}/{seat_id}")
    public Ticket add_and_display_ticket
            (@RequestParam String type,@PathVariable(name = "screening_id") Integer screening_id,
             @PathVariable(name = "seat_id") Integer seat_id) {
        return ticketService.generate_a_ticket(screening_id,seat_id,type);
    }

    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }


}
