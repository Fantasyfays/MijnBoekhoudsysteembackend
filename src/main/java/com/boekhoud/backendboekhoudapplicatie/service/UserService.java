package com.boekhoud.backendboekhoudapplicatie.service;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.User;
import com.boekhoud.backendboekhoudapplicatie.dto.*;
import com.boekhoud.backendboekhoudapplicatie.enums.RoleType;
import com.boekhoud.backendboekhoudapplicatie.exception.InvalidCredentialsException;
import com.boekhoud.backendboekhoudapplicatie.exception.UserNotFoundException;
import com.boekhoud.backendboekhoudapplicatie.service.dalinterface.IUserDal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final IUserDal userDal;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(IUserDal userDal, PasswordEncoder passwordEncoder) {
        this.userDal = userDal;
        this.passwordEncoder = passwordEncoder;
    }

    public RoleType convertRoleIdToRoleType(Long roleId) {
        return switch (roleId.intValue()) {
            case 1 -> RoleType.ADMIN;
            case 2 -> RoleType.CLIENT;
            case 3 -> RoleType.ACCOUNTANT;
            default -> throw new IllegalArgumentException("Invalid roleId: " + roleId);
        };
    }

    public UserDTO createUser(CreateUserDTO createUserDTO, Long roleId) {
        RoleType roleType = convertRoleIdToRoleType(roleId);  // Convert roleId to RoleType enum
        User user = new User();
        user.setUsername(createUserDTO.getUsername());
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        user.setRole(roleType);  // Set RoleType here, not a String
        User savedUser = userDal.save(user);
        return new UserDTO(savedUser.getId(), savedUser.getUsername(), savedUser.getRole());  // Pass RoleType to UserDTO
    }

    public UserDTO updateUser(Long id, UpdateUserDTO updateUserDTO, Long roleId) {
        User user = userDal.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        user.setUsername(updateUserDTO.getUsername());
        user.setEmail(updateUserDTO.getEmail());
        if (roleId != null) {
            user.setRole(convertRoleIdToRoleType(roleId));  // Update role as RoleType
        }
        User updatedUser = userDal.save(user);

        return new UserDTO(updatedUser.getId(), updatedUser.getUsername(), updatedUser.getRole());  // Pass RoleType to UserDTO
    }

    public UserDTO loginUser(UserLoginDTO loginDTO) {
        User user = userDal.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        return new UserDTO(user.getId(), user.getUsername(), user.getRole());  // Return RoleType instead of String
    }

    public void updatePassword(long userId, UpdatePasswordDTO updatePasswordDTO) {
        User user = userDal.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        if (!passwordEncoder.matches(updatePasswordDTO.getCurrentPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(updatePasswordDTO.getNewPassword()));
        userDal.save(user);
    }

    public List<UserDTO> getAllUsers() {
        return userDal.findAll().stream()
                .map(user -> new UserDTO(user.getId(), user.getUsername(), user.getRole()))  // Pass RoleType to UserDTO
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        User user = userDal.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return new UserDTO(user.getId(), user.getUsername(), user.getRole());  // Return RoleType to UserDTO
    }

    public void deleteUser(Long id) {
        userDal.deleteById(id);
    }
}
