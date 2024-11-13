package com.boekhoud.backendboekhoudapplicatie.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateInvoiceDTO {

    @NotBlank(message = "Invoice number is required.")
    private String invoiceNumber;

    @NotNull(message = "Invoice date is required.")
    private LocalDate invoiceDate;

    @NotNull(message = "Due date is required.")
    private LocalDate dueDate;

    @NotBlank(message = "Description is required.")
    private String description;

    @Min(value = 1, message = "Quantity must be at least 1.")
    private int quantity;

    @DecimalMin(value = "0.0", inclusive = false, message = "Unit price must be greater than 0.")
    private double unitPrice;

    @DecimalMin(value = "0.0", inclusive = false, message = "Tax rate must be greater than 0.")
    private double taxRate;

    @NotBlank(message = "BIC/SWIFT number is required.")
    private String bicSwiftNumber;

    @NotBlank(message = "Payment terms are required.")
    private String paymentTerms;

    @NotBlank(message = "Payment currency is required.")
    private String paymentCurrency;

    @NotBlank(message = "Recipient name is required.")
    private String recipientName;

    @NotBlank(message = "Recipient company is required.")
    private String recipientCompany;

    @NotBlank(message = "Recipient address is required.")
    private String recipientAddress;

    @NotBlank(message = "Recipient email is required.")
    private String recipientEmail;
}
