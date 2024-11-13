package com.boekhoud.backendboekhoudapplicatie.util;

import com.boekhoud.backendboekhoudapplicatie.dal.entity.Invoice;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.ByteArrayOutputStream;
import java.util.logging.Logger;

public class InvoicePdfGenerator {

    private static final Logger logger = Logger.getLogger(InvoicePdfGenerator.class.getName());

    public static ByteArrayOutputStream generateInvoicePdf(Invoice invoice) {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50); // Add margins
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            document.addTitle("Invoice PDF");
            document.addAuthor("Your Company Name");

            addInvoiceHeader(document, invoice);
            addSectionHeader(document, "Sender Information");
            addSenderInformation(document, invoice);

            addSectionHeader(document, "Recipient Information");
            addRecipientInformation(document, invoice);

            addSectionHeader(document, "Invoice Details");
            addInvoiceDetails(document, invoice);

            addSectionHeader(document, "Items");
            addLineItems(document, invoice);

            addSectionHeader(document, "Invoice Summary");
            addTotals(document, invoice);

            addFooter(document);

        } catch (DocumentException e) {
            logger.severe("Error generating PDF: " + e.getMessage());
        } finally {
            document.close();
        }
        return out;
    }

    private static void addInvoiceHeader(Document document, Invoice invoice) throws DocumentException {
        Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD);
        Font companyFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

        Paragraph logo = new Paragraph("Your Company Logo", companyFont);
        logo.setAlignment(Element.ALIGN_LEFT);
        document.add(logo);

        Paragraph title = new Paragraph("INVOICE", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Paragraph companyName = new Paragraph(invoice.getClient().getName(), companyFont);
        companyName.setAlignment(Element.ALIGN_CENTER);
        document.add(companyName);

        document.add(new Paragraph(" "));
    }

    private static void addSectionHeader(Document document, String title) throws DocumentException {
        Font sectionFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
        Paragraph sectionTitle = new Paragraph(title, sectionFont);
        sectionTitle.setAlignment(Element.ALIGN_LEFT);
        document.add(sectionTitle);
        document.add(new Paragraph(" "));
    }

    private static void addSenderInformation(Document document, Invoice invoice) throws DocumentException {
        Font font = new Font(Font.FontFamily.HELVETICA, 12);
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(5f);
        table.setSpacingAfter(10f);

        addTableCell(table, "Sender Name:", font, true);
        addTableCell(table, invoice.getClient().getName(), font, false);

        addTableCell(table, "Sender Address:", font, true);
        addTableCell(table, invoice.getClient().getAddress(), font, false);

        addTableCell(table, "Sender Email:", font, true);
        addTableCell(table, invoice.getClient().getEmail(), font, false);

        addTableCell(table, "Sender Phone:", font, true);
        addTableCell(table, invoice.getClient().getPhoneNumber(), font, false);

        document.add(table);
    }

    private static void addRecipientInformation(Document document, Invoice invoice) throws DocumentException {
        Font font = new Font(Font.FontFamily.HELVETICA, 12);
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(5f);
        table.setSpacingAfter(10f);

        addTableCell(table, "Recipient Name:", font, true);
        addTableCell(table, invoice.getRecipientName() != null ? invoice.getRecipientName() : "", font, false);

        addTableCell(table, "Recipient Company:", font, true);
        addTableCell(table, invoice.getRecipientCompany() != null ? invoice.getRecipientCompany() : "", font, false);

        addTableCell(table, "Recipient Address:", font, true);
        addTableCell(table, invoice.getRecipientAddress() != null ? invoice.getRecipientAddress() : "", font, false);

        addTableCell(table, "Recipient Email:", font, true);
        addTableCell(table, invoice.getRecipientEmail() != null ? invoice.getRecipientEmail() : "", font, false);

        document.add(table);
    }

    private static void addInvoiceDetails(Document document, Invoice invoice) throws DocumentException {
        Font font = new Font(Font.FontFamily.HELVETICA, 12);
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(5f);
        table.setSpacingAfter(10f);

        addTableCell(table, "Invoice Number:", font, true);
        addTableCell(table, invoice.getInvoiceNumber(), font, false);

        addTableCell(table, "Invoice Date:", font, true);
        addTableCell(table, invoice.getInvoiceDate().toString(), font, false);

        addTableCell(table, "Due Date:", font, true);
        addTableCell(table, invoice.getDueDate().toString(), font, false);

        addTableCell(table, "Description:", font, true);
        addTableCell(table, invoice.getDescription(), font, false);

        document.add(table);
    }

    private static void addLineItems(Document document, Invoice invoice) throws DocumentException {
        Font font = new Font(Font.FontFamily.HELVETICA, 12);
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setSpacingBefore(5f);
        table.setSpacingAfter(10f);

        addTableCell(table, "Item", font, true);
        addTableCell(table, "Quantity", font, true);
        addTableCell(table, "Unit Price", font, true);
        addTableCell(table, "Total", font, true);

        addTableCell(table, invoice.getDescription(), font, false);
        addTableCell(table, String.valueOf(invoice.getQuantity()), font, false);
        addTableCell(table, String.format("%.2f", invoice.getUnitPrice()), font, false);
        addTableCell(table, String.format("%.2f", invoice.getSubtotal()), font, false);

        document.add(table);
    }

    private static void addTotals(Document document, Invoice invoice) throws DocumentException {
        Font font = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(50);
        table.setHorizontalAlignment(Element.ALIGN_RIGHT);

        addTableCell(table, "Subtotal:", font, true);
        addTableCell(table, String.format("%.2f", invoice.getSubtotal()), font, false);

        addTableCell(table, "Tax:", font, true);
        addTableCell(table, String.format("%.2f", invoice.getTax()), font, false);

        addTableCell(table, "Total Amount:", font, true);
        addTableCell(table, String.format("%.2f", invoice.getTotalAmount()), font, false);

        document.add(table);
    }

    private static void addFooter(Document document) throws DocumentException {
        Font font = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC);
        Paragraph footer = new Paragraph("Thank you for your business!", font);
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);

        Paragraph terms = new Paragraph("Please make payment within 30 days to the bank account specified. For any inquiries, contact us at info@company.com.", font);
        terms.setAlignment(Element.ALIGN_CENTER);
        document.add(terms);
    }

    private static void addTableCell(PdfPTable table, String content, Font font, boolean isHeader) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setBorderWidth(0.5f);
        cell.setPadding(5);
        if (isHeader) {
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            font.setStyle(Font.BOLD);
        }
        table.addCell(cell);
    }
}
