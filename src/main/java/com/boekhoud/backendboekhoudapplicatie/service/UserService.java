package com.boekhoud.backendboekhoudapplicatie.service;

import com.boekhoud.backendboekhoudapplicatie.dto.*;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.User;
import com.boekhoud.backendboekhoudapplicatie.enums.RoleType;
import com.boekhoud.backendboekhoudapplicatie.exception.InvalidCredentialsException;
import com.boekhoud.backendboekhoudapplicatie.exception.UserNotFoundException;
import com.boekhoud.backendboekhoudapplicatie.service.dalinterface.IUserDal;
import com.boekhoud.backendboekhoudapplicatie.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final IUserDal userDal;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public UserService(IUserDal userDal, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userDal = userDal;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
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

    public UserDTO createUser(CreateUserDTO createUserDTO, RoleType role) {
        User user = userMapper.toEntity(createUserDTO, role, passwordEncoder);
        User savedUser = userDal.save(user);
        return userMapper.toDTO(savedUser);
    }

    public UserDTO loginUser(UserLoginDTO loginDTO) {
        User user = userDal.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        return userMapper.toDTO(user);
    }

    public UserDTO updateUser(Long id, UpdateUserDTO updateUserDTO, String newRole) {
        User user = userDal.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        userMapper.updateEntityFromDTO(updateUserDTO, user, newRole);
        User updatedUser = userDal.save(user);
        return userMapper.toDTO(updatedUser);
    }

    public List<UserDTO> getAllUsers() {
        return userDal.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        User user = userDal.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return userMapper.toDTO(user);
    }

    public void deleteUser(Long id) {
        userDal.deleteById(id);
    }
}
