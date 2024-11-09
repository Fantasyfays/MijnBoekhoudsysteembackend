package com.boekhoud.backendboekhoudapplicatie.dto;

import lombok.Data;

@Data
public class EditUserDTO {
    private String username;
    private String email;
    private String password;
}
