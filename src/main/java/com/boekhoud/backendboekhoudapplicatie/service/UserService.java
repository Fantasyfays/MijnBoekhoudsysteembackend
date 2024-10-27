package com.boekhoud.backendboekhoudapplicatie.service;

import com.boekhoud.backendboekhoudapplicatie.dal.repository.RoleRepository;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.UserRepository;
import com.boekhoud.backendboekhoudapplicatie.model.Role;
import com.boekhoud.backendboekhoudapplicatie.model.User;
import com.boekhoud.backendboekhoudapplicatie.presentation.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Voeg een nieuwe gebruiker toe en converteer naar DTO
    public UserDTO addUser(UserDTO userDTO, Long roleId) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);

        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    // Converteer een User naar UserDTO
    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRoleName(user.getRole().getName());  // Alleen rolnaam meesturen
        return dto;
    }

    // Haal alle gebruikers op en converteer naar DTO
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDTO);
    }

    public UserDTO updateUser(Long id, UserDTO userDetails, Long roleId) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(userDetails.getUsername());
        user.setPassword(passwordEncoder.encode(userDetails.getPassword()));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);

        User updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
