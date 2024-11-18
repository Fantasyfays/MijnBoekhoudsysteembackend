package com.boekhoud.backendboekhoudapplicatie.service;

import com.boekhoud.backendboekhoudapplicatie.DAL.MockInvoiceDAL;
import com.boekhoud.backendboekhoudapplicatie.DAL.MockClientDAL;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.Client;
import com.boekhoud.backendboekhoudapplicatie.dto.CreateInvoiceDTO;
import com.boekhoud.backendboekhoudapplicatie.dto.InvoiceDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceServiceTest {

    private InvoiceService invoiceService;
    private MockInvoiceDAL mockInvoiceDAL;
    private MockClientDAL mockClientDAL;

    @BeforeEach
    void setUp() {
        mockInvoiceDAL = new MockInvoiceDAL();
        mockClientDAL = new MockClientDAL();

        InvoicePdfService invoicePdfService = new InvoicePdfService();

        invoiceService = new InvoiceService(mockInvoiceDAL, invoicePdfService, mockClientDAL);
    }

    @Test
    void testCreateInvoice_HappyFlow() {
        Client client = new Client();
        client.setId(1L);

        mockClientDAL.save(client);

        CreateInvoiceDTO createInvoiceDTO = new CreateInvoiceDTO();
        createInvoiceDTO.setInvoiceDate(LocalDate.parse("2024-01-01"));
        createInvoiceDTO.setDueDate(LocalDate.parse("2024-01-15"));
        createInvoiceDTO.setDescription("Test Invoice");
        createInvoiceDTO.setQuantity(5);
        createInvoiceDTO.setUnitPrice(20.0);

        InvoiceDTO invoiceDTO = invoiceService.createInvoice(createInvoiceDTO, client.getId());

        assertNotNull(invoiceDTO);
        assertEquals("Test Invoice", invoiceDTO.getDescription());
        assertEquals(5, invoiceDTO.getQuantity());
        assertEquals(20.0, invoiceDTO.getUnitPrice());
    }

    @Test
    void testCreateInvoice_UnhappyFlow() {
        CreateInvoiceDTO createInvoiceDTO = new CreateInvoiceDTO();
        createInvoiceDTO.setInvoiceDate(LocalDate.parse("2024-01-01"));
        createInvoiceDTO.setDueDate(LocalDate.parse("2024-01-15"));
        createInvoiceDTO.setDescription("Test Invoice");
        createInvoiceDTO.setQuantity(0);
        createInvoiceDTO.setUnitPrice(20.0);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            invoiceService.createInvoice(createInvoiceDTO, 1L);
        });

        assertEquals("Quantity must be at least 1.", thrown.getMessage());
    }

    @Test
    void testCreateInvoice_EdgeCase() {
        Client client = new Client();
        client.setId(1L);

        mockClientDAL.save(client);

        CreateInvoiceDTO createInvoiceDTO = new CreateInvoiceDTO();
        createInvoiceDTO.setInvoiceDate(LocalDate.parse("2024-01-01"));
        createInvoiceDTO.setDueDate(LocalDate.parse("2024-01-15"));
        createInvoiceDTO.setDescription("Edge Case Invoice");
        createInvoiceDTO.setQuantity(1);
        createInvoiceDTO.setUnitPrice(100.0);

        InvoiceDTO invoiceDTO = invoiceService.createInvoice(createInvoiceDTO, client.getId());

        assertNotNull(invoiceDTO);
        assertEquals("Edge Case Invoice", invoiceDTO.getDescription());
        assertEquals(1, invoiceDTO.getQuantity());
        assertEquals(100.0, invoiceDTO.getUnitPrice());
    }
}