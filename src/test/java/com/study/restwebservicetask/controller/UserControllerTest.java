package com.study.restwebservicetask.controller;

import com.study.restwebservicetask.dao.UserDao;
import com.study.restwebservicetask.exception.user.UserDoesNotExistException;
import com.study.restwebservicetask.exception.user.UserNotFoundException;
import com.study.restwebservicetask.exception.user.UserWithSameIdAlreadyExistException;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    UserDao userDao;

    @InjectMocks
    UserController userController;

    private User user1 = new User("1", "Anthony", "Soprano");
    private User user2 = new User("2", "Carmella", "Soprano");

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void welcomeShouldReturnString() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome to Homepage REST web App"));
    }

    @Test
    public void getAllUsersShouldReturnJsonObject()  {
        List<User> list = Arrays.asList(user1, user2);
        when(userDao.getAllUsers()).thenReturn(list);

        List<User> listResponse = userController.getAllUsers();
        assertEquals(2, listResponse.size());
    }

    @Test
    public void getAllUsersShouldThrowException()  {
        when(userDao.getAllUsers()).thenThrow(UserNotFoundException.class);

        assertThatThrownBy(() -> mockMvc.perform(get("/users")).andExpect(status().isNotFound()))
                .hasCause(new UserNotFoundException());
    }

    @Test
    public void addUserShouldReturnJsonObject(){
        when(userDao.addUser(user1)).thenReturn(user1);

        User userResponse = userController.addUser(user1);
        assertEquals("1", userResponse.getId());
        assertEquals("Anthony", userResponse.getFirstname());
        assertEquals("Soprano", userResponse.getLastname());
    }

    @Test
    public void addUserShouldThrowSameIdAlreadyExistException(){
        List<String> contactIdList = Arrays.asList(user1.getId(), user2.getId());
        String contactIdError = "2";
        if(contactIdList.contains(contactIdError)){
            when(userDao.addUser(any(User.class))).thenThrow(UserWithSameIdAlreadyExistException.class);
        }

        final String JSON_REQUEST = "{\n" +
                "\"id\" : \"2\",\n" +
                "\"firstname\" : \"AJ\",\n" +
                "\"lastname\" : \"Soprano\"\n" +
                "}";

        assertThatThrownBy(() -> mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON_REQUEST))
                .andExpect(status().is4xxClientError()))
                .hasCause(new UserWithSameIdAlreadyExistException());
    }

    @Test
    public void getUserByIdShouldReturnJsonObject(){
        when(userDao.getUser("1")).thenReturn(user1);

        User userResponse = userController.getUser("1");
        assertEquals("1", userResponse.getId());
        assertEquals("Anthony", userResponse.getFirstname());
        assertEquals("Soprano", userResponse.getLastname());
    }

    @Test
    public void getUserByIdShouldThrowDoesNotExistException(){
        List<String> userIdList = Arrays.asList(user1.getId(), user2.getId());
        String idError = "3";
        if(!userIdList.contains(idError)){
            when(userDao.getUser(idError)).thenThrow(UserDoesNotExistException.class);
        }
        assertThatThrownBy(() -> mockMvc.perform(get("/users/3")).andExpect(status().is4xxClientError()))
                .hasCause(new UserDoesNotExistException());
    }

    @Test
    public void editUserShouldReturnJsonObject(){
        when(userDao.editUser("1", user2)).thenReturn(user2);

        User userResponse = userController.editUser("1", user2);
        assertEquals("2", user2.getId());
        assertEquals("Carmella", userResponse.getFirstname());
        assertEquals("Soprano", userResponse.getLastname());
    }

    @Test
    public void deleteUserShouldTransmitToUserdao(){
        userController.deleteUser("1");
        verify(userDao, times(1)).deleteUser("1");
    }
}
