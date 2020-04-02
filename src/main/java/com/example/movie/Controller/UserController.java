package com.example.movie.Controller;

import com.example.movie.Service.UserService;
import com.example.movie.Entity.User;
import com.example.movie.Session.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(path = "/demo")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(path = "/getAll")
    public @ResponseBody Iterable<User> getAllUsers()
    {
        return userService.getAllUsers();
    }

    @PostMapping(path = "/getUser")
    public @ResponseBody List<User> findByUsername(String username)
    {
        return userService.findByUsername(username);
    }

    @PostMapping(path = "/get")
    public @ResponseBody List<User> findByUsernameAndPassword(String username,String password)
    {
        List<User> users=userService.findByUsernameAndPassword(username,password);
        if(users.size()>0) {
            UserInfo userInfo = new UserInfo(users.get(0).getId(), users.get(0).getUsername());
            HttpSession session = getRequest().getSession();
            session.setAttribute("current_normal_user", userInfo);
        }
        return users;
    }

    @PostMapping(path = "/add")
    public @ResponseBody boolean add(@RequestParam String username,String password)
    {
        return userService.add(username, password);
    }

    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

}
