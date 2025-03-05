package com.bestellen.click_munch.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User userA, userB;

    @BeforeEach
    void setUp() {
        userA = new User(
                1, "userA", "testA@test.com",
                "passwordA", "passA", "3142589654", true, Set.of());
        userB = new User(
                2, "userB", "testB@test.com",
                "passwordB", "passB", "3142589654", true, Set.of());

    }

    @Test
    void shouldFindAll() {
        when(userService.findAll()).thenReturn(List.of(userA, userB));
        assertEquals(List.of(userA, userB), userController.findAll());
    }

    @Test
    void shouldFindById() {
        when(userService.findById(1)).thenReturn(userA);
        assertEquals(userA, userController.findById(1));
    }

    @Test
    void shouldFindByUsername() {
        when(userService.findByUsername("userA")).thenReturn(userA);
        assertEquals(userA, userController.findByUsername("userA"));
    }


}
