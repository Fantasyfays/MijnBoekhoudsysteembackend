package com.boekhoud.backendboekhoudapplicatie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDTO {

    private Long id;
    private String name;
    private String address;
    private String email;
    private List<AccountantDTO> accountants;
}
