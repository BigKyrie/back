package com.example.movie.Controller;

import com.example.movie.Entity.Cinema_Admin;
import com.example.movie.Entity.Screen;
import com.example.movie.Entity.Screening;
import com.example.movie.Entity.Seat;
import com.example.movie.Service.*;
import com.example.movie.Session.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(path = "/screen")
public class ScreenController {
    @Autowired
    private ScreenService screenService;
    @Autowired
    private Cinema_AdminService cinema_adminService;
    @Autowired
    private ScreeningService screeningService;
    @Autowired
    private SeatService seatService;
    @Autowired
    private TicketService ticketService;
    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    @GetMapping(path="/getAllScreens")
    public String allScreen( Model model) {
        HttpSession session = getRequest().getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("user_info_in_the_session");
        Integer id = Integer.valueOf(userInfo.getUserId());
        Cinema_Admin cinema_admin = cinema_adminService.findAdminById(id);
        List<Screen> screens = screenService.get_screen_by_cinema_id(cinema_admin.getCinema().getId());
        model.addAttribute("screens", screens);
        return "screen_list";
    }

    @PostMapping(path = "/add")
    public String add(@RequestParam Integer num)
    {
        //get current user
        HttpSession session = getRequest().getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("user_info_in_the_session");
        Integer id = Integer.valueOf(userInfo.getUserId());
        screenService.add(id,Integer.valueOf(num));
        return "redirect:/screen/getAllScreens";
    }

    @RequestMapping(path = "/delete/{screen_id}")
    public String delete(@PathVariable Integer screen_id )
    {
        List<Seat> seats = seatService.view_seat_of_a_screen(screen_id);
        for(int i = 0; i<seats.size();i++){
            //        delete ticket related to this screen
            ticketService.delete_ticket_by_seat_id(seats.get(i).getId());
            //        delete seat related to this screen
            seatService.delete_seat_by_id(seats.get(i).getId());
        }
        //        delete screening relate to this screen
        List<Screening> screenings = screeningService.find_screenings_by_screen_id(screen_id);
        for(int i = 0; i<screenings.size();i++){
            screeningService.delete_by_screening_id(screenings.get(i).getId());
        }
        //delete screen
        screenService.delete_by_screen_id(screen_id);
        return "redirect:/screen/getAllScreens";
    }
}
