package com.example.movie.Controller;

import com.example.movie.Entity.Cinema;
import com.example.movie.Entity.Cinema_Admin;
import com.example.movie.Repository.Cinema_AdminRepository;
import com.example.movie.Service.CinemaService;
import com.example.movie.Service.Cinema_AdminService;
import com.example.movie.Session.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(path = "/cinema")
public class CinemaController {
    @Autowired
    private CinemaService cinemaService;
    @Autowired
    private Cinema_AdminService cinema_adminService;

    @GetMapping(path = "/{movie_id}")
    public @ResponseBody List<Cinema> display_cinemas_for_a_certain_movie(@PathVariable(name = "movie_id") Integer movie_id) {
        return cinemaService.display_cinemas_for_a_certain_movie(movie_id);
    }

    @PostMapping(path = "/add")
    public String add(@RequestParam String title, String location, String tel , boolean refund,
                      boolean change_time, boolean snack, boolean three_D_glasses,
                      boolean wifi, boolean rest_area, boolean children_discount
    )
    {
        //get current user
        HttpSession session = getRequest().getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("user_info_in_the_session");
        Integer id = Integer.valueOf(userInfo.getUserId());
        cinemaService.add(id,title, location, tel, refund, change_time,  snack, three_D_glasses,
         wifi, rest_area, children_discount);
        return "redirect:/manage";
    }


    @GetMapping(path="/getAllCinema")
    public String allCinema( Model model) {
        HttpSession session = getRequest().getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("user_info_in_the_session");
        Integer id = Integer.valueOf(userInfo.getUserId());
        Cinema_Admin cinema_admin = cinema_adminService.findAdminById(id);
        String hasCinema = "true";
        if (cinema_admin.getCinema()!= null){
            Cinema cinema = cinemaService.getById(cinema_admin.getCinema().getId());
            model.addAttribute("cinemas",cinema);
        }
        else
        {
            hasCinema = "false";
        }
        model.addAttribute("hasCinema",hasCinema);
//        model.addAttribute("userInfo",userInfo.getUserId());
        return "cinema_list";
    }


    @GetMapping(path = "/toEdit/{cinema_id}")
    public String toEdit(Model model, @PathVariable(name = "cinema_id")Integer id){
        Cinema cinema=cinemaService.getById(id);
        model.addAttribute("cinema", cinema);
        return "cinema_edit";
    }

    @PostMapping(path = "/edit/{cinema_id}")
    public String update(@RequestParam String title, String location, String tel , boolean refund,
                   boolean change_time, boolean snack, boolean three_D_glasses,
                   boolean wifi, boolean rest_area, boolean children_discount, @PathVariable(name = "cinema_id")Integer id
    ) {
        cinemaService.update(title, location, tel, refund, change_time,  snack, three_D_glasses,
                wifi, rest_area, children_discount,id);
        return "redirect:/cinema/getAllCinema";
    }

    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

}