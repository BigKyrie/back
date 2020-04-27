package com.example.movie.Controller;

import com.example.movie.Entity.Movie;
import com.example.movie.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping(path = "/allMovies")
    public @ResponseBody List<Movie> display_all_movies(){return movieService.display_all_movies();}

    @PostMapping(path = "/searchByKeyword")
    public String search_movie_by_keyword(@RequestParam String keyword, Model model) {
        List<Movie> movies=movieService.search_movie_by_keyword(keyword);
        model.addAttribute("movies",movies);
        return "movie_list";
    }

}
