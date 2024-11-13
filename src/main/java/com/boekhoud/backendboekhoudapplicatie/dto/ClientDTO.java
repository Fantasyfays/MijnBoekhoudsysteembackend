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
public class ClientDTO {

    private Long id;
    private String name;
    private String address;
    private String kvkNumber;
    private String taxNumber;
    private String bankAccountNumber;
    private String email;
    private String phoneNumber;
    private CompanyDTO company;
    private AccountantDTO accountant;
    private List<InvoiceDTO> invoices;
}
