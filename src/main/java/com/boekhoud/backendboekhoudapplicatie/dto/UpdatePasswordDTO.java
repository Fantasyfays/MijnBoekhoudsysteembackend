package com.boekhoud.backendboekhoudapplicatie.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class UpdatePasswordDTO {

    @NotBlank(message = "Current password is required.")
    private String currentPassword;

    @NotBlank(message = "New password is required.")
    private String newPassword;
}
