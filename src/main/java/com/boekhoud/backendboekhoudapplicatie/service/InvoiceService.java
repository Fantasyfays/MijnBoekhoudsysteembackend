package com.boekhoud.backendboekhoudapplicatie.service;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.Invoice;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.Client;
import com.boekhoud.backendboekhoudapplicatie.service.dalinterface.IInvoiceDal;
import com.boekhoud.backendboekhoudapplicatie.service.dalinterface.IClientDal;
import com.boekhoud.backendboekhoudapplicatie.dto.CreateInvoiceDTO;
import com.boekhoud.backendboekhoudapplicatie.dto.InvoiceDTO;
import com.itextpdf.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceService.class);

    private final IInvoiceDal invoiceDal;
    private final InvoicePdfService invoicePdfService;
    private final IClientDal clientDal;

    @Autowired
    public InvoiceService(IInvoiceDal invoiceDal, InvoicePdfService invoicePdfService, IClientDal clientDal) {
        this.invoiceDal = invoiceDal;
        this.invoicePdfService = invoicePdfService;
        this.clientDal = clientDal;
    }

    private String generateUUIDInvoiceNumber() {
        return "INV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private LocalDate convertStringToLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }

    private InvoiceDTO convertToDTO(Invoice invoice) {
        return new InvoiceDTO(
                invoice.getId(),
                invoice.getInvoiceNumber(),
                invoice.getInvoiceDate(),
                invoice.getDueDate(),
                invoice.getDescription(),
                invoice.getQuantity(),
                invoice.getUnitPrice(),
                invoice.getSubtotal(),
                invoice.getTax(),
                invoice.getTotalAmount(),
                invoice.getBicSwiftNumber(),
                invoice.getBankAccountNumber(), // Map this field
                invoice.getPaymentTerms(),
                invoice.getPaymentCurrency(),
                invoice.getRecipientName(),
                invoice.getRecipientCompany(),
                invoice.getRecipientAddress(),
                invoice.getRecipientEmail(),
                invoice.getDeliveryDate()
        );
    }

    public InvoiceDTO createInvoice(CreateInvoiceDTO createInvoiceDTO, Long clientId) {
        if (createInvoiceDTO.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be at least 1.");
        }
        if (clientId == null) {
            throw new IllegalArgumentException("Client must be specified for the invoice.");
        }
        Client client = clientDal.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found with ID: " + clientId));

        LocalDate invoiceDate = convertStringToLocalDate(createInvoiceDTO.getInvoiceDate().toString());
        LocalDate dueDate = convertStringToLocalDate(createInvoiceDTO.getDueDate().toString());

        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(generateUUIDInvoiceNumber());
        invoice.setInvoiceDate(invoiceDate);
        invoice.setDueDate(dueDate);
        invoice.setDescription(createInvoiceDTO.getDescription());
        invoice.setQuantity(createInvoiceDTO.getQuantity());
        invoice.setUnitPrice(createInvoiceDTO.getUnitPrice());
        invoice.setClient(client);

        invoice.setBicSwiftNumber(createInvoiceDTO.getBicSwiftNumber());
        invoice.setPaymentTerms(createInvoiceDTO.getPaymentTerms());
        invoice.setPaymentCurrency(createInvoiceDTO.getPaymentCurrency());
        invoice.setRecipientName(createInvoiceDTO.getRecipientName());
        invoice.setRecipientCompany(createInvoiceDTO.getRecipientCompany());
        invoice.setRecipientAddress(createInvoiceDTO.getRecipientAddress());
        invoice.setRecipientEmail(createInvoiceDTO.getRecipientEmail());

        double subtotal = createInvoiceDTO.getQuantity() * createInvoiceDTO.getUnitPrice();
        double tax = (subtotal * createInvoiceDTO.getTaxRate()) / 100;
        double totalAmount = subtotal + tax;

        invoice.setSubtotal(subtotal);
        invoice.setTax(tax);
        invoice.setTotalAmount(totalAmount);

        Invoice savedInvoice = invoiceDal.save(invoice);

        return convertToDTO(savedInvoice);
    }

    public ResponseEntity<InputStreamResource> generateInvoicePdf(Long invoiceId) throws IOException {
        try {
            Invoice invoice = invoiceDal.findById(invoiceId)
                    .orElseThrow(() -> new RuntimeException("Invoice not found with ID: " + invoiceId));

            if (invoice.getClient() == null) {
                throw new IllegalStateException("Client is not set for this invoice.");
            }

            ByteArrayOutputStream pdfStream = invoicePdfService.generatePdf(invoice);
            InputStreamResource pdfResource = new InputStreamResource(new ByteArrayInputStream(pdfStream.toByteArray()));

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=invoice_" + invoice.getInvoiceNumber() + ".pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfResource);
        } catch (IOException | DocumentException e) {
            logger.error("Error generating PDF for invoice ID: {}", invoiceId, e);
            throw new IOException("Error generating PDF for invoice ID: " + invoiceId, e);
        } catch (IllegalStateException e) {
            logger.error("Error with invoice: {}", invoiceId, e);
            throw e;
        }
    }

    public InvoiceDTO getInvoiceById(Long id) {
        Invoice invoice = invoiceDal.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with ID: " + id));

        return convertToDTO(invoice);
    }

    public InvoiceDTO updateInvoice(Long id, CreateInvoiceDTO createInvoiceDTO) {
        Invoice invoice = invoiceDal.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with ID: " + id));

        // Factuurnummer wordt niet overschreven, omdat het al eerder is gegenereerd
        invoice.setInvoiceDate(convertStringToLocalDate(createInvoiceDTO.getInvoiceDate().toString()));
        invoice.setDueDate(convertStringToLocalDate(createInvoiceDTO.getDueDate().toString()));
        invoice.setDescription(createInvoiceDTO.getDescription());
        invoice.setQuantity(createInvoiceDTO.getQuantity());
        invoice.setUnitPrice(createInvoiceDTO.getUnitPrice());
        invoice.setBicSwiftNumber(createInvoiceDTO.getBicSwiftNumber());
        invoice.setPaymentTerms(createInvoiceDTO.getPaymentTerms());
        invoice.setPaymentCurrency(createInvoiceDTO.getPaymentCurrency());
        invoice.setRecipientName(createInvoiceDTO.getRecipientName());
        invoice.setRecipientCompany(createInvoiceDTO.getRecipientCompany());
        invoice.setRecipientAddress(createInvoiceDTO.getRecipientAddress());
        invoice.setRecipientEmail(createInvoiceDTO.getRecipientEmail());

        double subtotal = createInvoiceDTO.getQuantity() * createInvoiceDTO.getUnitPrice();
        double tax = (subtotal * createInvoiceDTO.getTaxRate()) / 100;
        double totalAmount = subtotal + tax;

        invoice.setSubtotal(subtotal);
        invoice.setTax(tax);
        invoice.setTotalAmount(totalAmount);

        Invoice updatedInvoice = invoiceDal.save(invoice);

        return convertToDTO(updatedInvoice);
    }


    public void deleteInvoice(Long id) {
        invoiceDal.deleteById(id);
    }
    public List<InvoiceDTO> getInvoicesByClient(Long clientId) {
        Client client = clientDal.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found with ID: " + clientId));

        return invoiceDal.findAllByClient(client).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}