package com.boekhoud.backendboekhoudapplicatie.presentation;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.RoleType;
import com.boekhoud.backendboekhoudapplicatie.dto.CreateUserDTO;
import com.boekhoud.backendboekhoudapplicatie.dto.EditUserDTO;
import com.boekhoud.backendboekhoudapplicatie.dto.UserLoginDTO;
import com.boekhoud.backendboekhoudapplicatie.dto.UserDTO;
import com.boekhoud.backendboekhoudapplicatie.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/create")
    public UserDTO registerUser(@RequestBody CreateUserDTO createUserDTO, @RequestParam Long roleId) {
        RoleType roleType = convertRoleIdToRoleType(roleId);
        return userService.createUser(createUserDTO, roleType);
    }

    private RoleType convertRoleIdToRoleType(Long roleId) {
        if (roleId == 1L) return RoleType.ADMIN;
        if (roleId == 2L) return RoleType.CLIENT;
        if (roleId == 3L) return RoleType.ACCOUNTANT;

        throw new IllegalArgumentException("Invalid roleId: " + roleId);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@RequestBody UserLoginDTO loginDTO, HttpSession session) {
        try {
            UserDTO loggedInUser = userService.loginUser(loginDTO);
            session.setAttribute("loggedInUser", loggedInUser);
            return ResponseEntity.ok(loggedInUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(null);
        }
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody EditUserDTO editUserDTO, @RequestParam(required = false) String role) {
        return userService.updateUser(id, editUserDTO, role);
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
}
