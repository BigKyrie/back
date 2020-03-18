package com.example.movie.Controller;

import com.example.movie.Service.UserService;
import com.example.movie.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/demo")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(path = "/index")
    private String helloWorld() {
        return "hello";
    }

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
        return userService.findByUsernameAndPassword(username,password);
    }

    @PostMapping(path = "/add")
    public @ResponseBody boolean add(@RequestParam String username,String password)
    {
        return userService.add(username, password);
    }


}
