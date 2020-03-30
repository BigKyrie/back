package com.example.movie.Controller;

import com.example.movie.Entity.Cinema;
import com.example.movie.Service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/cinema")
public class CinemaController {
    @Autowired
    private CinemaService cinemaService;
//    //find cinemas by movie_id
//    @GetMapping(path = "/all")
//    public @ResponseBody List<Cinema> display_all_cinemas() {return cinemaService.display_all_cinemas();}

    @GetMapping(path = "/{movie_id}")
    public @ResponseBody List<Cinema> display_cinemas_for_a_certain_movie(@PathVariable(name = "movie_id") Integer movie_id) {
        return cinemaService.display_cinemas_for_a_certain_movie(movie_id);
    }

    @PostMapping(path = "/add")
    public String add(@RequestParam String title, String location, String tel , boolean refund,
                      boolean change_time, boolean snack, boolean three_D_glasses,
                      boolean wifi, boolean rest_area, boolean children_discount
    )
    {
        cinemaService.add(title, location, tel, refund, change_time,  snack, three_D_glasses,
         wifi, rest_area, children_discount);
        return "redirect:/manage";
    }


    @GetMapping(path="/getAllCinema")
    public String allCinema( Model model) {
        List<Cinema> cinemas = cinemaService.display_all_cinemas();
        model.addAttribute("cinemas",cinemas);
        return "cinema_list";
    }

    @RequestMapping("/update")
    public String update() {
        Cinema cinema = cinemaService.getById(2);
        cinemaService.update(cinema);
        return "cinema_list";
    }
}