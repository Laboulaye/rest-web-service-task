package com.study.restwebservicetask.controller;

import com.study.restwebservicetask.Controller.UserController;
import com.study.restwebservicetask.Dao.UserDao;
import com.study.restwebservicetask.Model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserDao userDao;

    @Test
    public void addUserTest(){

        User user = new User("1", "Anthony", "Soprano", new HashMap<>());
        User testUser = userController.addUser(user);

        assertNotNull(testUser);
        assertThat(testUser.getId().equals(user.getId()));
        assertThat(testUser.getFirstname().equals(user.getFirstname()));
        assertThat(testUser.getLastname().equals(user.getLastname()));
    }

    @Test
    public void getAllUsersTest(){
        User user1 = new User("1", "Anthony", "Soprano", new HashMap<>());
        User user2 = new User("2", "Carmella", "Soprano", new HashMap<>());
        List<User> listExpected = new ArrayList<>(Arrays.asList(user1, user2));

        when(userDao.getAllUsers()).thenReturn(listExpected);
        List<User> listResult = userController.getUsers();

        assertThat(listResult.size()).isEqualTo(2);
        assertThat(listResult.get(0).getFirstname())
                .isEqualTo(user1.getFirstname());
    }

}
