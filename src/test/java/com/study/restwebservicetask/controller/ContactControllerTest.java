package com.study.restwebservicetask.controller;

import com.study.restwebservicetask.dao.ContactDao;
import com.study.restwebservicetask.exception.contact.ContactDoesNotExistException;
import com.study.restwebservicetask.exception.contact.ContactNotFoundException;
import com.study.restwebservicetask.exception.contact.ContactWithSameIdAlreadyExistException;
import com.study.restwebservicetask.exception.user.UserDoesNotExistException;
import com.study.restwebservicetask.exception.user.UserNotFoundException;
import com.study.restwebservicetask.model.Contact;
import com.study.restwebservicetask.model.User;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class ContactControllerTest {

    private MockMvc mockMvc;

    @Mock
    ContactDao contactDao;

    @InjectMocks
    ContactController contactController;

    private User user1 = new User("1", "Anthony", "Soprano");
    private User user2 = new User("2", "Junior", "Carrado");
    private Contact contact1 = new Contact("1", "AJ", "Soprano", "+7-999-50");
    private Contact contact2 = new Contact("2", "Madow", "Soprano", "+7-999-60");

    private final String JSON_REQUEST = "{\n" +
            "\"id\" : \"1\",\n" +
            "\"firstname\" : \"AJ\",\n" +
            "\"lastname\" : \"Soprano\",\n" +
            "\"phone\" : \"+7-999-60\"\n" +
            "}";

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(contactController).build();
    }

    @Test
    public void getAllContactsShouldReturnJsonObject(){
        List<Contact> list = Arrays.asList(contact1, contact2);
        when(contactDao.getAllContacts("1")).thenReturn(list);

        List<Contact> listResponse = contactController.getAllContacts("1");
        assertEquals(2, listResponse.size());
    }

    @Test
    public void getAllContactsShouldThrowUserDoesNotExistException(){
        when(contactDao.getAllContacts("3")).thenThrow(UserDoesNotExistException.class);

        assertThatThrownBy(() -> mockMvc.perform(get("/users/3/contacts")).andExpect(status().is4xxClientError()))
                .hasCause(new UserDoesNotExistException());
    }

    @Test
    public void getAllContactsShouldThrowContactNotFoundException(){
        when(contactDao.getAllContacts("2")).thenThrow(ContactNotFoundException.class);

        assertThatThrownBy(() -> mockMvc.perform(get("/users/2/contacts")).andExpect(status().is4xxClientError()))
                .hasCause(new ContactNotFoundException());
    }

    @Test
    public void addContactShouldReturnJsonObject(){
        when(contactDao.addContact("1", contact1)).thenReturn(contact1);

        Contact contactResponse = contactController.addContact("1", contact1);
        assertEquals(contactResponse.getId(), contact1.getId());
        assertEquals(contactResponse.getFirstname(), contact1.getFirstname());
        assertEquals(contactResponse.getLastname(), contact1.getLastname());
        assertEquals(contactResponse.getPhone(), contact1.getPhone());
    }

    @Test
    public void addContactShouldThrowUserNotExistException(){
        List<String> listId = Arrays.asList(user1.getId(), user2.getId());
        String userIdError = "3";
        if(!listId.contains(userIdError)){
            when(contactDao.addContact(eq(userIdError), any(Contact.class))).thenThrow(UserDoesNotExistException.class);
        }

        assertThatThrownBy(() -> mockMvc.perform(post("/users/3/contacts").accept(APPLICATION_JSON)
                .content(JSON_REQUEST)
                .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError()))
                .hasCause(new UserDoesNotExistException());
    }

    @Test
    public void addContactShouldReturnContactSameIdExistException() {
        List<String> contactIdList = Arrays.asList(contact1.getId(), contact2.getId());
        String contactIdError = "2";
        if(contactIdList.contains(contactIdError)){
            when(contactDao.addContact(eq("1"), any(Contact.class))).thenThrow(ContactWithSameIdAlreadyExistException.class);
        }

        assertThatThrownBy(() -> mockMvc.perform(post("/users/1/contacts").accept(APPLICATION_JSON)
                .content(JSON_REQUEST)
                .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError()))
                .hasCause(new ContactWithSameIdAlreadyExistException());
    }

    @Test
    public void getContactByIdShouldReturnJsonObject() {
        when(contactDao.getContact(eq("1"), eq("1"))).thenReturn(contact1);

        Contact contactResponse = contactController.getContact("1", "1");
        assertEquals(contactResponse.getId(), contactResponse.getId());
        assertEquals(contactResponse.getFirstname(), contact1.getFirstname());
        assertEquals(contactResponse.getLastname(), contact1.getLastname());
        assertEquals(contactResponse.getPhone(), contact1.getPhone());
    }

    @Test
    public void getContactByIdShouldThrowUserDoesNotExistException(){
        List<String> userIdList = Arrays.asList(user1.getId(), user2.getId());
        String userIdError = "3";
        if(!userIdList.contains(userIdError)){
            when(contactDao.getContact(eq(userIdError), anyString())).thenThrow(UserDoesNotExistException.class);
        }

        assertThatThrownBy(() -> mockMvc.perform(get("/users/3/contacts/1")).andExpect(status().is4xxClientError()))
                .hasCause(new UserDoesNotExistException());
    }

    @Test
    public void getContactByIdShouldThrowContactDoesNotExistException(){
        List<String> contactIdList = Arrays.asList(contact1.getId(), contact2.getId());
        String contactIdError = "3";
        if(!contactIdList.contains(contactIdError)){
            when(contactDao.getContact(anyString(), eq(contactIdError))).thenThrow(ContactDoesNotExistException.class);
        }

        assertThatThrownBy(() -> mockMvc.perform(get("/users/1/contacts/3")).andExpect(status().is4xxClientError()))
                .hasCause(new ContactDoesNotExistException());
    }

    @Test
    public void editContactShouldReturnJsonObject(){
        when(contactDao.editContact("1", "2", contact1)).thenReturn(contact1);

        Contact contactResponse = contactController.editContact("1", "2", contact1);
        assertEquals(contactResponse.getId(), contact1.getId());
        assertEquals(contactResponse.getFirstname(), contact1.getFirstname());
        assertEquals(contactResponse.getLastname(), contact1.getLastname());
        assertEquals(contactResponse.getPhone(), contact1.getPhone());
    }

    @Test
    public void editContactShouldThrowUserDoesNotExistException(){
        List<String> listUserId = Arrays.asList(user1.getId(), user2.getId());
        String userIdError = "3";
        if(!listUserId.contains(userIdError)){
            when(contactDao.editContact(eq(userIdError), anyString(), any(Contact.class))).thenThrow(UserDoesNotExistException.class);
        }

        assertThatThrownBy(() -> mockMvc.perform(put("/users/3/contacts/2").accept(APPLICATION_JSON)
                .content(JSON_REQUEST)
                .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError()))
                .hasCause(new UserDoesNotExistException());
    }

    @Test
    public void editContactShouldTrowContactDoesNotExistException(){
        List<String> contactIdList = Arrays.asList(contact1.getId(), contact2.getId());
        String contactIdError = "3";
        if(!contactIdList.contains(contactIdError)){
            when(contactDao.editContact(anyString(), eq(contactIdError), any(Contact.class))).thenThrow(ContactDoesNotExistException.class);
        }

        assertThatThrownBy(() -> mockMvc.perform(put("/users/1/contacts/3").accept(APPLICATION_JSON)
                .content(JSON_REQUEST)
                .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError()))
                .hasCause(new ContactDoesNotExistException());
    }

    @Test
    public void deleteContactShouldTransmitToContactdao(){
        contactController.deleteContact("1", "1");
        verify(contactDao, times(1)).deleteContact("1", "1");
    }
}
