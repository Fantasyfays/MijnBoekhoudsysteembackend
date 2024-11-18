package com.boekhoud.backendboekhoudapplicatie.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CreateInvoiceDTO {

    @NotNull(message = "Invoice date is required.")
    private LocalDate invoiceDate;

    @NotNull(message = "Due date is required.")
    private LocalDate dueDate;

    @NotBlank(message = "Description cannot be blank.")
    private String description;

    @Min(value = 1, message = "Quantity must be at least 1.")
    private int quantity;

    @DecimalMin(value = "0.0", inclusive = false, message = "Unit price must be greater than 0.")
    private double unitPrice;

    @DecimalMin(value = "0.0", message = "Tax rate must be at least 0.")
    private double taxRate;

    @NotBlank(message = "BIC/Swift number cannot be blank.")
    private String bicSwiftNumber;

    @NotBlank(message = "Payment terms cannot be blank.")
    private String paymentTerms;

    @NotBlank(message = "Payment currency cannot be blank.")
    private String paymentCurrency;

    @NotBlank(message = "Recipient name cannot be blank.")
    private String recipientName;

    @NotBlank(message = "Recipient company cannot be blank.")
    private String recipientCompany;

    @NotBlank(message = "Recipient address cannot be blank.")
    private String recipientAddress;

    @Email(message = "Recipient email must be valid.")
    @NotBlank(message = "Recipient email cannot be blank.")
    private String recipientEmail;

    @NotNull(message = "Delivery date is required.")
    private LocalDate deliveryDate; // Optional afhankelijk van jouw eisen
}
