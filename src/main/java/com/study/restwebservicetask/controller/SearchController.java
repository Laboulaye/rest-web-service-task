package com.study.restwebservicetask.controller;

import com.study.restwebservicetask.dao.SearchDao;
import com.study.restwebservicetask.model.Contact;
import com.study.restwebservicetask.model.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchDao searchDao;

    @PostMapping
    @ApiOperation(value = "Find user by some info. It can find user by id, or by firstname, or by lastname, or fullname")
    public List<User> findUserByName(@ApiParam(value = "User data for search user info", required = true)
                                     @RequestBody User user){
        return searchDao.findUserBySomeInfo(user);
    }

    @GetMapping
    @ApiOperation(value = "Find contact by phone")
    public Contact findContactByPhone(@ApiParam(value = "Contact phone for search contact", required = true)
                                          @RequestParam(name = "phone") String phone){
        return searchDao.findContactByPhone(phone);
    }
}
