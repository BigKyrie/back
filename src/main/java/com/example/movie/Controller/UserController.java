package com.example.movie.Controller;

import com.example.movie.Entity.*;
import com.example.movie.Service.*;
import org.apache.tomcat.util.modeler.BaseAttributeFilter;
import com.example.movie.Service.UserService;
import com.example.movie.Entity.User;
import com.example.movie.Session.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(path = "/demo")
public class UserController {
    @Autowired
    private MovieService movieService;
    @Autowired
    private CinemaService cinemaService;
    @Autowired
    private ScreeningService screeningService;
    @Autowired
    private ScreenService screenService;
    @Autowired
    private Cinema_AdminService cinema_adminService;
    @Autowired
    private TicketService ticketService;

    @GetMapping(path="/userMovie")
    public String userCinema(Model model){
        List<Movie> movies=movieService.display_all_movies();
        model.addAttribute("movies",movies);
        return "index";
    }

    @GetMapping(path = "/allMovie")
    public String allMovie(Model model){
        List<Movie> movies=movieService.display_all_movies();
        model.addAttribute("movies",movies);
        return "movie_list";
    }

    @GetMapping(path="myMovie")
    public String userMovie(Model model){
        HttpSession session = getRequest().getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("user_info_in_the_session");
        List<Movie> movies = movieService.display_movies_by_userID(userInfo.getUserId());
        model.addAttribute("movies",movies);
        return "user_bought_movie";
    }

    @GetMapping(path="/mine")
    public String mineInformation(Model model){
        return "mine_information";
    }

    @GetMapping(path="/allorders")
    public String mineallorders(Model model){
        HttpSession session = getRequest().getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("user_info_in_the_session");
        Cinema_Admin admin = cinema_adminService.findAdminById(userInfo.getUserId());
        List<Ticket> tickets = ticketService.find_ticket_of_a_user(admin.getId());
        model.addAttribute("tickets",tickets);
        return "user_tickets";
    }

    @PostMapping(path="/userLogin")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password) {
        List<Cinema_Admin> admins = cinema_adminService.findByCinemaAdminUsernameAndPassword(username,password);
        List<Cinema_Admin> adminExist = cinema_adminService.findByCinemaAdminUsername(username);
        if(adminExist.size()>0) {
            if(admins.size()>0){
                UserInfo userInfo = new UserInfo(admins.get(0).getId(), admins.get(0).getUsername());
                HttpSession session = getRequest().getSession();
                session.setAttribute("user_info_in_the_session", userInfo);
                return "redirect:/demo/userMovie";
            }
            else {
                return "redirect:/userLogin";
            }
        }

        else{
            return "redirect:/userLogin";
        }
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


    @PostMapping(path = "/getUser")
    public @ResponseBody List<Cinema_Admin> findByUsername(String username)
    {
        return cinema_adminService.findByCinemaAdminUsername(username);
    }

    @PostMapping(path = "/get")
    public @ResponseBody List<Cinema_Admin> findByUsernameAndPassword(String username,String password)
    {
        List<Cinema_Admin> users=cinema_adminService.findByCinemaAdminUsernameAndPassword(username,password);
        if(users.size()>0) {
            UserInfo userInfo = new UserInfo(users.get(0).getId(), users.get(0).getUsername());
            HttpSession session = getRequest().getSession();
            session.setAttribute("user_info_in_the_session", userInfo);
        }
        return users;
    }

    @PostMapping(path = "/add")
    public @ResponseBody boolean add(@RequestParam String username,String password)
    {
        return cinema_adminService.add(username, password);
    }

    @GetMapping(path = "/my_ticket")
    public @ResponseBody List<Ticket> mytickets(String user_id){
        //HttpSession session = getRequest().getSession();
        //UserInfo userInfo = (UserInfo) session.getAttribute("user_info_in_the_session");
        Cinema_Admin cinema_admin=cinema_adminService.findAdminById(Integer.parseInt(user_id));
        return ticketService.find_ticket_of_a_user(cinema_admin.getId());
    }

    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

}
