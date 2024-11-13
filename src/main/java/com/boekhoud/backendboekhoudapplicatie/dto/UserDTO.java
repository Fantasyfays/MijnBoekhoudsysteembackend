package com.boekhoud.backendboekhoudapplicatie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotBlank(message = "Username is required.")
    private String username;

    @NotBlank(message = "Role is required.")
    private String role;
}
