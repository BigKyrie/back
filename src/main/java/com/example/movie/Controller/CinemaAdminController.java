package com.example.movie.Controller;
import com.example.movie.Entity.*;
import com.example.movie.Service.*;
import com.example.movie.Service.Cinema_AdminService;
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
import java.util.ArrayList;
import java.util.Date;
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
    private ScreenService screenService;
    @Autowired
    private CinemaService cinemaService;

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
                else {
                    return "redirect:/login";
                }
            }

            else{
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
        List<Movie> moviesInCinema=new ArrayList<>();
        HttpSession session = getRequest().getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("user_info_in_the_session");
        List<Screening> screenings=screeningService.screenings_in_cinema(cinema_adminService.findAdminById(userInfo.getUserId()).getCinema().getId());
        for(int i=0;i<screenings.size();i++) {
            if(!moviesInCinema.contains(screenings.get(i).getMovie())) {
                moviesInCinema.add(screenings.get(i).getMovie());
            }
        }
        model.addAttribute("movies",moviesInCinema);
        return "manage_movie";
    }

    @GetMapping(path = "/allScreenings")
    public String displayAllScreeningsInCinema(Model model)
    {
        HttpSession session = getRequest().getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("user_info_in_the_session");
        List<Screening> screenings=screeningService.screenings_in_cinema(cinema_adminService.findAdminById(userInfo.getUserId()).getCinema().getId());
        model.addAttribute("screenings",screenings);
        return "screening_list";
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
    public String displayMovieForm()
    {
        return "movie_form";
    }

    @GetMapping(path = "/searchScreeningForm")
    public String searchScreeningForm()
    {
        return "screening_search";
    }

    @GetMapping(path = "/addScreeningForm")
    public String addScreeningForm()
    {
        return "screening_form";
    }


    @GetMapping(path = "/toEditMovie/{movie_id}")
    public String editMovieForm(Model model,@PathVariable Integer movie_id)
    {
        Movie movie=movieService.findMoviebyID(movie_id);
        model.addAttribute("movie",movie);
        model.addAttribute("movie_id",movie_id);
        return "movie_edit";
    }

    @PostMapping(path = "/editMovie/{movie_id}")
    public String editMovie(@RequestParam String title, String blurb, String certificate, String director, String actors,
                            String showtime,Integer duration,String type,String language,String url,@PathVariable Integer movie_id)
    {
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm");
            movieService.update(title, blurb, certificate, director, actors,
                    sdf.parse(showtime),duration,type,language,url,movie_id);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "redirect:/cinemaAdmin/allMovies";
    }




    @PostMapping(path = "/addMovies")
    public String addMovies(@RequestParam String title, String blurb, String certificate, String director, String actors,
                            String showtime, Integer duration, String type, String language, String url,
                            String start_time,String end_time,String price,String num)
    {
        try {

            if(movieService.find_movie_by_certificate(certificate).size()>0) {
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm");
                HttpSession session = getRequest().getSession();
                UserInfo userInfo = (UserInfo) session.getAttribute("user_info_in_the_session");
                Cinema cinema=cinema_adminService.findAdminById(userInfo.getUserId()).getCinema();
                Screen screen=screenService.find_screen_by_num_and_cinema(Integer.parseInt(num),cinema.getId());
                screeningService.addScreening(sdf.parse(start_time), sdf.parse(end_time), Float.parseFloat(price), screen,
                                              movieService.find_movie_by_certificate(certificate).get(0));
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
