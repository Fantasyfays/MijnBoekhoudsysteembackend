package com.boekhoud.backendboekhoudapplicatie.presentation.dto;

import lombok.Data;

@Data
public class CompanyDTO {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
}
