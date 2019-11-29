package com.study.restwebservicetask.Dao;

import com.study.restwebservicetask.Model.User;
import com.study.restwebservicetask.exception.user.UserWithSameIdAlreadyExistException;
import com.study.restwebservicetask.exception.user.UserDoesNotExistException;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserDao {

    private  Map<String, User> userMap = new HashMap<>();

    public Map<String, User> getUserMap() {
        return userMap;
    }

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
        return new ArrayList<>(userMap.values());
    }

    private void catchUserForValidity(String userId){
        if(!userMap.containsKey(userId)){
            throw new UserDoesNotExistException();
        }
    }


}
