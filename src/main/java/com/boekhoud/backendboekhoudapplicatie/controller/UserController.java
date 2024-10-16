package com.boekhoud.backendboekhoudapplicatie.controller;

import com.boekhoud.backendboekhoudapplicatie.models.Role;
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

    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get user by ID
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // Create new user
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    // Update user
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userService.updateUser(id, userDetails);
    }

    // Delete user
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    // Assign role to user
    @PostMapping("/{userId}/roles/{roleName}")
    public User assignRoleToUser(@PathVariable Long userId, @PathVariable String roleName) {
        return userService.assignRoleToUser(userId, roleName);
    }

    // Get all roles
    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return userService.getAllRoles();
    }

    // Create new role
    @PostMapping("/roles")
    public Role createRole(@RequestBody Role role) {
        return userService.addRole(role);
    }

    // Delete role
    @DeleteMapping("/roles/{id}")
    public void deleteRole(@PathVariable Long id) {
        userService.deleteRole(id);
    }
}
