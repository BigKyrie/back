package com.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/demo")
public class MainController {
    @Autowired
    private MainService mainService;

    @GetMapping(path = "/getAll")
    public @ResponseBody Iterable<User> getAllUsers()
    {
        return mainService.getAllUsers();
    }

    @PostMapping(path = "/getUser")
    public @ResponseBody List<User> findByUsername(String username)
    {
        return mainService.findByUsername(username);
    }
    @PostMapping(path = "/get")
    public @ResponseBody List<User> findByUsernameAndPassword(String username,String password)
    {
        return mainService.findByUsernameAndPassword(username,password);
    }

    @PostMapping(path = "/add")
    public @ResponseBody boolean add(@RequestParam String username,String password)
    {
        return mainService.add(username, password);
    }

//    @PostMapping(path = "/modify")
//    public @ResponseBody boolean modify(@RequestParam Integer id,@RequestParam String name)
//    {
//        return mainService.modify(id,name);
//    }
//
//    @PostMapping(path = "/delete")
//    public @ResponseBody boolean deleteByName(@RequestParam String name)
//    {
//        return mainService.deleteByName(name);
//    }
}
