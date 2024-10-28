package com.boekhoud.backendboekhoudapplicatie.service.interfaces;

import com.boekhoud.backendboekhoudapplicatie.presentation.dto.UserDTO;
import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {
    Optional<UserDTO> getUserById(Long id);
    List<UserDTO> getAllUsers();
    UserDTO createUser(UserDTO userDTO, Long roleId); // roleId toegevoegd
    UserDTO updateUser(Long id, UserDTO userDTO, Long roleId);
    void deleteUser(Long id);
}
