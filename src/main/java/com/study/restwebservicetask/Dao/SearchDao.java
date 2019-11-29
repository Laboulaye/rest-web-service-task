package com.study.restwebservicetask.Dao;

import com.study.restwebservicetask.Model.Contact;
import com.study.restwebservicetask.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class SearchDao {

    @Autowired
    UserDao userDao;

    public List<User> findUserBySomeInfo(User userInfo){
        List<User> list = new ArrayList<>(userDao.getUserMap().values());
        List<User> listFind = new ArrayList<>();
        for(User user: list){
            if(user.getId().equals(userInfo.getId())){
                listFind.add(user);
                break;
            }
            else if(user.getFirstname().equals(userInfo.getFirstname()) &&
                    user.getLastname().equals(userInfo.getLastname())) {
                listFind.add(user);
            }
            else if(user.getFirstname().equals(userInfo.getFirstname()) && userInfo.getLastname()== null){
                listFind.add(user);
            }
            else if(user.getLastname().equals(userInfo.getLastname()) && userInfo.getFirstname()== null){
                listFind.add(user);
            }
        }
        return listFind;
    }

    public Contact findContactByPhone(String phone){
        List<User> userList = userDao.getAllUsers();
        for(User user: userList){
            List<Contact> contactList = new ArrayList<>(user.getContacts().values());
            for(Contact contact: contactList){
                if (contact.getPhone().equals(phone)){
                    return contact;
                }
            }
        }
        return null;
    }
}
