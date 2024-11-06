package com.boekhoud.backendboekhoudapplicatie.dal.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Integer quantity;
    private Double unitPrice;
    private Double total;

    public InvoiceItem(String description, int quantity, double unitPrice, double total) {
        this.description = description;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.total = total;
    }

}
