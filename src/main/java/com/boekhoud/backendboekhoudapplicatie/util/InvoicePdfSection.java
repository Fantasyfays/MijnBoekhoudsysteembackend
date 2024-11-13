package com.boekhoud.backendboekhoudapplicatie.util;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.Invoice;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class InvoicePdfSection {

    public static Element createInvoiceHeader(Invoice invoice) throws DocumentException {
        Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD);
        Font companyFont = new Font(Font.FontFamily.HELVETICA, 12);

        // Create header paragraphs individually and set alignment separately
        Paragraph header = new Paragraph();

        Paragraph logo = new Paragraph("Your Company Logo", companyFont);
        logo.setAlignment(Element.ALIGN_LEFT);
        header.add(logo);

        Paragraph title = new Paragraph("INVOICE", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        header.add(title);

        Paragraph clientName = new Paragraph(invoice.getClient().getName(), companyFont);
        clientName.setAlignment(Element.ALIGN_CENTER);
        header.add(clientName);

        header.add(new Paragraph(" "));
        return header;
    }

    public static Element createSectionHeader(String title) throws DocumentException {
        Font sectionFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
        Paragraph sectionHeader = new Paragraph(title, sectionFont);
        sectionHeader.setAlignment(Element.ALIGN_LEFT);
        sectionHeader.add(new Paragraph(" "));
        return sectionHeader;
    }

    public static Element createSenderInformation(Invoice invoice) throws DocumentException {
        PdfPTable table = createTable(2);
        addTableRow(table, "Sender Name:", invoice.getClient().getName());
        addTableRow(table, "Sender Address:", invoice.getClient().getAddress());
        addTableRow(table, "Sender Email:", invoice.getClient().getEmail());
        addTableRow(table, "Sender Phone:", invoice.getClient().getPhoneNumber());
        return table;
    }

    public static Element createRecipientInformation(Invoice invoice) throws DocumentException {
        PdfPTable table = createTable(2);
        addTableRow(table, "Recipient Name:", invoice.getRecipientName());
        addTableRow(table, "Recipient Company:", invoice.getRecipientCompany());
        addTableRow(table, "Recipient Address:", invoice.getRecipientAddress());
        addTableRow(table, "Recipient Email:", invoice.getRecipientEmail());
        return table;
    }

    public static Element createInvoiceDetails(Invoice invoice) throws DocumentException {
        PdfPTable table = createTable(2);
        addTableRow(table, "Invoice Number:", invoice.getInvoiceNumber());
        addTableRow(table, "Invoice Date:", invoice.getInvoiceDate().toString());
        addTableRow(table, "Due Date:", invoice.getDueDate().toString());
        addTableRow(table, "Description:", invoice.getDescription());
        return table;
    }

    public static Element createLineItems(Invoice invoice) throws DocumentException {
        PdfPTable table = createTable(4);
        addTableRow(table, "Item", "Quantity", "Unit Price", "Total");
        addTableRow(table, invoice.getDescription(), String.valueOf(invoice.getQuantity()),
                String.format("%.2f", invoice.getUnitPrice()),
                String.format("%.2f", invoice.getSubtotal()));
        return table;
    }

    public static Element createTotals(Invoice invoice) throws DocumentException {
        PdfPTable table = createTable(2);
        table.setHorizontalAlignment(Element.ALIGN_RIGHT);
        addTableRow(table, "Subtotal:", String.format("%.2f", invoice.getSubtotal()));
        addTableRow(table, "Tax:", String.format("%.2f", invoice.getTax()));
        addTableRow(table, "Total Amount:", String.format("%.2f", invoice.getTotalAmount()));
        return table;
    }

    public static Element createFooter() throws DocumentException {
        Font font = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC);

        Paragraph footer = new Paragraph();

        Paragraph thankYou = new Paragraph("Thank you for your business!", font);
        thankYou.setAlignment(Element.ALIGN_CENTER);
        footer.add(thankYou);

        Paragraph paymentTerms = new Paragraph("Please make payment within 30 days to the specified bank account.", font);
        paymentTerms.setAlignment(Element.ALIGN_CENTER);
        footer.add(paymentTerms);

        return footer;
    }

    private static PdfPTable createTable(int columns) {
        PdfPTable table = new PdfPTable(columns);
        table.setWidthPercentage(100);
        table.setSpacingBefore(5f);
        table.setSpacingAfter(10f);
        return table;
    }

    private static void addTableRow(PdfPTable table, String... cells) {
        for (String cellContent : cells) {
            PdfPCell cell = new PdfPCell(new Phrase(cellContent));
            cell.setBorderWidth(0.5f);
            cell.setPadding(5);
            table.addCell(cell);
        }
    }
}
