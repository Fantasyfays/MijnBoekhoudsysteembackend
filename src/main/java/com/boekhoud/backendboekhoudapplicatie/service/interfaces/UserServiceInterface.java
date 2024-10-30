package com.boekhoud.backendboekhoudapplicatie.service.interfaces;

import com.boekhoud.backendboekhoudapplicatie.presentation.dto.RoleDTO;
import com.boekhoud.backendboekhoudapplicatie.presentation.dto.UserDTO;
import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {
    UserDTO createUser(UserDTO userDTO, Long roleId);
    List<UserDTO> getAllUsers();
    Optional<UserDTO> getUserById(Long id);
    UserDTO updateUserById(Long id, UserDTO userDto, Long roleId); // Updated to include roleId
    void deleteUserById(Long id);
    List<RoleDTO> getAllRoles();
    boolean isRoleValid(Long roleId);
}
