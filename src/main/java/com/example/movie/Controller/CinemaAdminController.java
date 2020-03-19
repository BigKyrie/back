package com.example.movie.Controller;

import com.example.movie.Entity.Cinema_Admin;
import com.example.movie.Service.Cinema_AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/cinemaAdmin")
public class CinemaAdminController {
    @Autowired
    private Cinema_AdminService cinema_adminService;

    @GetMapping(path="/h")
    private String helloWorld() {
        return "cinema_login";
    }


    @PostMapping(path="/login")
    public String login(@RequestParam("username") String username,
                                      @RequestParam("password") String password){
        List<Cinema_Admin> admins = cinema_adminService.findByCinemaAdminUsernameAndPassword(username,password);
        if(admins.size()>0){
            return "manage";
        }
        else{
            return "Wrong";
        }

    }

    @PostMapping(path = "/add")
    public @ResponseBody String add(@RequestParam String username, String password)
    {
        cinema_adminService.add(username, password);
        return "Successfully registered!";
    }
}
