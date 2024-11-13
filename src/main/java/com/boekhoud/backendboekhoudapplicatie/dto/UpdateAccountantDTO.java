package com.boekhoud.backendboekhoudapplicatie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAccountantDTO {

    @Pattern(regexp = "\\+?[0-9]{10,15}", message = "Invalid phone number format.")
    private String phoneNumber;

    @Email(message = "Invalid email format.")
    private String email;

    private String specialization;
    private String officeLocation;
    private Boolean activeStatus;
}
