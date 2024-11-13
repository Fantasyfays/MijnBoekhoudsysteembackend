package com.boekhoud.backendboekhoudapplicatie.util;


public class InvoiceCalculationUtils {

    public static double calculateSubtotal(double unitPrice, int quantity) {
        return unitPrice * quantity;
    }

    public static double calculateTax(double subtotal, double taxRate) {
        return (subtotal * taxRate) / 100;
    }

    public static double calculateTotalAmount(double subtotal, double tax) {
        return subtotal + tax;
    }
}
