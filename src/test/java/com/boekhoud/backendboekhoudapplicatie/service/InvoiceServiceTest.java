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

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

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
    void testCreateInvoiceHappyFlow() {
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setInvoiceNumber("INV-001");
        invoiceDTO.setInvoiceDate(LocalDate.of(2024, 11, 5));
        invoiceDTO.setDueDate(LocalDate.of(2024, 12, 5));
        invoiceDTO.setCustomerName("John Doe");
        invoiceDTO.setCustomerAddress("123 Main St");
        invoiceDTO.setItems(List.of(new InvoiceItemDTO("Item 1", 1, 100.0, 100.0)));

        Invoice mockInvoice = new Invoice();
        mockInvoice.setInvoiceNumber("INV-001");
        mockInvoice.setSubtotal(100.0);
        mockInvoice.setTax(10.0);
        mockInvoice.setTotalAmount(110.0);

        when(invoiceRepository.save(any(Invoice.class))).thenReturn(mockInvoice);

        Invoice createdInvoice = invoiceService.createInvoice(invoiceDTO);

        assertEquals("INV-001", createdInvoice.getInvoiceNumber());
        assertEquals(100.0, createdInvoice.getSubtotal());
        assertEquals(10.0, createdInvoice.getTax());
        assertEquals(110.0, createdInvoice.getTotalAmount());
    }

    @Test
    void testCreateInvoiceWithoutInvoiceNumber() {
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setCustomerName("John Doe");
        invoiceDTO.setCustomerAddress("123 Main St");
        invoiceDTO.setInvoiceDate(LocalDate.of(2024, 11, 5));
        invoiceDTO.setItems(List.of(new InvoiceItemDTO("Item 1", 1, 100.0, 100.0)));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            invoiceService.createInvoice(invoiceDTO);
        });

        assertEquals("Factuurnummer is verplicht", exception.getMessage());
    }

    @Test
    void testCreateInvoiceWithoutItems() {
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setInvoiceNumber("INV-002");
        invoiceDTO.setCustomerName("John Doe");
        invoiceDTO.setCustomerAddress("123 Main St");
        invoiceDTO.setInvoiceDate(LocalDate.of(2024, 11, 5));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            invoiceService.createInvoice(invoiceDTO);
        });

        assertEquals("Factuur moet minimaal één item bevatten", exception.getMessage());
    }

    @Test
    void testCreateInvoiceWithNegativeUnitPriceOrQuantity() {
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setInvoiceNumber("INV-003");
        invoiceDTO.setCustomerName("John Doe");
        invoiceDTO.setCustomerAddress("123 Main St");
        invoiceDTO.setInvoiceDate(LocalDate.of(2024, 11, 5));

        InvoiceItemDTO negativePriceItem = new InvoiceItemDTO("Item met negatieve prijs", 1, -50.0, -50.0);
        InvoiceItemDTO zeroQuantityItem = new InvoiceItemDTO("Item met hoeveelheid nul", 0, 100.0, 0.0);

        invoiceDTO.setItems(List.of(negativePriceItem));

        Exception negativePriceException = assertThrows(IllegalArgumentException.class, () -> {
            invoiceService.createInvoice(invoiceDTO);
        });
        assertEquals("Eenheidsprijs mag niet negatief zijn", negativePriceException.getMessage());

        invoiceDTO.setItems(List.of(zeroQuantityItem));
        Exception zeroQuantityException = assertThrows(IllegalArgumentException.class, () -> {
            invoiceService.createInvoice(invoiceDTO);
        });
        assertEquals("Hoeveelheid moet groter zijn dan nul", zeroQuantityException.getMessage());
    }
}
