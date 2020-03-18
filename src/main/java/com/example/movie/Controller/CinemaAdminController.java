package com.example.movie.Controller;

import com.example.movie.Service.Cinema_AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/cinemaAdmin")
public class CinemaAdminController {
    @Autowired
    private Cinema_AdminService cinema_adminService;

    @PostMapping(path = "/add")
    public @ResponseBody String add(@RequestParam String username, String password)
    {
        cinema_adminService.add(username, password);
        return "Successfully registered!";
    }
}
