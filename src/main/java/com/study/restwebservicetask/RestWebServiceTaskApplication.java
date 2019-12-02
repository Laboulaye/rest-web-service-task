package com.study.restwebservicetask;

import com.study.restwebservicetask.dao.UserDao;
import com.study.restwebservicetask.model.Contact;
import com.study.restwebservicetask.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class RestWebServiceTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestWebServiceTaskApplication.class, args);
        initBase();
    }

    public static void initBase(){
        User user1 = new User("1", "Anthony", "Soprano");
        User user2 = new User("2", "Christopher", "Moltiesanti");

        Contact contact1 = new Contact("1", "AJ", "Soprano", "8-999-85");
        Contact contact2 = new Contact("2", "Madow", "Soprano", "8-999-95");
        Contact contact3 = new Contact("1", "Adriana", "La Surva", "8-921-10");

        Map<String, Contact> mapContactsUser1 = new HashMap<>();
        mapContactsUser1.put(contact1.getId(), contact1);
        mapContactsUser1.put(contact2.getId(), contact2);
        user1.setContacts(mapContactsUser1);

        Map<String, Contact> mapContactsUser2 = new HashMap<>();
        mapContactsUser2.put(contact3.getId(), contact3);
        user2.setContacts(mapContactsUser2);

        UserDao.userMap.put(user1.getId(), user1);
        UserDao.userMap.put(user2.getId(), user2);
    }


}
