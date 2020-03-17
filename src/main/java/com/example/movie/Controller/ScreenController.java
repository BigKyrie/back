package com.example.movie.Controller;

import com.example.movie.Entity.Screen;
import com.example.movie.Service.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path = "/screens")
public class ScreenController {
    @Autowired
    private ScreenService screenService;

    @GetMapping(path = "/allScreens")
    public @ResponseBody
    List<Screen> display_all_screens(){return screenService.display_all_screens();}
}
