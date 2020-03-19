package com.example.movie.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class WebApplicationController {
    @GetMapping(path="/login")
    private String login() { return "cinema_admin_login"; }

    @GetMapping(path = "/register")
    private String register() {
        return "register";
    }

    @GetMapping(path = "/manage")
    public String manageMain(){
        return "manage";
    }

    @GetMapping(path = "/myCinema")
    public String myCinema(){
        return "cinema_form";
    }
}
