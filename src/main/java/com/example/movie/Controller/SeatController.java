package com.example.movie.Controller;

import com.example.movie.Entity.Cinema;
import com.example.movie.Entity.Movie;
import com.example.movie.Entity.Screen;
import com.example.movie.Entity.Seat;
import com.example.movie.Service.ScreenService;
import com.example.movie.Service.SeatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path = "/seat")
public class SeatController {
    @Autowired
    private SeatService seatService;
    @Autowired
    private ScreenService screenService;


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
}
