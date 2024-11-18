package com.boekhoud.backendboekhoudapplicatie.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateClientDTO {

    @NotBlank(message = "Company name cannot be blank.")
    private String companyName;

    @NotBlank(message = "Address cannot be blank.")
    private String address;

    @NotBlank(message = "Zip code cannot be blank.")
    private String zipCode;

    @NotBlank(message = "City cannot be blank.")
    private String city;

    @NotBlank(message = "Country cannot be blank.")
    private String country;

    @NotNull(message = "Email is required.")
    @Email(message = "Email should be valid.")
    private String email;

    private String phoneNumber;
    private String bank;
    private String swiftCode;
    private String bankAccountNumber;

    @NotNull(message = "KvK number is required.")
    @Size(min = 8, max = 20, message = "KvK number must be between 8 and 20 characters.")
    private String kvkNumber;

    @NotNull(message = "Tax number is required.")
    @Size(min = 10, max = 20, message = "Tax number must be between 10 and 20 characters.")
    private String taxNumber;

    @NotBlank(message = "Username cannot be blank.")
    private String username;

    @NotBlank(message = "Password cannot be blank.")
    private String password;
}
