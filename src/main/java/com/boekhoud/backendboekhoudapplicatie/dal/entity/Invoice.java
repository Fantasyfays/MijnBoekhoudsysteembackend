package com.boekhoud.backendboekhoudapplicatie.dal.entity;

import com.boekhoud.backendboekhoudapplicatie.enums.InvoiceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    // New status field
    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;
}
