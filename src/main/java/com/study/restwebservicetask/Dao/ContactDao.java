package com.study.restwebservicetask.Dao;

import com.study.restwebservicetask.Model.Contact;
import com.study.restwebservicetask.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Repository
public class ContactDao {

    private static Map<String, Contact> contactMap;

    @Autowired
    UserDao userDao;

    public Contact getContact(String userId, String contactId){
        User usr = userDao.getUser(userId);
        contactMap = usr.getContacts();
        return contactMap.get(contactId);
    }

    public Contact addContact(String userId, Contact contact){
        User usr = userDao.getUser(userId);
        contactMap = usr.getContacts();
        contactMap.put(contact.getId(), contact);
        return contact;
    }

    public Contact editContact(String userId, Contact contact){
        User usr = userDao.getUser(userId);
        contactMap = usr.getContacts();
        contactMap.put(contact.getId(), contact);
        return contact;
    }

    public void deleteContact(String userId, String id){
        User usr = userDao.getUser(userId);
        contactMap = usr.getContacts();
        contactMap.remove(id);
    }

    public List<Contact> getAllContacts(String userId){
        User usr = userDao.getUser(userId);
        if(usr.getContacts() == null){
            contactMap = new HashMap<>();
        }
        else{
            contactMap = usr.getContacts();
        }
        Collection<Contact> contacts = contactMap.values();
        return new ArrayList<>(contacts);
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
