package com.example.movie.Controller;

//import com.example.movie.Annotation.CurrentUser;
import com.example.movie.Entity.Cinema;
import com.example.movie.Entity.Cinema_Admin;
import com.example.movie.Entity.Movie;
import com.example.movie.Entity.User;
import com.example.movie.Service.Cinema_AdminService;

import com.example.movie.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path = "/cinemaAdmin")
public class CinemaAdminController {
    @Autowired
    private Cinema_AdminService cinema_adminService;
    @Autowired
    private MovieService movieService;

    @PostMapping(path="/login")
    public String login(@RequestParam("username") String username,
                                      @RequestParam("password") String password){
        List<Cinema_Admin> admins = cinema_adminService.findByCinemaAdminUsernameAndPassword(username,password);
        if(admins.size()>0){
            return "redirect:/manage";
        }
        else{
            return "Wrong";
        }

    }
    @PostMapping(path = "/add")
    public String add(@RequestParam String username, String password)
    {
        cinema_adminService.add(username, password);
        return "redirect:/login";
    }

    @GetMapping(path = "/allMovies")
    public String displayAllMovies(Model model)
    {
        List<Movie> movies=movieService.display_all_movies();
        model.addAttribute("movies",movies);
        return "manage_movie";
    }

    @GetMapping(path = "/addMovieForm")
    public String displayMovieForm()
    {
        return "movie_form";
    }

    @PostMapping(path = "/addMovies")
    public String addMovies(@RequestParam String title, String blurb, String certificate, String director, String actors,
                            String showtime, Integer duration, String type, String language, String url)
    {
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            movieService.addMovies(title,blurb,certificate,director,actors,
                    sdf.parse(showtime),duration,type,language,url);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "redirect:/cinemaAdmin/allMovies";
    }






}
