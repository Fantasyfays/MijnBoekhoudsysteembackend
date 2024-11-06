package com.boekhoud.backendboekhoudapplicatie.service;

import com.boekhoud.backendboekhoudapplicatie.dto.LoginDTO;
import com.boekhoud.backendboekhoudapplicatie.dto.LoginResponseDTO;
import com.boekhoud.backendboekhoudapplicatie.dto.UserDTO;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.User;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.Role;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.RoleRepository;
import com.boekhoud.backendboekhoudapplicatie.service.dalinterface.UserDal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserDal userDal;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserDal userDal, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userDal = userDal;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO createUser(UserDTO userDTO, Long roleId) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));
        user.setRole(role);

        User savedUser = userDal.save(user);
        return convertToDTO(savedUser);
    }

    public List<UserDTO> getAllUsers() {
        return userDal.findAll().stream().map(user -> {
            UserDTO dto = new UserDTO();
            dto.setId(user.getId());
            dto.setUsername(user.getUsername());
            dto.setRoleName(user.getRole().getName());
            return dto;
        }).collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        User user = userDal.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return convertToDTO(user);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO, Long roleId) {
        User user = userDal.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setUsername(userDTO.getUsername());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        if (roleId != null) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));
            user.setRole(role);
        }

        User updatedUser = userDal.save(user);
        return convertToDTO(updatedUser);
    }

    public void deleteUser(Long id) {
        User user = userDal.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        userDal.deleteById(user.getId());
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setRoleName(user.getRole() != null ? user.getRole().getName() : null); // Gebruik setRoleName
        return userDTO;
    }

    public LoginResponseDTO login(LoginDTO loginDTO) {
        Optional<User> userOpt = userDal.findByUsername(loginDTO.getUsername());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
                return new LoginResponseDTO("Login successful!");
            } else {
                return new LoginResponseDTO("Invalid password.");
            }
        } else {
            return new LoginResponseDTO("User not found.");
        }
    }
    public User findByUsername(String username) {
        return userDal.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

}
