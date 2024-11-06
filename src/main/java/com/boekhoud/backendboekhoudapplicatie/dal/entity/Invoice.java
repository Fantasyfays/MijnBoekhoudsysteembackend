package com.boekhoud.backendboekhoudapplicatie.dal.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    private List<InvoiceItem> items;
}
