package models;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Stock {
    private final String name;
    private int quantity;
    private double price; 
    private String category;
    private Date expiryDate; 

    public Stock() {
        this.name = "";
        this.quantity = 0;
        this.price = 0.0;
        this.category = "";
        this.expiryDate = null;
    }

    public Stock(String name, int quantity, double price, String category, Date expiryDate) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
        this.expiryDate = expiryDate;
    }

    
    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public void addQuantity(int quantityToAdd) {
        quantity += quantityToAdd;
    }

    public void removeQuantity(int quantityToRemove) {
        if (quantityToRemove <= quantity) {
            quantity -= quantityToRemove;
        } else {
            System.out.println("Not enough quantity to remove.");
        }
    }

    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        } else {
            System.out.println("Price cannot be negative.");
        }
    }

    public void setCategory(String category) {
        if (category != null && !category.isEmpty()) {
            this.category = category;
        } else {
            System.out.println("Category cannot be empty.");
        }
    }
    
    public Date getExpiryDate() {
        return expiryDate;
    }

    @JsonIgnore
    public boolean isExpired() {
        return expiryDate != null && expiryDate.before(new Date());
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd"); // Format for "Fri Nov 15"
        String formattedExpiryDate = expiryDate != null ? dateFormat.format(expiryDate) : "N/A";

        return String.format(
            "+----------------+----------+----------+---------------+------------+\n" +
            "| Name           | Quantity | Price    | Category      | Expiry Date|\n" +
            "+----------------+----------+----------+---------------+------------+\n" +
            "| %-14s | %-8d | £%-7.2f | %-13s | %-10s |\n" +
            "+----------------+----------+----------+---------------+------------+",
            name, quantity, price, category, formattedExpiryDate
        );
    }

}


