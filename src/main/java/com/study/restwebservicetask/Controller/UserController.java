package com.study.restwebservicetask.Controller;


import com.study.restwebservicetask.Dao.UserDao;
import com.study.restwebservicetask.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserDao userDao;

    @RequestMapping("/")
    public String welcome(){
        return "Welcome to Homepage REST web App";
    }
    @GetMapping("/users")
    public List<User> getUsers(){
        return userDao.getAllUsers();
    }
    @PostMapping("/user")
    public User addUser(@RequestBody User user){
        System.out.println("(Service Side) Creating User: " + user.getId());
        return userDao.addUser(user);
    }
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id") String id){
        return userDao.getUser(id);
    }
    @PutMapping("/user")
    public User editUser(@RequestBody User user){
        System.out.println("(Service Side) Editing User: " + user.getId());
        return userDao.editUser(user);
    }
    @DeleteMapping("user/{id}")
    public void deleteUser(@PathVariable("id") String id){
        System.out.println("(Service Side) Deleting User: " + id);
        userDao.deleteUser(id);
    }

}
