package com.boekhoud.backendboekhoudapplicatie.service;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.Invoice;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.InvoiceItem;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.InvoiceRepository;
import com.boekhoud.backendboekhoudapplicatie.dto.InvoiceDTO;
import com.boekhoud.backendboekhoudapplicatie.dto.InvoiceItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @InjectMocks
    private InvoiceService invoiceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateInvoice() {
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setInvoiceNumber("INV-001");
        invoiceDTO.setInvoiceDate(LocalDate.of(2024, 11, 5));
        invoiceDTO.setDueDate(LocalDate.of(2024, 12, 5));
        invoiceDTO.setCustomerName("John Doe");
        invoiceDTO.setCustomerAddress("123 Elm Street");
        invoiceDTO.setCustomerEmail("johndoe@example.com");
        invoiceDTO.setCustomerPhone("555-1234");
        invoiceDTO.setCompanyName("Tech Solutions Inc.");
        invoiceDTO.setCompanyAddress("456 Oak Avenue");
        invoiceDTO.setCompanyPhone("555-5678");
        invoiceDTO.setCompanyTaxId("US123456789");
        invoiceDTO.setPaymentTerms("Net 30");
        invoiceDTO.setNotes("Thank you for your business.");

        InvoiceItemDTO item1 = new InvoiceItemDTO();
        item1.setDescription("Website Design Services");
        item1.setQuantity(1);
        item1.setUnitPrice(300.00);
        item1.setTotal(300.00);

        InvoiceItemDTO item2 = new InvoiceItemDTO();
        item2.setDescription("Hosting Service (6 months)");
        item2.setQuantity(1);
        item2.setUnitPrice(100.00);
        item2.setTotal(100.00);

        invoiceDTO.setItems(List.of(item1, item2));

        double expectedSubtotal = 400.00;
        double expectedTax = 40.00;
        double expectedTotalAmount = 440.00;

        Invoice mockInvoice = new Invoice();
        mockInvoice.setInvoiceNumber("INV-001");
        mockInvoice.setInvoiceDate(LocalDate.of(2024, 11, 5));
        mockInvoice.setDueDate(LocalDate.of(2024, 12, 5));
        mockInvoice.setCustomerName("John Doe");
        mockInvoice.setCustomerAddress("123 Elm Street");
        mockInvoice.setCustomerEmail("johndoe@example.com");
        mockInvoice.setCustomerPhone("555-1234");
        mockInvoice.setCompanyName("Tech Solutions Inc.");
        mockInvoice.setCompanyAddress("456 Oak Avenue");
        mockInvoice.setCompanyPhone("555-5678");
        mockInvoice.setCompanyTaxId("US123456789");
        mockInvoice.setSubtotal(expectedSubtotal);
        mockInvoice.setTax(expectedTax);
        mockInvoice.setTotalAmount(expectedTotalAmount);
        mockInvoice.setPaymentTerms("Net 30");
        mockInvoice.setNotes("Thank you for your business.");
        mockInvoice.setItems(List.of(
                new InvoiceItem("Website Design Services", 1, 300.00, 300.00),
                new InvoiceItem("Hosting Service (6 months)", 1, 100.00, 100.00)
        ));

        when(invoiceRepository.save(any(Invoice.class))).thenReturn(mockInvoice);

        Invoice createdInvoice = invoiceService.createInvoice(invoiceDTO);

        assertEquals("INV-001", createdInvoice.getInvoiceNumber());
        assertEquals(expectedSubtotal, createdInvoice.getSubtotal());
        assertEquals(expectedTax, createdInvoice.getTax());
        assertEquals(expectedTotalAmount, createdInvoice.getTotalAmount());
        assertEquals(2, createdInvoice.getItems().size());
    }
    @Test
    void testCreateInvoiceWithoutInvoiceNumber() {
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setInvoiceDate(LocalDate.of(2024, 11, 5));
        invoiceDTO.setCustomerName("John Doe");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            invoiceService.createInvoice(invoiceDTO);
        });

        assertEquals("Invoice number is required", exception.getMessage());
    }
    @Test
    void testCreateInvoiceWithoutCustomerName() {
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setInvoiceNumber("INV-002");
        invoiceDTO.setInvoiceDate(LocalDate.of(2024, 11, 5));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            invoiceService.createInvoice(invoiceDTO);
        });

        assertEquals("Customer name is required", exception.getMessage());
    }
    @Test
    void testCreateInvoiceWithNegativeUnitPrice() {
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setInvoiceNumber("INV-003");
        invoiceDTO.setCustomerName("John Doe");
        invoiceDTO.setInvoiceDate(LocalDate.of(2024, 11, 5));

        InvoiceItemDTO item = new InvoiceItemDTO();
        item.setDescription("Service");
        item.setQuantity(1);
        item.setUnitPrice(-100.00);

        invoiceDTO.setItems(List.of(item));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            invoiceService.createInvoice(invoiceDTO);
        });

        assertEquals("Unit price cannot be negative", exception.getMessage());
    }
    @Test
    void testCreateInvoiceWithZeroQuantity() {
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setInvoiceNumber("INV-004");
        invoiceDTO.setCustomerName("John Doe");
        invoiceDTO.setInvoiceDate(LocalDate.of(2024, 11, 5));

        InvoiceItemDTO item = new InvoiceItemDTO();
        item.setDescription("Service");
        item.setQuantity(0);
        item.setUnitPrice(50.00);

        invoiceDTO.setItems(List.of(item));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            invoiceService.createInvoice(invoiceDTO);
        });

        assertEquals("Quantity must be greater than zero", exception.getMessage());
    }
    @Test
    void testCreateInvoiceWithoutItems() {
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setInvoiceNumber("INV-005");
        invoiceDTO.setCustomerName("John Doe");
        invoiceDTO.setInvoiceDate(LocalDate.of(2024, 11, 5));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            invoiceService.createInvoice(invoiceDTO);
        });

        assertEquals("Invoice must contain at least one item", exception.getMessage());
    }


}
