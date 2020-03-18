package com.example.movie.Controller;

import com.example.movie.Entity.Cinema;
import com.example.movie.Entity.Screening;
import com.example.movie.Service.CinemaService;
import com.example.movie.Service.Cinema_AdminService;

import com.example.movie.Service.ScreeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
}