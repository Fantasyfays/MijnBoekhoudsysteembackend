package com.boekhoud.backendboekhoudapplicatie.controller;

import com.boekhoud.backendboekhoudapplicatie.models.User;
import com.boekhoud.backendboekhoudapplicatie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public User createUser(@RequestBody User user, @RequestParam Long roleId) {
        return userService.addUser(user, roleId);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails, @RequestParam Long roleId) {
        return userService.updateUser(id, userDetails, roleId);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
