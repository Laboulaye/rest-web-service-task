package com.study.restwebservicetask.Controller;


import com.study.restwebservicetask.Dao.UserDao;
import com.study.restwebservicetask.Model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value = "Home page")
    public String welcome(){
        return "Welcome to Homepage REST web App";
    }

    @GetMapping("/users")
    @ApiOperation(value = "View a list of available users", response = List.class)
    public List<User> getUsers(){
        return userDao.getAllUsers();
    }

    @PostMapping("/users")
    @ApiOperation(value = "Add user")
    public User addUser(@ApiParam(value = "User object for add", required = true)
                            @RequestBody User user){
        System.out.println("(Service Side) Creating User: " + user.getId());
        return userDao.addUser(user);
    }

    @GetMapping("/users/{id}")
    @ApiOperation(value = "Get user by Id")
    public User getUser(@ApiParam(value = "User Id from which user object will retrieve", required = true)
                            @PathVariable("id") String id){
        return userDao.getUser(id);
    }

    @PutMapping("/users/{userId}")
    @ApiOperation(value = "Update user")
    public User editUser(@ApiParam(value = "User Id from which user object will update", required = true)
                             @PathVariable("userId") String userId,
                         @ApiParam(value = "Update user object", required = true)
                             @RequestBody User userUpdate){
        System.out.println("(Service Side) Editing User: " + userId);
        return userDao.editUser(userId, userUpdate);
    }

    @DeleteMapping("users/{userId}")
    @ApiOperation(value = "Delete user")
    public void deleteUser(@ApiParam(value = "User Id from which user object will delete", required = true)
                               @PathVariable("userId") String userId){
        System.out.println("(Service Side) Deleting User: " + userId);
        userDao.deleteUser(userId);
    }

    @GetMapping("/search")
    @ApiOperation(value = "Find user by name")
    public User findUserByName(@ApiParam(value = "User name for search user info", required = true) @RequestParam(name="name") String name){
        return userDao.findUserByName(name);
    }

}
