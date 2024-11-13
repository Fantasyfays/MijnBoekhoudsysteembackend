package com.boekhoud.backendboekhoudapplicatie.dto;

import com.boekhoud.backendboekhoudapplicatie.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotBlank(message = "Username is required.")
    private String username;

    @NotBlank(message = "Role is required.")
    private RoleType role;  // Make sure to use RoleType here, not String
}
