package com.boekhoud.backendboekhoudapplicatie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCompanyDTO {

    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Address is required.")
    private String address;

    @NotBlank(message = "Email is required.")
    private String email;
}
