package com.boekhoud.backendboekhoudapplicatie.presentation;

import com.boekhoud.backendboekhoudapplicatie.enums.RoleType;
import com.boekhoud.backendboekhoudapplicatie.dto.*;
import com.boekhoud.backendboekhoudapplicatie.exception.InvalidCredentialsException;
import com.boekhoud.backendboekhoudapplicatie.exception.UserNotFoundException;
import com.boekhoud.backendboekhoudapplicatie.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<UserDTO> registerUser(@RequestBody CreateUserDTO createUserDTO, @RequestParam Long roleId) {
        try {
            RoleType roleType = convertRoleIdToRoleType(roleId);
            UserDTO newUser = userService.createUser(createUserDTO, roleType);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@RequestBody UserLoginDTO loginDTO, HttpSession session) {
        try {
            UserDTO loggedInUser = userService.loginUser(loginDTO);
            session.setAttribute("loggedInUser", loggedInUser);
            return ResponseEntity.ok(loggedInUser);
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id,
                                        @RequestBody UpdateUserDTO updateUserDTO,
                                        @RequestParam(required = false) Long roleId) {
        try {
            RoleType roleType = roleId != null ? convertRoleIdToRoleType(roleId) : null;
            UserDTO updatedUser = userService.updateUser(id, updateUserDTO, roleType != null ? roleType.name() : null);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid role ID or data.");
        }
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<?> updatePassword(@PathVariable Long id, @RequestBody UpdatePasswordDTO updatePasswordDTO) {
        try {
            userService.updatePassword(id, updatePasswordDTO);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with id: " + id);
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Current password is incorrect.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the password.");
        }
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        try {
            UserDTO user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private RoleType convertRoleIdToRoleType(Long roleId) {
        switch (roleId.intValue()) {
            case 1: return RoleType.ADMIN;
            case 2: return RoleType.CLIENT;
            case 3: return RoleType.ACCOUNTANT;
            default: throw new IllegalArgumentException("Invalid roleId: " + roleId);
        }
    }
}
