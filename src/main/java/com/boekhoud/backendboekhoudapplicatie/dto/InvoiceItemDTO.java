package com.boekhoud.backendboekhoudapplicatie.dto;

import lombok.Data;

@Data
public class InvoiceItemDTO {
    private String description;
    private Integer quantity;
    private Double unitPrice;
    private Double total;
}
