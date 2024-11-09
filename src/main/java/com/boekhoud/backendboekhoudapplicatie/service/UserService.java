package com.boekhoud.backendboekhoudapplicatie.service;

import com.boekhoud.backendboekhoudapplicatie.dto.CreateUserDTO;
import com.boekhoud.backendboekhoudapplicatie.dto.EditUserDTO;
import com.boekhoud.backendboekhoudapplicatie.dto.UserLoginDTO;
import com.boekhoud.backendboekhoudapplicatie.dto.UserDTO;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.User;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.RoleType;
import com.boekhoud.backendboekhoudapplicatie.service.dalinterface.IUserDal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final IUserDal IUserDal;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(IUserDal IUserDal, PasswordEncoder passwordEncoder) {
        this.IUserDal = IUserDal;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO createUser(CreateUserDTO createUserDTO, RoleType role) {
        User user = new User();
        user.setUsername(createUserDTO.getUsername());
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        user.setRole(role);

        User savedUser = IUserDal.save(user);
        return convertToDTO(savedUser);
    }

    public UserDTO loginUser(UserLoginDTO loginDTO) {
        User user = IUserDal.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        return convertToDTO(user);
    }

    public UserDTO updateUser(Long id, EditUserDTO editUserDTO, String newRole) {
        User user = IUserDal.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setUsername(editUserDTO.getUsername());

        if (newRole != null) {
            user.setRole(RoleType.valueOf(newRole.toUpperCase()));
        }

        User updatedUser = IUserDal.save(user);
        return convertToDTO(updatedUser);
    }

    public List<UserDTO> getAllUsers() {
        return IUserDal.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        User user = IUserDal.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return convertToDTO(user);
    }

    public void deleteUser(Long id) {
        IUserDal.deleteById(id);
    }

    private UserDTO convertToDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getRole().name());
    }
}
