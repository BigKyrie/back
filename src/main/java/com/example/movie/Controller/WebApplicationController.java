package com.example.movie.Controller;

import org.hibernate.annotations.GeneratorType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class WebApplicationController {
    @GetMapping(path="/login")
    private String Login() { return "user_admin_selection"; }

    @GetMapping(path="/adminLogin")
    private String adminLogin() { return "cinema_admin_login"; }

    @GetMapping(path="/userLogin")
    private String userLogin() { return "user_login"; }

    @GetMapping(path = "/register")
    private String adminRegister() {
        return "register";
    }

    @GetMapping(path = "/manage")
    public String manageMain(){
        return "manage";
    }

    @GetMapping(path = "/addCinema")
    public String myCinema(){
        return "cinema_form";
    }

    @GetMapping(path = "/addScreen")
    public String myScreen() { return "screen_form"; }
}
