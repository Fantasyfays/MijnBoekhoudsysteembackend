package com.boekhoud.backendboekhoudapplicatie.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateAccountantDTO {

    @NotBlank(message = "First name is required.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    private String lastName;

    @Email(message = "Invalid email format.")
    private String email;

    @Pattern(regexp = "\\+?[0-9]{10,15}", message = "Invalid phone number format.")
    private String phoneNumber;

    private String specialization;
    private String officeLocation;
    private Boolean activeStatus;
    private LocalDate dateOfHire;
}
