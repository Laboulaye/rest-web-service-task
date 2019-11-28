package com.study.restwebservicetask.Controller;

import com.study.restwebservicetask.Dao.ContactDao;
import com.study.restwebservicetask.Model.Contact;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api
@RequestMapping("/users/{userId}")
public class ContactController {

    @Autowired
    ContactDao contactDao;

    @GetMapping("/contacts")
    @ApiOperation(value = "View a list of available user contacts", response = List.class)
    public List<Contact> getAllContacts(@PathVariable("userId") String userId){
        return contactDao.getAllContacts(userId);
    }

    @PostMapping("/contacts")
    @ApiOperation(value = "Add contact")
    public Contact addContact(@ApiParam(value = "User Id to add user contact object", required = true)
                                  @PathVariable("userId") String userId,
                              @ApiParam(value = "Contact object for add", required = true)
                                    @RequestBody Contact contact){
        System.out.println("(Service Side) Creating contact: #" + contact.getId() + " of user: #" + userId);
        return contactDao.addContact(userId, contact);
    }

    @GetMapping("/contacts/{contactId}")
    @ApiOperation(value = "Get contact by Id")
    public Contact getContact(@ApiParam(value = "User Id to get user contact object", required = true)
                                  @PathVariable("userId") String userId,
                              @ApiParam(value = "Contact Id from which contact object will retrieve", required = true)
                                    @PathVariable("contactId") String contactId){
        return contactDao.getContact(userId, contactId);
    }

    @PutMapping("/contacts")
    @ApiOperation(value = "Update contact")
    public Contact editContact(@ApiParam(value = "User Id to update user contact object", required = true)
                                   @PathVariable("userId") String userId,
                               @ApiParam(value = "Update contact object", required = true)
                               @RequestBody Contact contact){
        return contactDao.editContact(userId, contact);
    }

    @DeleteMapping("/contacts/{contactId}")
    @ApiOperation(value = "Delete contact")
    public void deleteContact(@ApiParam(value = "User Id to delete user contact object", required = true)
                                  @PathVariable("userId") String userId,
                              @ApiParam(value = "Contact Id from which contact object will delete", required = true)
                              @PathVariable("contactId") String contactId){
        System.out.println("(Service Side) Deleting contact: #" + contactId + " of user: #" + userId );
        contactDao.deleteContact(userId, contactId);
    }

    @GetMapping("/search")
    @ApiOperation(value = "Find contact by phone")
    public Contact findContactByPhone(@ApiParam(value = "Contact phone for search contact", required = true) @RequestParam(name="phone") String phone){
        return contactDao.findContactByPhone(phone);
    }

}
