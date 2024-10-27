package com.boekhoud.backendboekhoudapplicatie.presentation.dto;

import lombok.Data;

@Data
public class AccountantDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String address;      // New field
    private String employeeId;    // New field
    private String phoneNumber;   // New field
    private Long companyId;
}
