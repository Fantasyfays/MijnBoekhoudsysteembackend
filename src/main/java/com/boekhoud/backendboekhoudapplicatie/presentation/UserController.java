// UserController.java
package com.boekhoud.backendboekhoudapplicatie.presentation;

import com.boekhoud.backendboekhoudapplicatie.dto.UserDTO;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.RoleType;
import com.boekhoud.backendboekhoudapplicatie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO, @RequestParam Long roleId) {
        RoleType roleType = convertRoleIdToRoleType(roleId);
        return userService.createUser(userDTO, roleType); // Gebruik een enkele RoleType in plaats van een lijst
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO, @RequestParam Long roleId) {
        RoleType roleType = convertRoleIdToRoleType(roleId);
        return userService.updateUser(id, userDTO, roleType); // Gebruik een enkele RoleType in plaats van een lijst
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    private RoleType convertRoleIdToRoleType(Long roleId) {
        if (roleId == 1L) return RoleType.ADMIN;
        if (roleId == 2L) return RoleType.CLIENT;
        if (roleId == 3L) return RoleType.ACCOUNTANT;

        throw new IllegalArgumentException("Ongeldig roleId: " + roleId);
    }
}
