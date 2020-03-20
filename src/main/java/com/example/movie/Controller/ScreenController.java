package com.example.movie.Controller;

import com.example.movie.Entity.Cinema;
import com.example.movie.Entity.Screen;
import com.example.movie.Service.ScreenService;
import com.example.movie.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path = "/screen")
public class ScreenController {
    @Autowired
    private ScreenService screenService;

    @GetMapping(path="/getAllScreens")
    public String allScreen( Model model) {
        List<Screen> screens = screenService.display_all_screens();
        model.addAttribute("screens", screens);
        return "screen_list";
    }
}
