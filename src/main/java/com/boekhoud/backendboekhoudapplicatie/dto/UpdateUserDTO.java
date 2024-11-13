package com.boekhoud.backendboekhoudapplicatie.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
public class UpdateUserDTO {

    @NotBlank(message = "Username is required.")
    private String username;

    @Email(message = "Invalid email format.")
    private String email;
}
