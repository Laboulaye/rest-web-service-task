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

    private static final String ID = "1";
    private static final String FIRSTNAME = "Anthony";
    private static final String LASTNAME = "Soprano";

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
    public void getAllUsersShouldReturnJsonObject() throws Exception {
        User user1 = new User(ID, FIRSTNAME, LASTNAME);
        User user2 = new User("2", "Carmella", "Soprano");

        List<User> list = Arrays.asList(user1, user2);
        when(userDao.getAllUsers()).thenReturn(list);

        List<User> listResponse = userController.getAllUsers();
        assertEquals(2, listResponse.size());
    }

    @Test
    public void getAllUsersShouldThrowException() throws Exception {
        when(userDao.getAllUsers()).thenThrow(UserNotFoundException.class);

        assertThatThrownBy(() -> mockMvc.perform(get("/users")).andExpect(status().isNotFound()))
                .hasCause(new UserNotFoundException());
    }

    @Test
    public void addUserShouldReturnJsonObject() throws Exception{
        User user = new User(ID, FIRSTNAME, LASTNAME);
        when(userDao.addUser(user)).thenReturn(user);

        User userResponse = userController.addUser(user);
        assertEquals(ID, userResponse.getId());
        assertEquals(FIRSTNAME, userResponse.getFirstname());
        assertEquals(LASTNAME, userResponse.getLastname());
    }

    @Test
    @Ignore
    public void addUserShouldThrowSameIdAlreadyExistException(){
        User user1 = new User(ID, FIRSTNAME, LASTNAME);
        User user2 = new User(ID, "Junior", "Carrado");
        if(user2.getId().equals(user1.getId())){
            when(userDao.addUser(user2)).thenThrow(UserWithSameIdAlreadyExistException.class);
        }

        assertThatThrownBy(() -> mockMvc.perform(post("/users")).andExpect(status().is4xxClientError()))
                .hasCause(new UserWithSameIdAlreadyExistException());
    }

    @Test
    public void getUserByIdShouldReturnJsonObject(){
        User user1 = new User(ID, FIRSTNAME, LASTNAME);
        User user2 = new User("2", "Carmella", "Soprano");
        when(userDao.getUser("1")).thenReturn(user1);

        User userResponse = userController.getUser("1");
        assertEquals(ID, userResponse.getId());
        assertEquals(FIRSTNAME, userResponse.getFirstname());
        assertEquals(LASTNAME, userResponse.getLastname());
    }

    @Test
    public void getUserByIdShouldThrowDoesNotExistException(){
        User user1 = new User(ID, FIRSTNAME, LASTNAME);
        User user2 = new User("2", "Carmella", "Soprano");
        String idError = "3";
        if(!idError.equals(user1.getId()) && !idError.equals(user2.getId())){
            when(userDao.getUser(idError)).thenThrow(UserDoesNotExistException.class);
        }
        assertThatThrownBy(() -> mockMvc.perform(get("/users/3")).andExpect(status().is4xxClientError()))
                .hasCause(new UserDoesNotExistException());
    }

    @Test
    public void editUserShouldReturnJsonObject(){
        User userUpdate = new User("2", "Carmella", "Soprano");
        when(userDao.editUser("1", userUpdate)).thenReturn(userUpdate);

        User userResponse = userController.editUser("1", userUpdate);
        assertEquals("2", userUpdate.getId());
        assertEquals("Carmella", userResponse.getFirstname());
        assertEquals("Soprano", userResponse.getLastname());
    }

    @Test
    public void deleteUserShouldTransmitToUserdao(){
        User user1 = new User(ID, FIRSTNAME, LASTNAME);
        userController.deleteUser(ID);
        verify(userDao, times(1)).deleteUser(ID);
    }
}
