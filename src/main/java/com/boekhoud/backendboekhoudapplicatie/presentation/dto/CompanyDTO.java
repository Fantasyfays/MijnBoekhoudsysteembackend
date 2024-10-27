package com.boekhoud.backendboekhoudapplicatie.presentation.dto;

import lombok.Data;

@Data
public class CompanyDTO {
    private Long id;
    private String name;
    private String address;
    private String city;
    private String zip;
    private String country;
    private String phone;
    private String email;
}
