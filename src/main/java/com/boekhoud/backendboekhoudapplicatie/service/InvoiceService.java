package com.boekhoud.backendboekhoudapplicatie.service;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.Invoice;
import com.boekhoud.backendboekhoudapplicatie.dal.entity.InvoiceItem;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.InvoiceRepository;
import com.boekhoud.backendboekhoudapplicatie.dto.InvoiceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public Invoice createInvoice(InvoiceDTO invoiceDTO) {
        if (invoiceDTO.getInvoiceNumber() == null || invoiceDTO.getInvoiceNumber().isEmpty()) {
            throw new IllegalArgumentException("Factuurnummer is verplicht");
        }
        if (invoiceDTO.getCustomerName() == null || invoiceDTO.getCustomerName().isEmpty()) {
            throw new IllegalArgumentException("Klantnaam is verplicht");
        }
        if (invoiceDTO.getCustomerAddress() == null || invoiceDTO.getCustomerAddress().isEmpty()) {
            throw new IllegalArgumentException("Klantadres is verplicht");
        }
        if (invoiceDTO.getInvoiceDate() == null) {
            throw new IllegalArgumentException("Factuurdatum is verplicht");
        }
        if (invoiceDTO.getItems() == null || invoiceDTO.getItems().isEmpty()) {
            throw new IllegalArgumentException("Factuur moet minimaal één item bevatten");
        }

        List<InvoiceItem> items = invoiceDTO.getItems().stream().map(itemDTO -> {
            if (itemDTO.getQuantity() <= 0) {
                throw new IllegalArgumentException("Hoeveelheid moet groter zijn dan nul");
            }
            if (itemDTO.getUnitPrice() < 0) {
                throw new IllegalArgumentException("Eenheidsprijs mag niet negatief zijn");
            }

            InvoiceItem item = new InvoiceItem();
            item.setDescription(itemDTO.getDescription());
            item.setQuantity(itemDTO.getQuantity());
            item.setUnitPrice(itemDTO.getUnitPrice());
            item.setTotal(itemDTO.getQuantity() * itemDTO.getUnitPrice());
            return item;
        }).collect(Collectors.toList());

        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(invoiceDTO.getInvoiceNumber());
        invoice.setInvoiceDate(invoiceDTO.getInvoiceDate());
        invoice.setDueDate(invoiceDTO.getDueDate());
        invoice.setCustomerName(invoiceDTO.getCustomerName());
        invoice.setCustomerAddress(invoiceDTO.getCustomerAddress());
        invoice.setCustomerEmail(invoiceDTO.getCustomerEmail());
        invoice.setCustomerPhone(invoiceDTO.getCustomerPhone());
        invoice.setCompanyName(invoiceDTO.getCompanyName());
        invoice.setCompanyAddress(invoiceDTO.getCompanyAddress());
        invoice.setCompanyPhone(invoiceDTO.getCompanyPhone());
        invoice.setCompanyTaxId(invoiceDTO.getCompanyTaxId());
        invoice.setSubtotal(invoiceDTO.getSubtotal());
        invoice.setTax(invoiceDTO.getTax());
        invoice.setTotalAmount(invoiceDTO.getTotalAmount());
        invoice.setPaymentTerms(invoiceDTO.getPaymentTerms());
        invoice.setNotes(invoiceDTO.getNotes());
        invoice.setItems(items);

        return invoiceRepository.save(invoice);
    }
}
