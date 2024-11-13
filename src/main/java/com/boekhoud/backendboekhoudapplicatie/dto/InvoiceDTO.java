package com.boekhoud.backendboekhoudapplicatie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {

    private Long id;
    private String invoiceNumber;
    private LocalDate invoiceDate;
    private LocalDate dueDate;
    private String description;
    private int quantity;
    private double unitPrice;
    private double subtotal;
    private double tax;
    private double totalAmount;
    private String bicSwiftNumber;
    private String paymentTerms;
    private String paymentCurrency;

    private String recipientName;
    private String recipientCompany;
    private String recipientAddress;
    private String recipientEmail;
}
