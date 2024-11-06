package com.boekhoud.backendboekhoudapplicatie.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class InvoiceDTO {
    private String invoiceNumber;
    private LocalDate invoiceDate;
    private LocalDate dueDate;

    private String customerName;
    private String customerAddress;
    private String customerEmail;
    private String customerPhone;

    private String companyName;
    private String companyAddress;
    private String companyPhone;
    private String companyTaxId;

    private Double subtotal;
    private Double tax;
    private Double totalAmount;

    private String paymentTerms;
    private String notes;

    private List<InvoiceItemDTO> items;
}
