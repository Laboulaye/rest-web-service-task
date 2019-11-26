package com.study.restwebservicetask.Controller;


import com.study.restwebservicetask.Dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserDao userDao;

    @RequestMapping("/")
    public String welcome(){
        return "Welcome to Homepage REST web App";
    }
}
