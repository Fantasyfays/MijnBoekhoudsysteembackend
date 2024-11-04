package com.boekhoud.backendboekhoudapplicatie.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AccountantDTO {
    private Long id;
    private String username;
    private String password;
    private Long companyId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String specialization;
    private String officeLocation;
    private LocalDate dateOfHire;
    private Boolean activeStatus;
}
