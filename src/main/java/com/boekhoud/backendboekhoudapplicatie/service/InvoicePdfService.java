package com.boekhoud.backendboekhoudapplicatie.service;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.Invoice;
import com.boekhoud.backendboekhoudapplicatie.util.InvoicePdfGenerator;
import com.itextpdf.text.DocumentException;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class InvoicePdfService {

    public ByteArrayOutputStream generatePdf(Invoice invoice) throws DocumentException, IOException {
        return InvoicePdfGenerator.generateInvoicePdf(invoice);
    }
}
