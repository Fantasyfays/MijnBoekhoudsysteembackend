package com.boekhoud.backendboekhoudapplicatie.presentation.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String roleName;
    private long roleId;
}
