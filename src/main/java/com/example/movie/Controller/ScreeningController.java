package com.example.movie.Controller;

import com.example.movie.Entity.Movie;
import com.example.movie.Entity.Screening;
import com.example.movie.Service.MovieService;
import com.example.movie.Service.ScreeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path = "/screenings")
public class ScreeningController {
    @Autowired
    private ScreeningService screeningService;
    //find screenings by movie_id
    @GetMapping(path = "/{id}")
    public @ResponseBody List<Screening> display_screenings_by_id(@PathVariable(name = "id") Integer id)
    {return screeningService.display_screenings_by_id(id);}
}
