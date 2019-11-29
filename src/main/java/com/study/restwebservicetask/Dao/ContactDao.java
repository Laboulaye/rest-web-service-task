package com.study.restwebservicetask.Dao;

import com.study.restwebservicetask.Model.Contact;
import com.study.restwebservicetask.Model.User;
import com.study.restwebservicetask.exception.contact.ContactDoesNotExistException;
import com.study.restwebservicetask.exception.contact.ContactWithSameIdAlreadyExistException;
import com.study.restwebservicetask.exception.user.UserDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ContactDao {

    private static Map<String, Contact> contactMap;

    @Autowired
    UserDao userDao;

    public Contact getContact(String userId, String contactId){
        catchUserForValidity(userId);
        catchContactForValidity(userId, contactId);
        return contactMap.get(contactId);
    }

    public Contact addContact(String userId, Contact contact){
        catchUserForValidity(userId);
        contactMap = userDao.getUser(userId).getContacts();
        if(contactMap.containsKey(contact.getId())){
            throw new ContactWithSameIdAlreadyExistException();
        }
        else {
            contactMap.put(contact.getId(), contact);
            return contact;
        }
    }

    public Contact editContact(String userId, String contactId, Contact contact){
        catchUserForValidity(userId);
        catchContactForValidity(userId, contactId);
        contactMap.remove(contactId);
        contactMap.put(contact.getId(), contact);
        return contact;
    }

    public void deleteContact(String userId, String contactId){
        catchUserForValidity(userId);
        catchContactForValidity(userId, contactId);
        contactMap.remove(contactId);
    }

    public List<Contact> getAllContacts(String userId){
        catchUserForValidity(userId);
        contactMap = userDao.getUser(userId).getContacts();
        Collection<Contact> contacts = contactMap.values();
        return new ArrayList<>(contacts);
    }

    private void catchUserForValidity(String userId){
        User usr = userDao.getUser(userId);
        if(usr == null){
            throw new UserDoesNotExistException();
        }
    }

    private void catchContactForValidity(String userId, String contactId){
        User usr = userDao.getUser(userId);
        contactMap = usr.getContacts();
        if(!contactMap.containsKey(contactId)){
            throw new ContactDoesNotExistException();

        }
    }

}
