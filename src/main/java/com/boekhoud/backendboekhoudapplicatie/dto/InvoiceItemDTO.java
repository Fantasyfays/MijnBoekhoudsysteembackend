package com.boekhoud.backendboekhoudapplicatie.dto;

import lombok.Data;

@Data
public class InvoiceItemDTO {
    private String description;
    private Integer quantity;
    private Double unitPrice;
    private Double total;

    public InvoiceItemDTO(String description, int quantity, double unitPrice, double total) {
        this.description = description;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.total = total;
    }
}
