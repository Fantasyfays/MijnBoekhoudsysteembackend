package com.boekhoud.backendboekhoudapplicatie.presentation;

import com.boekhoud.backendboekhoudapplicatie.dto.CreateInvoiceDTO;
import com.boekhoud.backendboekhoudapplicatie.dto.InvoiceDTO;
import com.boekhoud.backendboekhoudapplicatie.service.InvoiceService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping("/create")
    public ResponseEntity<InvoiceDTO> createInvoice(
            @RequestBody CreateInvoiceDTO createInvoiceDTO,
            @RequestParam("clientId") Long clientId) {
        InvoiceDTO newInvoice = invoiceService.createInvoice(createInvoiceDTO, clientId);
        return ResponseEntity.status(201).body(newInvoice);
    }

    @GetMapping("/generate-pdf/{invoiceId}")
    public ResponseEntity<InputStreamResource> downloadInvoicePdf(@PathVariable Long invoiceId) throws DocumentException, IOException {
        return invoiceService.generateInvoicePdf(invoiceId);
    }
}
