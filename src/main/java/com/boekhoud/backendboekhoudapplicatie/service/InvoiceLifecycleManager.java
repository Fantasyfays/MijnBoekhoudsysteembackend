package com.boekhoud.backendboekhoudapplicatie.service;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.Invoice;
import com.boekhoud.backendboekhoudapplicatie.enums.InvoiceStatus;
import org.springframework.stereotype.Component;

@Component
public class InvoiceLifecycleManager {

    public void markAsDraft(Invoice invoice) {
        validateTransition(invoice, InvoiceStatus.DRAFT);
        invoice.setStatus(InvoiceStatus.DRAFT);
    }

    public void issueInvoice(Invoice invoice) {
        validateTransition(invoice, InvoiceStatus.ISSUED);
        invoice.setStatus(InvoiceStatus.ISSUED);
    }

    public void markAsPaid(Invoice invoice) {
        validateTransition(invoice, InvoiceStatus.PAID);
        invoice.setStatus(InvoiceStatus.PAID);
    }

    private void validateTransition(Invoice invoice, InvoiceStatus targetStatus) {
        if (invoice.getStatus() == InvoiceStatus.PAID && targetStatus != InvoiceStatus.PAID) {
            throw new IllegalStateException("Cannot modify a paid invoice.");
        }
    }
}
