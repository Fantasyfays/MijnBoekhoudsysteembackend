package com.boekhoud.backendboekhoudapplicatie.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDTO {

    private Long id;
    private String companyName;
    private String address;
    private String zipCode;
    private String city;
    private String country;
    private String email;
    private String phoneNumber;
    private String bank;
    private String swiftCode;
    private String bankAccountNumber;
    private String kvkNumber;
    private String taxNumber;
}
