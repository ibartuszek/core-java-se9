package tutorial.ch02.exercise15;

import java.util.ArrayList;

/**
 * Fully implement the Invoice class in Section 2.6.1, “Static Nested Classes”
 * (page 85). Provide a method that prints the invoice and a demo program that
 * constructs and prints a sample invoice
 */
public class Invoice {

    public static class Item {
        private String description;
        private int quantity;
        private double unitPrice;

        public Item(final String description, final int quantity, final double unitPrice) {
            this.description = description;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
        }

        public String getDescription() {
            return description;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getUnitPrice() {
            return unitPrice;
        }

        public double price() {
            return quantity * unitPrice;
        }

        @Override
        public String toString() {
            return "Item{"
                + "description='" + description + '\''
                + ", quantity=" + quantity
                + ", unitPrice=" + unitPrice
                + '}';
        }
    }

    private ArrayList<Item> items = new ArrayList<>();

    public void addItem(final Item item) {
        items.add(item);
    }

    @Override
    public String toString() {
        return "Invoice{" +
            "items=" + items +
            '}';
    }

    public static void main(String[] args) {
        Invoice invoice = new Invoice();
        invoice.addItem(new Invoice.Item("Blackwell Toaster", 2, 19.95d));
        invoice.addItem(new Invoice.Item("Blackwell Toaster2", 1, 18.95d));
        System.out.println(invoice);
    }

}
