package com.boekhoud.backendboekhoudapplicatie.DAL;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.Invoice;
import com.boekhoud.backendboekhoudapplicatie.service.dalinterface.IInvoiceDal;

import java.util.*;

public class MockInvoiceDAL implements IInvoiceDal {

    private Map<Long, Invoice> invoiceDatabase = new HashMap<>();
    private Long currentId = 1L;

    @Override
    public Invoice save(Invoice invoice) {
        if (invoice.getId() == null) {
            invoice.setId(currentId++);
        }
        invoiceDatabase.put(invoice.getId(), invoice);
        return invoice;
    }

    @Override
    public Optional<Invoice> findById(Long id) {
        return Optional.ofNullable(invoiceDatabase.get(id));
    }

    @Override
    public List<Invoice> findAll() {
        return new ArrayList<>(invoiceDatabase.values());
    }

    @Override
    public void deleteById(Long id) {
        invoiceDatabase.remove(id);
    }

    // Method to get the saved invoice, for testing purposes
    public Invoice getSavedInvoice() {
        if (invoiceDatabase.isEmpty()) {
            return null;
        }
        // Return the last saved invoice (latest added)
        return invoiceDatabase.get(currentId - 1);
    }
}
