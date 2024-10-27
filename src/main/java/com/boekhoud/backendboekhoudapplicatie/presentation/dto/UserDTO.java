package com.boekhoud.backendboekhoudapplicatie.presentation.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;
    private String roleName;
}
