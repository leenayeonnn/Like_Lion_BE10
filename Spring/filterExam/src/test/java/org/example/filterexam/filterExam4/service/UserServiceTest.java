package org.example.filterexam.filterExam4.service;

import static org.junit.jupiter.api.Assertions.*;

import org.example.filterexam.filterExam4.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    void registerNewUser() {
        User user = new User();
        user.setUsername("lee");
        user.setName("lee");
        user.setPassword("1234");
        user.setEmail("lee@email.com");

        User user1 = userService.registerUser(user);

        assertNotNull(user1.getId());
    }
}