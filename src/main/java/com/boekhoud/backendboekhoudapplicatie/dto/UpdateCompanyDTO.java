package com.boekhoud.backendboekhoudapplicatie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCompanyDTO {

    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Address is required.")
    private String address;

    @Email(message = "Invalid email format.")
    private String email;
}
