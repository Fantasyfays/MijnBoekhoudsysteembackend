package com.boekhoud.backendboekhoudapplicatie.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateClientDTO {

    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Address is required.")
    private String address;

    @NotBlank(message = "KvK Number is required.")
    private String kvkNumber;

    @NotBlank(message = "Tax number is required.")
    private String taxNumber;

    @NotBlank(message = "Bank account number is required.")
    private String bankAccountNumber;

    @Email(message = "Invalid email format.")
    private String email;

    @Pattern(regexp = "\\+?[0-9]{10,15}", message = "Invalid phone number format.")
    private String phoneNumber;
}
