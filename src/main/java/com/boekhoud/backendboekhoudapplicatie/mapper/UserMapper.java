package com.boekhoud.backendboekhoudapplicatie.mapper;

import com.boekhoud.backendboekhoudapplicatie.dto.*;
import com.boekhoud.backendboekhoudapplicatie.enums.RoleType;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(CreateUserDTO createUserDTO, RoleType role, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setUsername(createUserDTO.getUsername());
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        user.setRole(role);
        return user;
    }

    public UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getRole().name());
    }

    public void updateEntityFromDTO(UpdateUserDTO updateUserDTO, User user, String newRole) {
        user.setUsername(updateUserDTO.getUsername());
        if (newRole != null) {
            user.setRole(RoleType.valueOf(newRole.toUpperCase()));
        }
    }
}
