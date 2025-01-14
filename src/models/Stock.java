package models;

import java.util.Objects;

public class Stock {

    private String itemId;
    private String itemName;
    private int quantity;
    private double price;
    private String category;
    private int minimumStock;

    // Constructor
    public Stock(String itemId, String itemName, int quantity, double price, String category, int minimumStock) {
        if (quantity < 0 || price < 0 || minimumStock < 0) {
            throw new IllegalArgumentException("Quantity, price, and minimum stock cannot be negative.");
        }
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
        this.minimumStock = minimumStock;
    }

    // Getters and Setters
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getMinimumStock() {
        return minimumStock;
    }

    public void setMinimumStock(int minimumStock) {
        if (minimumStock < 0) {
            throw new IllegalArgumentException("Minimum stock cannot be negative.");
        }
        this.minimumStock = minimumStock;
    }

    // Add quantity to stock
    public void addQuantity(int quantityToAdd) {
        if (quantityToAdd < 0) {
            throw new IllegalArgumentException("Quantity to add cannot be negative.");
        }
        this.quantity += quantityToAdd;
    }

    // Remove quantity from stock
    public void removeQuantity(int quantityToRemove) {
        if (quantityToRemove < 0) {
            throw new IllegalArgumentException("Quantity to remove cannot be negative.");
        }
        if (quantityToRemove > this.quantity) {
            throw new IllegalArgumentException("Cannot remove more than available quantity.");
        }
        this.quantity -= quantityToRemove;
    }

    // Override toString
    @Override
    public String toString() {
        return "Stock{" +
                "itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", minimumStock=" + minimumStock +
                '}';
    }

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return quantity == stock.quantity &&
                Double.compare(stock.price, price) == 0 &&
                minimumStock == stock.minimumStock &&
                Objects.equals(itemId, stock.itemId) &&
                Objects.equals(itemName, stock.itemName) &&
                Objects.equals(category, stock.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, itemName, quantity, price, category, minimumStock);
    }
}
