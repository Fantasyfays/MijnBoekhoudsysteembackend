package com.boekhoud.backendboekhoudapplicatie.service;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.Client;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.Invoice;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.ClientRepository;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.InvoiceRepository;
import com.boekhoud.backendboekhoudapplicatie.dto.CreateInvoiceDTO;
import com.boekhoud.backendboekhoudapplicatie.dto.InvoiceDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class InvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private InvoiceService invoiceService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateInvoice_HappyFlow() {
        Long clientId = 1L;
        CreateInvoiceDTO createInvoiceDTO = new CreateInvoiceDTO();
        createInvoiceDTO.setInvoiceNumber("INV-00001");
        createInvoiceDTO.setInvoiceDate(LocalDate.now());
        createInvoiceDTO.setDescription("Happy Flow Test Invoice");
        createInvoiceDTO.setQuantity(2);
        createInvoiceDTO.setUnitPrice(50.0);

        Client client = new Client();
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(invoiceRepository.save(any(Invoice.class))).thenAnswer(invocation -> invocation.getArgument(0));

        InvoiceDTO result = invoiceService.createInvoice(createInvoiceDTO, clientId);

        assertNotNull(result);
        assertEquals("Happy Flow Test Invoice", result.getDescription());
        assertEquals(2, result.getQuantity());
        assertEquals(50.0, result.getUnitPrice(), 0.01);
        assertNotNull(result.getInvoiceNumber());
    }

    @Test
    public void testCreateInvoice_UnhappyFlow_ClientNotFound() {
        Long clientId = 99L;
        CreateInvoiceDTO createInvoiceDTO = new CreateInvoiceDTO();
        createInvoiceDTO.setInvoiceNumber("INV-00002");

        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            invoiceService.createInvoice(createInvoiceDTO, clientId);
        });
        assertEquals("Client not found with ID: " + clientId, exception.getMessage());
    }

    @Test
    public void testCreateInvoice_EdgeCase_MinQuantityAndPrice() {
        Long clientId = 1L;
        CreateInvoiceDTO createInvoiceDTO = new CreateInvoiceDTO();
        createInvoiceDTO.setInvoiceNumber("INV-00003");
        createInvoiceDTO.setInvoiceDate(LocalDate.now());
        createInvoiceDTO.setDescription("Edge Case Test Invoice");
        createInvoiceDTO.setQuantity(1);
        createInvoiceDTO.setUnitPrice(0.01);

        Client client = new Client();
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(invoiceRepository.save(any(Invoice.class))).thenAnswer(invocation -> invocation.getArgument(0));

        InvoiceDTO result = invoiceService.createInvoice(createInvoiceDTO, clientId);

        assertNotNull(result);
        assertEquals(1, result.getQuantity());
        assertEquals(0.01, result.getUnitPrice(), 0.001);
    }
}
