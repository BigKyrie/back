package com.example.movie.Controller;

import com.example.movie.Annotation.CurrentUser;
import com.example.movie.Entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class WebApplicationController {
    @GetMapping(path = "/register")
    private String adminRegister() {
        return "register";
    }

    @GetMapping(path="/login")
    private String adminLogin() {
        return "cinema_login";
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
