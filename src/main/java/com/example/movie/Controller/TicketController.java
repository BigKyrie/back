package com.example.movie.Controller;

import com.example.movie.Entity.Movie;
import com.example.movie.Entity.Ticket;
import com.example.movie.Service.MovieService;
import com.example.movie.Service.TicketService;
import com.example.movie.Session.UserInfo;
import com.example.movie.nonEntity.MovieSelling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping(path = "/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private MovieService movieService;

    @GetMapping(path="/sellingweek")
    public String selling(Model model) throws ParseException {
        HttpSession session = getRequest().getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("user_info_in_the_session");
        List<Ticket> tickets = ticketService.find_ticket_of_a_cinema_admin(userInfo.getUserId());
        List<Ticket> tickets1 = ticketService.find_ticket_within_one_week(tickets);
        List<Movie> movies = movieService.display_movies_by_adminID(userInfo.getUserId());
        List<MovieSelling> movieSellings = ticketService.display_selling_by_movie_and_ticket(movies,tickets1);
        model.addAttribute("movies",movieSellings);
        return "ticket_selling";
    }

    @GetMapping(path="/sellingmonth")
    public  String sellingmonth(Model model) throws  ParseException{
        HttpSession session = getRequest().getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("user_info_in_the_session");
        List<Ticket> tickets = ticketService.find_ticket_of_a_cinema_admin(userInfo.getUserId());
        List<Ticket> tickets1 = ticketService.find_ticket_within_one_month(tickets);
        List<Movie> movies = movieService.display_movies_by_adminID(userInfo.getUserId());
        List<MovieSelling> movieSellings = ticketService.display_selling_by_movie_and_ticket(movies,tickets1);
        model.addAttribute("movies",movieSellings);
        return "ticket_sellingmonth";
    }

    @GetMapping(path = "/sellingtotal")
    public String sellingtotal(Model model) throws  ParseException{
        HttpSession session = getRequest().getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("user_info_in_the_session");
        List<Ticket> tickets = ticketService.find_ticket_of_a_cinema_admin(userInfo.getUserId());
        List<Movie> movies = movieService.display_movies_by_adminID(userInfo.getUserId());
        List<MovieSelling> movieSellings = ticketService.display_selling_by_movie_and_ticket(movies,tickets);
        model.addAttribute("movies",movieSellings);
        return "ticket_sellingtotal";

    }

    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

//    @GetMapping(path = "/{screening_id}/{seat_id}")
//    public @ResponseBody
//    List<Ticket> display_ticket
//            (@PathVariable(name = "screening_id") Integer screening_id, @PathVariable(name = "seat_id") Integer seat_id)
//    {return screeningService.display_screenings_filterby_cinema_id_and_movie_id(movie_id,cinema_id);}

    @PostMapping(path = "/{screening_id}/{seat_id}")
    public Ticket add_and_display_ticket
            (@RequestParam String type,@PathVariable(name = "screening_id") Integer screening_id,
             @PathVariable(name = "seat_id") Integer seat_id) {
        //return ticketService.generate_a_ticket(screening_id,seat_id,type);
        return null;
    }

    @PostMapping(path = "generate/{screening_id}/{seat_id}")
    public String generate_ticket
            (@RequestParam String type,@PathVariable(name = "screening_id") Integer screening_id,
             @PathVariable(name = "seat_id") Integer seat_id) {
        Ticket ticket=ticketService.generate_a_ticket(screening_id,seat_id,type);
        return "redirect:/manage";
    }


}
