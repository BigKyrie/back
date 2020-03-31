package com.example.movie.Controller;

import com.example.movie.Entity.*;
import com.example.movie.Service.*;
import org.apache.tomcat.util.modeler.BaseAttributeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/demo")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private CinemaService cinemaService;
    @Autowired
    private ScreeningService screeningService;
    @Autowired
    private ScreenService screenService;

    @GetMapping(path="/userMovie")
    public String userCinema(Model model){
        List<Movie> movies=movieService.display_all_movies();
        model.addAttribute("movies",movies);
        return "user_movie";
    }

    @RequestMapping(path="/selectCinema/{movie_id}")
    public String selectCinema(Model model,@PathVariable Integer movie_id){
        List<Cinema> cinemas = cinemaService.display_cinemas_for_a_certain_movie(movie_id);
        model.addAttribute("cinemas",cinemas);
        model.addAttribute("movie_id",movie_id);
        return "movie_cinema";
    }
    @RequestMapping(path="/selectScreening/{cinema_id}/{movie_id}")
    public String selectScreenings(Model model, @PathVariable Integer cinema_id, @PathVariable Integer movie_id){
        List<Screening> screenings = screeningService.display_screenings_filterby_cinema_id_and_movie_id(movie_id,cinema_id);
        List<Screen> screens = screenService.display_all_screens();

        model.addAttribute("screenings",screenings);
        return "movie_cinema_screenings";
    }

    @GetMapping(path = "/getAll")
    public @ResponseBody Iterable<User> getAllUsers()
    {
        return userService.getAllUsers();
    }

    @PostMapping(path = "/getUser")
    public @ResponseBody List<User> findByUsername(String username)
    {
        return userService.findByUsername(username);
    }

    @PostMapping(path = "/get")
    public @ResponseBody List<User> findByUsernameAndPassword(String username,String password)
    {
        return userService.findByUsernameAndPassword(username,password);
    }

    @PostMapping(path = "/add")
    public @ResponseBody boolean add(@RequestParam String username,String password)
    {
        return userService.add(username, password);
    }


}
