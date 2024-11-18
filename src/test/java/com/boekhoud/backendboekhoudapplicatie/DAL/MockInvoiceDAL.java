package com.boekhoud.backendboekhoudapplicatie.DAL;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.Client;
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

    @Override
    public List<Invoice> findAllByClient(Client client) {
        List<Invoice> invoicesByClient = new ArrayList<>();
        for (Invoice invoice : invoiceDatabase.values()) {
            if (invoice.getClient() != null && invoice.getClient().equals(client)) {
                invoicesByClient.add(invoice);
            }
        }
        return invoicesByClient;
    }

    public Invoice getSavedInvoice() {
        if (invoiceDatabase.isEmpty()) {
            return null;
        }
        return invoiceDatabase.get(currentId - 1);
    }
}
