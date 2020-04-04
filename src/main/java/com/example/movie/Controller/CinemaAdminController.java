package com.example.movie.Controller;
import com.example.movie.Entity.Cinema;
import com.example.movie.Entity.Cinema_Admin;
import com.example.movie.Entity.Movie;
import com.example.movie.Entity.Screen;
import com.example.movie.Service.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.example.movie.Session.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path = "/cinemaAdmin")
public class CinemaAdminController {
    @Autowired
    private Cinema_AdminService cinema_adminService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private ScreeningService screeningService;
    @Autowired
    private ScreenService screenService;

    @PostMapping(path="/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password) {
        List<Cinema_Admin> admins = cinema_adminService.findByCinemaAdminUsernameAndPassword(username,password);
        List<Cinema_Admin> adminExist = cinema_adminService.findByCinemaAdminUsername(username);
        if(adminExist.size()>0) {
            if(admins.size()>0){
                UserInfo userInfo = new UserInfo(admins.get(0).getId(), admins.get(0).getUsername());
                HttpSession session = getRequest().getSession();
                session.setAttribute("user_info_in_the_session", userInfo);
                return "redirect:/manage";
            }
            else{
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
    public String displayAllMoviesInCinema(Model model)
    {
        HttpSession session = getRequest().getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("user_info_in_the_session");
        List<Movie> movies=movieService.movies_in_cinema(cinema_adminService.findAdminById(userInfo.getUserId()).getCinema().getId());
        model.addAttribute("movies",movies);
        //model.addAttribute("username",userInfo.getUsername());
        return "manage_movie";
    }

    @GetMapping(path = "/addMovieForm")
    public String displayMovieForm()
    {
        return "movie_form";
    }


    @PostMapping(path = "/addMovies")
    public String addMovies(@RequestParam String title, String blurb, String certificate, String director, String actors,
                            String showtime, Integer duration, String type, String language, String url,
                            String start_time,String end_time,String price,String num)
    {
        try {

            if(movieService.find_movie_by_certificate(certificate).size()>0) {

                return "redirect:/cinemaAdmin/addMovieForm";
            }
            else {
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm");
                Movie movie=movieService.addMovies(title,blurb,certificate,director,actors,
                        sdf.parse(showtime),duration,type,language,url);
                HttpSession session = getRequest().getSession();
                UserInfo userInfo = (UserInfo) session.getAttribute("user_info_in_the_session");
                Cinema cinema=cinema_adminService.findAdminById(userInfo.getUserId()).getCinema();
                Screen screen=screenService.find_screen_by_num_and_cinema(Integer.parseInt(num),cinema.getId());
                screeningService.addScreening(sdf.parse(start_time), sdf.parse(end_time), Float.parseFloat(price), screen, movie);

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "redirect:/cinemaAdmin/allMovies";
    }

    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

}
