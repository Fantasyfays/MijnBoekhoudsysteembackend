package com.boekhoud.backendboekhoudapplicatie.dal.implementatie;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.Invoice;
import com.boekhoud.backendboekhoudapplicatie.dal.repository.InvoiceRepository;
import com.boekhoud.backendboekhoudapplicatie.service.dalinterface.IInvoiceDal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class InvoiceDal implements IInvoiceDal {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public Optional<Invoice> findById(Long id) {
        return invoiceRepository.findById(id);
    }

    @Override
    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        invoiceRepository.deleteById(id);
    }
}
