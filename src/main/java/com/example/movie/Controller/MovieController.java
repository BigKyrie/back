package com.example.movie.Controller;

import com.example.movie.Entity.Movie;
import com.example.movie.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path = "/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping(path = "/allMovies")
    public @ResponseBody List<Movie> display_all_movies(){return movieService.display_all_movies();}

}
