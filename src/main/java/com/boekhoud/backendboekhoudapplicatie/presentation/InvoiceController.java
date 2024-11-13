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

    // Pas de URL aan om het companyId te bevatten
    @PostMapping("/create/{companyId}")
    public ResponseEntity<InvoiceDTO> createInvoice(
            @RequestBody CreateInvoiceDTO createInvoiceDTO,
            @PathVariable Long companyId) {  // Hier het companyId uit de URL halen
        InvoiceDTO newInvoice = invoiceService.createInvoice(createInvoiceDTO, companyId);
        return ResponseEntity.status(201).body(newInvoice);
    }

    @GetMapping("/{invoiceId}")
    public ResponseEntity<InvoiceDTO> getInvoiceById(@PathVariable Long invoiceId) {
        InvoiceDTO invoice = invoiceService.getInvoiceById(invoiceId);
        return ResponseEntity.ok(invoice);
    }

    @GetMapping("/generate-pdf/{invoiceId}")
    public ResponseEntity<InputStreamResource> downloadInvoicePdf(@PathVariable Long invoiceId) throws IOException, DocumentException {
        return invoiceService.generateInvoicePdf(invoiceId);
    }

    @PutMapping("/{invoiceId}")
    public ResponseEntity<InvoiceDTO> updateInvoice(
            @PathVariable Long invoiceId,
            @RequestBody CreateInvoiceDTO createInvoiceDTO) {
        InvoiceDTO updatedInvoice = invoiceService.updateInvoice(invoiceId, createInvoiceDTO);
        return ResponseEntity.ok(updatedInvoice);
    }

    @DeleteMapping("/{invoiceId}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long invoiceId) {
        invoiceService.deleteInvoice(invoiceId);
        return ResponseEntity.noContent().build();
    }
}
