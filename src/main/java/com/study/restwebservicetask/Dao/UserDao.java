package com.study.restwebservicetask.Dao;

import com.study.restwebservicetask.Model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserDao {

    private static Map<String, User> userMap = new HashMap<>();

    public User getUser(String id){
        return userMap.get(id);
    }

    public User addUser(User user){
        userMap.put(user.getId(), user);
        return user;
    }

    public User editUser(User user){
        userMap.put(user.getId(), user);
        return user;
    }

    public void deleteUser(String id){
        userMap.remove(id);
    }

    public List<User> getAllUsers(){
        Collection<User> users = userMap.values();
        return new ArrayList<>(users);
    }

    public User findUserByName(String name){
        Collection<User> users = userMap.values();
        List<User> list = new ArrayList<>(users);
        for(User user: list){
            if(user.getFirstname().equals(name)) {
                return user;
            }
        }
        return null;
    }


}
