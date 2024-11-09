package com.boekhoud.backendboekhoudapplicatie.service;

import com.boekhoud.backendboekhoudapplicatie.dal.MockUserDal;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserServiceTest {

    private MockUserDal mockUserDal;

    @BeforeEach
    void setUp() {
        mockUserDal = new MockUserDal();
        mockUserDal.clearStorage();
    }

    @Test
    void testFindUserById() {
        User user = new User();
        user.setUsername("testUser");
        User savedUser = mockUserDal.save(user);

        Optional<User> foundUser = mockUserDal.findById(savedUser.getId());
        assertTrue(foundUser.isPresent());
        assertEquals("testUser", foundUser.get().getUsername());
    }

    @Test
    void testSaveUser() {
        User userToSave = new User();
        userToSave.setUsername("newUser");

        User savedUser = mockUserDal.save(userToSave);

        assertEquals(1L, savedUser.getId());
        assertEquals("newUser", savedUser.getUsername());
    }

    @Test
    void testDeleteUserById() {
        User user = new User();
        user.setUsername("userToDelete");
        User savedUser = mockUserDal.save(user);

        mockUserDal.deleteById(savedUser.getId());

        Optional<User> deletedUser = mockUserDal.findById(savedUser.getId());
        assertTrue(deletedUser.isEmpty());
    }

    @Test
    void testFindUserByUsername() {
        User user = new User();
        user.setUsername("testUser");
        mockUserDal.save(user);

        Optional<User> foundUser = mockUserDal.findByUsername("testUser");
        assertTrue(foundUser.isPresent());
        assertEquals("testUser", foundUser.get().getUsername());
    }
}
