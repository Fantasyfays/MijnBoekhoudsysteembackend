package com.boekhoud.backendboekhoudapplicatie.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateClientDTO {

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

    @Email(message = "Email should be valid.")
    private String email;

    private String phoneNumber;
    private String bank;
    private String swiftCode;
    private String bankAccountNumber;
}
