package com.boekhoud.backendboekhoudapplicatie.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class CreateUserDTO {

    @NotBlank(message = "Username is required.")
    private String username;

    @NotBlank(message = "Password is required.")
    private String password;
}
