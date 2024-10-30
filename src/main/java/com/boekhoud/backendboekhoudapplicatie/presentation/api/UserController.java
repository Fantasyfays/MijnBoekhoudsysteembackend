package com.boekhoud.backendboekhoudapplicatie.presentation.api;

import com.boekhoud.backendboekhoudapplicatie.presentation.dto.RoleDTO;
import com.boekhoud.backendboekhoudapplicatie.presentation.dto.UserDTO;
import com.boekhoud.backendboekhoudapplicatie.service.interfaces.UserServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServiceInterface userService;

    public UserController(UserServiceInterface userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO, @RequestParam(required = false) Long roleId) {
        if (roleId == null) {
            return ResponseEntity.badRequest().build(); // Return empty body with 400 status
        }
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
        Optional<UserDTO> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable Long id, @RequestBody UserDTO userDto, @RequestParam(required = false) Long roleId) {
        // Check if the roleId is provided but does not correspond to a valid role in your service
        if (roleId != null && !userService.isRoleValid(roleId)) {
            return ResponseEntity.badRequest().body("Invalid Role ID provided");
        }

        UserDTO updatedUser = userService.updateUserById(id, userDto, roleId);
        return ResponseEntity.ok(updatedUser);
    }
    @GetMapping("/roles")
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<RoleDTO> roles = userService.getAllRoles();
        return ResponseEntity.ok(roles);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
