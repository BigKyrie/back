package com.example.movie.Controller;

import com.example.movie.Service.*;
import com.example.movie.Entity.Cinema;
import com.example.movie.Entity.Cinema_Admin;
import com.example.movie.Entity.Movie;
import com.example.movie.Service.AuthenticationService;
import com.example.movie.Service.Cinema_AdminService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.example.movie.Service.MovieService;
import com.example.movie.Session.UserInfo;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


@Controller
@RequestMapping(path = "/cinemaAdmin")
public class CinemaAdminController {
    @Autowired
    private Cinema_AdminService cinema_adminService;
    @Autowired
    private ScreeningService screeningService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private CinemaService cinemaService;
    @Autowired
    private AuthenticationService authenticationService;

    private static Cinema_Admin admin_user;

    public static Cinema_Admin getAdmin_user(){
        return admin_user;
    }


    @PostMapping(path="/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password) throws JSONException {
        List<Cinema_Admin> admins = cinema_adminService.findByCinemaAdminUsernameAndPassword(username, password);
        List<Cinema_Admin> adminExist = cinema_adminService.findByCinemaAdminUsername(username);
        if (adminExist.size() > 0) {
            if (admins.size() > 0) {
                UserInfo userInfo = new UserInfo(admins.get(0).getId(), admins.get(0).getUsername());
                HttpSession session = getRequest().getSession();
                session.setAttribute("user_info_in_the_session", userInfo);
                return "redirect:/manage";
                }
                else {
                    return "redirect:/login";
                }
            }
            else {
                return "redirect:/login";
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

    @RequestMapping(path="/delete/{movie_id}")
    public String deleteByID(@PathVariable Integer movie_id){
        int movie_temp=movie_id;
        screeningService.deletebymovieID(movie_temp);
        movieService.deletebyID(movie_temp);

        return "redirect:/cinemaAdmin/allMovies";
    }

    @RequestMapping(path="/selectCinema/{movie_id}")
    public String selectCinema(Model model,@PathVariable Integer movie_id){
        List<Cinema> cinemas = cinemaService.display_cinemas_for_a_certain_movie(movie_id);
        model.addAttribute("cinemas",cinemas);
        return "movie_cinema";
    }


    @GetMapping(path = "/addMovieForm")
    public String displayMovieForm(Model model)
    {
        HttpSession session = getRequest().getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("user_info_in_the_session");
        model.addAttribute("username",userInfo.getUsername());
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

    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

}
