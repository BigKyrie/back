package com.example.movie.Controller;

import com.example.movie.Entity.Movie;
import com.example.movie.Entity.Screen;
import com.example.movie.Entity.Screening;
import com.example.movie.Entity.Ticket;
import com.example.movie.Service.*;
import com.example.movie.Session.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path = "/screenings")
public class ScreeningController {
    @Autowired
    private ScreeningService screeningService;
    @Autowired
    private Cinema_AdminService cinema_adminService;
    @Autowired
    private ScreenService screenService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private TicketService ticketService;

    //find screenings by movie_id
    @GetMapping(path = "/{movie_id}/{cinema_id}")
    public @ResponseBody List<Screening> display_screenings_by_id
    (@PathVariable(name = "movie_id") Integer movie_id, @PathVariable(name = "cinema_id") Integer cinema_id)
    {return screeningService.display_screenings_filterby_cinema_id_and_movie_id(movie_id,cinema_id);}

    @GetMapping(path = "/toEditScreening/{screening_id}")
    public String editScreeningForm(Model model,@PathVariable Integer screening_id)
    {
        Screening screening=screeningService.find_screening_by_screening_id(screening_id);
        model.addAttribute("screening",screening);
        model.addAttribute("screening_id",screening_id);
        model.addAttribute("screen_number",screening.getScreen().getNum());
        model.addAttribute("movie_certificate",screening.getMovie().getCertificate());
        return "screening_edit";
    }
    @PostMapping(path = "/editScreening/{screening_id}")
    public String editScreening(@RequestParam String screen_number,String movie_certificate,String date,String start_time,
                            String end_time,String price,@PathVariable Integer screening_id) {
        try {
            HttpSession session = getRequest().getSession();
            UserInfo userInfo = (UserInfo) session.getAttribute("user_info_in_the_session");
            Screen screen=screenService.find_screen_by_num_and_cinema(Integer.parseInt(screen_number),cinema_adminService.findAdminById(userInfo.getUserId()).getCinema().getId()).get(0);
            Movie movie=movieService.find_movie_by_certificate(movie_certificate).get(0);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String start=date+" "+start_time;
            String end=date+" "+end_time;
            screeningService.update(sdf.parse(start), sdf.parse(end),Float.parseFloat(price), screen, movie,screening_id);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "redirect:/cinemaAdmin/allScreenings";
    }

    @PostMapping(path = "/search")
    public String searchScreening(@RequestParam String screen_number, String movie_title, String date, Model model)
    {
        HttpSession session = getRequest().getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("user_info_in_the_session");
        List<Screening> screenings=screeningService.searchScreening(screen_number, movie_title, date,
                cinema_adminService.findAdminById(userInfo.getUserId()).getCinema().getId());
        model.addAttribute("screenings",screenings);
        return "screening_list";
    }
    @PostMapping(path = "/add")
    public String addScreening(@RequestParam String screen_number, String movie_certificate, String date, String start_time, String end_time, String price, Model model)
    {
        try {
            HttpSession session = getRequest().getSession();
            UserInfo userInfo = (UserInfo) session.getAttribute("user_info_in_the_session");
            Screen screen=screenService.find_screen_by_num_and_cinema(Integer.parseInt(screen_number),cinema_adminService.findAdminById(userInfo.getUserId()).getCinema().getId()).get(0);
            Movie movie=movieService.find_movie_by_certificate(movie_certificate).get(0);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String start=date+" "+start_time;
            String end=date+" "+end_time;
            screeningService.addScreening(sdf.parse(start),sdf.parse(end),Float.parseFloat(price),screen,movie);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "redirect:/cinemaAdmin/allScreenings";
    }

    @RequestMapping(path="/delete/{screening_id}")
    public String deleteScreening(@PathVariable Integer screening_id){
        List<Ticket> tickets=ticketService.find_ticket_of_a_screening(screening_id);
        for(int i=0;i<tickets.size();i++) {
            ticketService.delete_by_ticket_id(tickets.get(i).getId());
        }
        screeningService.delete_by_screening_id(screening_id);
        return "redirect:/cinemaAdmin/allScreenings";
    }


    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }
}
