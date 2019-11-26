package com.study.restwebservicetask.Controller;

import com.study.restwebservicetask.Dao.ContactDao;
import com.study.restwebservicetask.Model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/{userId}")
public class ContactController {

    @Autowired
    ContactDao contactDao;

    @GetMapping("/contacts")
        public List<Contact> getAllContacts(@PathVariable("userId") String userId){
            return contactDao.getAllContacts(userId);
    }

    @PostMapping("/contact")
    public Contact addContact(@PathVariable("userId") String userId, @RequestBody Contact contact){
        System.out.println("(Service Side) Creating contact: #" + contact.getId() + " of user: #" + userId);
        return contactDao.addContact(userId, contact);
    }

    @GetMapping("/contact/{contactId}")
    public Contact getContact(@PathVariable("userId") String userId, @PathVariable("contactId") String contactId){
        return contactDao.getContact(userId, contactId);
    }

    @PutMapping("/contact")
    public Contact editContact(@PathVariable("userId") String userId, @RequestBody Contact contact){
        return contactDao.editContact(userId, contact);
    }

    @DeleteMapping("/contact/{contactId}")
    public void deleteContact(@PathVariable("userId") String userId, @PathVariable("contactId") String contactId){
        System.out.println("(Service Side) Deleting contact: #" + contactId + " of user: #" + userId );
        contactDao.deleteContact(userId, contactId);
    }

}
