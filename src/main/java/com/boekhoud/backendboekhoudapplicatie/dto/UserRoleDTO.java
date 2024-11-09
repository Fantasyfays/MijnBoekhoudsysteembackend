package com.boekhoud.backendboekhoudapplicatie.dto;

import lombok.Data;

@Data
public class UserRoleDTO {
    private Long userId;
    private String role;
}
