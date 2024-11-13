package com.boekhoud.backendboekhoudapplicatie.service;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.Client;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.Invoice;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.ClientRepository;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.InvoiceRepository;
import com.boekhoud.backendboekhoudapplicatie.dto.CreateInvoiceDTO;
import com.boekhoud.backendboekhoudapplicatie.dto.InvoiceDTO;
import com.itextpdf.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class InvoiceService {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceService.class);

    private final InvoiceRepository invoiceRepository;
    private final ClientRepository clientRepository;
    private final InvoicePdfService invoicePdfService;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, ClientRepository clientRepository, InvoicePdfService invoicePdfService) {
        this.invoiceRepository = invoiceRepository;
        this.clientRepository = clientRepository;
        this.invoicePdfService = invoicePdfService;
    }

    private String generateUUIDInvoiceNumber() {
        return "INV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public InvoiceDTO createInvoice(CreateInvoiceDTO createInvoiceDTO, Long clientId) {
        logger.info("Creating invoice for client with ID: {}", clientId);

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> {
                    logger.error("Client not found with ID: {}", clientId);
                    return new IllegalArgumentException("Client not found with ID: " + clientId);
                });

        Invoice invoice = buildInvoice(createInvoiceDTO, client);
        invoice.setInvoiceNumber(generateUUIDInvoiceNumber());

        logger.info("Generated invoice number: {}", invoice.getInvoiceNumber());

        Invoice savedInvoice = invoiceRepository.save(invoice);
        logger.info("Invoice with ID {} created successfully", savedInvoice.getId());

        return convertToDTO(savedInvoice);
    }

    public ResponseEntity<InputStreamResource> generateInvoicePdf(Long invoiceId) throws DocumentException, IOException {
        logger.info("Generating PDF for invoice ID: {}", invoiceId);

        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> {
                    logger.error("Invoice not found with ID: {}", invoiceId);
                    return new IllegalArgumentException("Invoice not found with ID: " + invoiceId);
                });

        ByteArrayOutputStream pdfStream = invoicePdfService.generatePdf(invoice);

        InputStreamResource pdfResource = new InputStreamResource(new ByteArrayInputStream(pdfStream.toByteArray()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=invoice_" + invoice.getInvoiceNumber() + ".pdf");

        logger.info("PDF generated for invoice ID: {}", invoiceId);

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfResource);
    }
    private Invoice buildInvoice(CreateInvoiceDTO createInvoiceDTO, Client client) {
        double subtotal = calculateSubtotal(createInvoiceDTO.getQuantity(), createInvoiceDTO.getUnitPrice());
        double tax = calculateTax(subtotal, createInvoiceDTO.getTaxRate());
        double totalAmount = subtotal + tax;

        // Log de waarden van de recipient velden
        logger.info("Recipient Name: {}", createInvoiceDTO.getRecipientName());
        logger.info("Recipient Company: {}", createInvoiceDTO.getRecipientCompany());
        logger.info("Recipient Address: {}", createInvoiceDTO.getRecipientAddress());
        logger.info("Recipient Email: {}", createInvoiceDTO.getRecipientEmail());

        return Invoice.builder()
                .invoiceNumber(createInvoiceDTO.getInvoiceNumber())
                .invoiceDate(createInvoiceDTO.getInvoiceDate())
                .dueDate(createInvoiceDTO.getDueDate())
                .description(createInvoiceDTO.getDescription())
                .quantity(createInvoiceDTO.getQuantity())
                .unitPrice(createInvoiceDTO.getUnitPrice())
                .subtotal(subtotal)
                .tax(tax)
                .totalAmount(totalAmount)
                .bicSwiftNumber(createInvoiceDTO.getBicSwiftNumber())
                .paymentTerms(createInvoiceDTO.getPaymentTerms())
                .paymentCurrency(createInvoiceDTO.getPaymentCurrency())
                .recipientName(createInvoiceDTO.getRecipientName())
                .recipientCompany(createInvoiceDTO.getRecipientCompany())
                .recipientAddress(createInvoiceDTO.getRecipientAddress())
                .recipientEmail(createInvoiceDTO.getRecipientEmail())
                .client(client)
                .build();
    }


    private double calculateSubtotal(int quantity, double unitPrice) {
        return quantity * unitPrice;
    }

    private double calculateTax(double subtotal, double taxRate) {
        return subtotal * (taxRate / 100.0);
    }

    InvoiceDTO convertToDTO(Invoice invoice) {
        return InvoiceDTO.builder()
                .id(invoice.getId())
                .invoiceNumber(invoice.getInvoiceNumber())
                .invoiceDate(invoice.getInvoiceDate())
                .dueDate(invoice.getDueDate())
                .description(invoice.getDescription())
                .quantity(invoice.getQuantity())
                .unitPrice(invoice.getUnitPrice())
                .subtotal(invoice.getSubtotal())
                .tax(invoice.getTax())
                .totalAmount(invoice.getTotalAmount())
                .bicSwiftNumber(invoice.getBicSwiftNumber())
                .paymentTerms(invoice.getPaymentTerms())
                .paymentCurrency(invoice.getPaymentCurrency())
                .recipientName(invoice.getRecipientName())
                .recipientCompany(invoice.getRecipientCompany())
                .recipientAddress(invoice.getRecipientAddress())
                .recipientEmail(invoice.getRecipientEmail())
                .build();
    }
}
