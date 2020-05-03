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
        model.addAttribute("screen",screen);
        //if the seat are already added to the screen
        if(seats.size()>0){
            model.addAttribute("row",seats.get(seats.size()-1).getRow());
            model.addAttribute("col",seats.get(seats.size()-1).getCol());
        }
        else
        {
            model.addAttribute("row",0);
            model.addAttribute("col",0);
        }
        model.addAttribute("seatSize",seats.size());
        return "view_seats";
    }

    @GetMapping(path = "/addSeat/{screen_id}")
    public String add_seat(@PathVariable(name = "screen_id") Integer screen_id,  Model model){
        Screen screen = screenService.get_screen_by_id(screen_id).get(0);
        List<Seat> seats = seatService.view_seat_of_a_screen(screen_id);
        if(seats.size() == 0) {
            model.addAttribute("screen", screen);
            return "add_seat";
        }
        else {
            return "redirect:/screen/getAllScreens";
        }
    }

    @PostMapping(path = "/add/{screen_id}")
    public String add_seats(@PathVariable(name = "screen_id") Integer screen_id, Integer row, Integer col){
        seatService.add_seats(screen_id,row.intValue(),col.intValue());
        return "redirect:/manage";
    }

    @GetMapping(path = "/editSeat/{screen_id}")
    public String edit_seat(@PathVariable(name = "screen_id") Integer screen_id,  Model model){
        Screen screen = screenService.get_screen_by_id(screen_id).get(0);
        List<Seat> seats = seatService.view_seat_of_a_screen(screen_id);
        if(seats.size()>0){
            model.addAttribute("screen",screen);
            return "edit_seat";
        }
        else{
            return "redirect:/screen/getAllScreens";
        }
    }

    @PostMapping(path = "/edit/{screen_id}")
    public String edit_seats(@PathVariable(name = "screen_id") Integer screen_id, Integer row, Integer col){
        List<Seat> seats = seatService.view_seat_of_a_screen(screen_id);
        for(int i = 0; i<seats.size();i++){
            ticketService.delete_ticket_by_seat_id(seats.get(i).getId());
            seatService.delete_seat_by_id(seats.get(i).getId());
        }
        seatService.add_seats(screen_id,row.intValue(),col.intValue());
        return "redirect:/manage";
    }

    //display seat of a screening
    @GetMapping(path = "/getSeats/{screening_id}")
    public @ResponseBody List<SeatInfo> get_seats(@PathVariable(name = "screening_id") Integer screening_id){
        return seatService.get_seat_of_a_screening(screening_id);
    }

    @GetMapping(path = "/selectSeats/{screening_id}")
    public String select_seats(@PathVariable(name = "screening_id") Integer screening_id,  Model model){
        List<SeatInfo> seatInfos = seatService.get_seat_of_a_screening(screening_id);
        model.addAttribute("seats",seatInfos);
        model.addAttribute("screenings",screeningService.find_screening_by_screening_id(screening_id));
        model.addAttribute("row",seatInfos.get(seatInfos.size()-1).getRow());
        model.addAttribute("col",seatInfos.get(seatInfos.size()-1).getCol());
        return "select_seat";
    }
}
