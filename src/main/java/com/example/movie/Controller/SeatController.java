package com.example.movie.Controller;

import com.example.movie.Entity.*;
import com.example.movie.Service.ScreenService;
import com.example.movie.Service.ScreeningService;
import com.example.movie.Service.SeatService;

import com.example.movie.Service.TicketService;
import com.example.movie.nonEntity.SeatInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/seat")
public class SeatController {
    @Autowired
    private SeatService seatService;
    @Autowired
    private ScreenService screenService;
    @Autowired
    private ScreeningService screeningService;
    @Autowired
    private TicketService ticketService;


    @GetMapping(path = "/view/{screen_id}")
    public String view_seat(@PathVariable(name = "screen_id") Integer screen_id,  Model model){
        List<Seat> seats = seatService.view_seat_of_a_screen(screen_id);
        Screen screen = screenService.get_screen_by_id(screen_id).get(0);
        model.addAttribute("seats",seats);
        model.addAttribute("screen",screen);
        return "view_seats";
    }

    @PostMapping(path = "/add/{screen_id}")
    public String add_seats(@PathVariable(name = "screen_id") Integer screen_id, Integer row, Integer col){
        seatService.add_seats(screen_id,row.intValue(),col.intValue());
        return "redirect:/manage";
    }

    //display seat of a screening
    @GetMapping(path = "/getSeats/{screening_id}")
    public @ResponseBody List<SeatInfo> get_seats(@PathVariable(name = "screening_id") Integer screening_id){
        Screening screening = screeningService.find_screening_by_screening_id(screening_id);
        List<Seat> seats = seatService.view_seat_of_a_screen(screening.getScreen().getId());
        List<Ticket> tickets = ticketService.find_ticket_of_a_screening(screening_id);
        List<SeatInfo> seatInfos = new ArrayList<>();
        List<Seat> taken = new ArrayList<>();
        List<Seat> empty = new ArrayList<>();
        for (int i = 0; i< tickets.size();i++) {
            for (int j = 0; j < seats.size();j++){
                if (tickets.get(i).getSeat().getId() == seats.get(j).getId()){
                    taken.add(seats.get(j));
                    seats.remove(seats.get(j));
                }
            }
        }
        for (int i = 0; i < taken.size();i++){
            SeatInfo seatInfo = new SeatInfo();
            seatInfo.setCol(taken.get(i).getCol());
            seatInfo.setRow(taken.get(i).getCol());
            seatInfo.setSeat_id(taken.get(i).getId());
            seatInfo.setScreening_id(screening.getId());
            seatInfo.setTaken_or_not(true);
            seatInfos.add(seatInfo);
        }
        for (int i = 0; i < seats.size();i++){
            SeatInfo seatInfo = new SeatInfo();
            seatInfo.setCol(seats.get(i).getCol());
            seatInfo.setRow(seats.get(i).getCol());
            seatInfo.setSeat_id(seats.get(i).getId());
            seatInfo.setScreening_id(screening.getId());
            seatInfo.setTaken_or_not(false);
            seatInfos.add(seatInfo);
        }
        return seatInfos;
    }
}
