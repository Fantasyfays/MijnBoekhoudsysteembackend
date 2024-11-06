package com.boekhoud.backendboekhoudapplicatie.presentation;

import com.boekhoud.backendboekhoudapplicatie.dto.LoginDTO;
import com.boekhoud.backendboekhoudapplicatie.dto.LoginResponseDTO;
import com.boekhoud.backendboekhoudapplicatie.dto.RoleDTO;
import com.boekhoud.backendboekhoudapplicatie.dto.UserDTO;
import com.boekhoud.backendboekhoudapplicatie.service.UserService;
import com.boekhoud.backendboekhoudapplicatie.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping("/add")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO, @RequestParam Long roleId) {
        UserDTO newUser = userService.createUser(userDTO, roleId);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO, @RequestParam(required = false) Long roleId) {
        UserDTO updatedUser = userService.updateUser(id, userDTO, roleId);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        LoginResponseDTO response = userService.login(loginDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<RoleDTO> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }
}
