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
public class UpdateInvoiceDTO {

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

    @DecimalMin(value = "0.0", inclusive = false, message = "Subtotal must be greater than 0.")
    private double subtotal;

    @DecimalMin(value = "0.0", inclusive = false, message = "Tax must be greater than 0.")
    private double tax;

    @DecimalMin(value = "0.0", inclusive = false, message = "Total amount must be greater than 0.")
    private double totalAmount;

    @NotBlank(message = "BIC/SWIFT number is required.")
    private String bicSwiftNumber;

    @NotBlank(message = "Payment terms are required.")
    private String paymentTerms;

    @NotBlank(message = "Payment currency is required.")
    private String paymentCurrency;
}
