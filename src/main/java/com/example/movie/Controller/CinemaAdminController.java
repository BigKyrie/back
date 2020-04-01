package com.example.movie.Controller;

//import com.example.movie.Annotation.CurrentUser;
import com.example.movie.Annotation.CurrentUser;
import com.example.movie.Annotation.LoginRequired;
import com.example.movie.Entity.Cinema;
import com.example.movie.Entity.Cinema_Admin;
import com.example.movie.Entity.Movie;
//import com.example.movie.Form.*;
import com.example.movie.Entity.User;
import com.example.movie.Service.AuthenticationService;
import com.example.movie.Service.Cinema_AdminService;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.example.movie.Service.MovieService;
import com.example.movie.Session.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.apache.shiro.subject.Subject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.ServletRequestAttributes;
//import sun.security.pkcs11.wrapper.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import static org.apache.shiro.web.util.WebUtils.getRequest;

@Controller
@RequestMapping(path = "/cinemaAdmin")
public class CinemaAdminController {
    @Autowired
    private Cinema_AdminService cinema_adminService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping(path="/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password) throws JSONException {
        //cinema_adminService.loginAuthentication(username,password);
        List<Cinema_Admin> admins = cinema_adminService.findByCinemaAdminUsernameAndPassword(username,password);
        //Subject currentUser = SecurityUtils.getSubject();
        //Cinema_Admin admin=(Cinema_Admin) currentUser.getPrincipal();
        //model.addAttribute("username",admin.getUsername());
        //HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //Cinema_Admin admin=(Cinema_Admin) request.getSession().getAttribute("currentUser");
        //model.addAttribute("username",admin.getUsername());
        List<Cinema_Admin> adminExist = cinema_adminService.findByCinemaAdminUsername(username);
        //JSONObject jsonObject = new JSONObject();
        if(adminExist.size()>0) {
            if(admins.size()>0){
                //String token = authenticationService.getToken(admins.get(0));
                //jsonObject.put("token", token);
                //jsonObject.put("user", admins.get(0));
                //model.addAttribute("user",user);
                UserInfo userInfo = new UserInfo(admins.get(0).getId(), admins.get(0).getUsername());
                HttpSession session = getRequest().getSession();
               // session.setAttribute(Constants.SESSION_KEY_USER,user.getId());
                session.setAttribute("user_info_in_the_session", userInfo);
                return "redirect:/manage";
            }
            else{
                //jsonObject.put("message", "Password is not correct");
                return "redirect:/login";
            }
        }
        else {
            //jsonObject.put("message", "Username does not exist");
            return "redirect:/login";
        }
        //return jsonObject;

    }
    @PostMapping(path = "/add")
    public String add(@RequestParam String username, String password)
    {
        cinema_adminService.add(username, password);
        return "redirect:/login";
    }

    @GetMapping(path = "/allMovies")
    public String displayAllMovies(Model model)
    {
        HttpSession session = getRequest().getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("user_info_in_the_session");
        List<Movie> movies=movieService.display_all_movies();
        model.addAttribute("movies",movies);
        model.addAttribute("username",userInfo.getUsername());
        return "manage_movie";
    }

    @GetMapping(path = "/addMovieForm")
    public String displayMovieForm(Model model)
    {
        HttpSession session = getRequest().getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("user_info_in_the_session");
        model.addAttribute("username",userInfo.getUsername());
        return "movie_form";
    }


    @PostMapping(path = "/addMovies")
    public String addMovies(@RequestParam String title, String blurb, String certificate, String director, String actors,
                            String showtime, Integer duration, String type, String language, String url)
    {
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            movieService.addMovies(title,blurb,certificate,director,actors,
                    sdf.parse(showtime),duration,type,language,url);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "redirect:/cinemaAdmin/allMovies";
    }

    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

}
