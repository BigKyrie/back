package com.example.movie.Controller;

import com.example.movie.Entity.Cinema_Admin;
import com.example.movie.Entity.Screen;
import com.example.movie.Service.CinemaService;
import com.example.movie.Service.Cinema_AdminService;
import com.example.movie.Service.ScreenService;
import com.example.movie.Session.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
}
