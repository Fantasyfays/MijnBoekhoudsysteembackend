package com.boekhoud.backendboekhoudapplicatie.presentation;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.Invoice;
import com.boekhoud.backendboekhoudapplicatie.dto.InvoiceDTO;
import com.boekhoud.backendboekhoudapplicatie.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/create")
    public Invoice createInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        System.out.println("Invoice DTO: " + invoiceDTO);
        return invoiceService.createInvoice(invoiceDTO);
    }

}
