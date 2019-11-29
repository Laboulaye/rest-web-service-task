package com.study.restwebservicetask.Dao;

import com.study.restwebservicetask.Model.User;
import com.study.restwebservicetask.exception.user.UserWithSameIdAlreadyExistException;
import com.study.restwebservicetask.exception.user.UserDoesNotExistException;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserDao {

    private static Map<String, User> userMap = new HashMap<>();

    public User getUser(String userId){
        catchUserForValidity(userId);
        return userMap.get(userId);
    }

    public User addUser(User user){
        if(userMap.containsKey(user.getId())){
            throw new UserWithSameIdAlreadyExistException();
        }
        else{
            userMap.put(user.getId(), user);
            return user;
        }
    }

    public User editUser(String userId, User userUpdate){
        catchUserForValidity(userId);

        User userOrigin = userMap.get(userId);
        userOrigin.setId(userUpdate.getId());
        userOrigin.setFirstname(userUpdate.getFirstname());
        userOrigin.setLastname(userUpdate.getLastname());
        userMap.remove(userId);
        userMap.put(userUpdate.getId(), userOrigin);
        return userUpdate;
    }

    public void deleteUser(String userId){
        catchUserForValidity(userId);
        userMap.remove(userId);
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

    private void catchUserForValidity(String userId){
        if(!userMap.containsKey(userId)){
            throw new UserDoesNotExistException();
        }
    }


}
