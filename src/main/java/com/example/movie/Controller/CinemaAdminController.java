package com.example.movie.Controller;

import com.example.movie.Entity.Cinema_Admin;
import com.example.movie.Service.Cinema_AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(path = "/cinemaAdmin")
public class CinemaAdminController {
    @Autowired
    private Cinema_AdminService cinema_adminService;

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


//    @GetMapping(path="/movies_in_cinema")
//    public @ResponseBody String moviesInCinema(@CurrentUser Cinema_Admin cinema_admin, Model model) {
//        List<Movie> movies=new ArrayList<>();
//        movies=cinema_adminService.findMovieByCinema(cinema_admin.getCinema().getId());
//        model.addAttribute("movies",movies);
//        return "manage_movie";
//    }
}
