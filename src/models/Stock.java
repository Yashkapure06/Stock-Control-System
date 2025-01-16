////package models;
////
////public class Stock {
////	private final String name;
////    private int quantity;
////    private final double price;
////    private final String category;
////    
////	public Stock() {
////		this.name = "";
////		this.quantity = 0;
////		this.price = 0.0;
////		this.category = "";
////	}
////    
////	public Stock(String name, int quantity, double price, String category) {
////		this.name = name;
////		this.quantity = quantity;
////		this.price = price;
////		this.category = category;
////	}
////	
////	public String getName() { return name; }
////    public int getQuantity() { return quantity; }
////    public double getPrice() { return price; }
////    public String getCategory() { return category; }
////
////    
//////    remove Quantity from Stock
////    public void removeQuantity(int quantityToRemove) {
////        if (quantityToRemove <= quantity) {
////            quantity -= quantityToRemove;
////        } else {
////            System.out.println("Not enough quantity to remove.");
////        }
////    }
////    
////    //    add Quantity to Stock
////    public void addQuantity(int quantityToAdd) {
////        quantity += quantityToAdd;
////    }
////
////	@Override
////	public String toString() {
////		return "Stock [name=" + name + ", quantity=" + quantity + ", price=" + price + ", category=" + category + "]";
////	}
////
////
////}
//
//
//
//package models;
//
//public class Stock {
//    private final String name;
//    private int quantity;
//    private double price; // Removed final to allow updates
//    private String category; // Removed final to allow updates
//
//    // Default constructor
//    public Stock() {
//        this.name = "";
//        this.quantity = 0;
//        this.price = 0.0;
//        this.category = "";
//    }
//
//    // Parameterized constructor
//    public Stock(String name, int quantity, double price, String category) {
//        this.name = name;
//        this.quantity = quantity;
//        this.price = price;
//        this.category = category;
//    }
//
//    // Getters
//    public String getName() {
//        return name;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    public double getPrice() {
//        return price;
//    }
//
//    public String getCategory() {
//        return category;
//    }
//
//    // Add quantity to stock
//    public void addQuantity(int quantityToAdd) {
//        quantity += quantityToAdd;
//    }
//
//    // Remove quantity from stock
//    public void removeQuantity(int quantityToRemove) {
//        if (quantityToRemove <= quantity) {
//            quantity -= quantityToRemove;
//        } else {
//            System.out.println("Not enough quantity to remove.");
//        }
//    }
//
//    // Setter for price
//    public void setPrice(double price) {
//        if (price >= 0) {
//            this.price = price;
//        } else {
//            System.out.println("Price cannot be negative.");
//        }
//    }
//
//    // Setter for category
//    public void setCategory(String category) {
//        if (category != null && !category.isEmpty()) {
//            this.category = category;
//        } else {
//            System.out.println("Category cannot be empty.");
//        }
//    }
//
//    @Override
//    public String toString() {
//        return "Stock [name=" + name + ", quantity=" + quantity + ", price=£" + price + ", category=" + category + "]";
//    }
//}
//
//
//package models;
//
//public class Stock {
//    private final String name;
//    private int quantity;
//    private double price; // Removed final to allow updates
//    private String category; // Removed final to allow updates
//
//    // Default constructor
//    public Stock() {
//        this.name = "";
//        this.quantity = 0;
//        this.price = 0.0;
//        this.category = "";
//    }
//
//    // Parameterized constructor
//    public Stock(String name, int quantity, double price, String category) {
//        this.name = name;
//        this.quantity = quantity;
//        this.price = price;
//        this.category = category;
//    }
//
//    // Getters
//    public String getName() {
//        return name;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    public double getPrice() {
//        return price;
//    }
//
//    public String getCategory() {
//        return category;
//    }
//
//    // Add quantity to stock
//    public void addQuantity(int quantityToAdd) {
//        quantity += quantityToAdd;
//    }
//
//    // Remove quantity from stock
//    public void removeQuantity(int quantityToRemove) {
//        if (quantityToRemove <= quantity) {
//            quantity -= quantityToRemove;
//        } else {
//            System.out.println("Not enough quantity to remove.");
//        }
//    }
//
//    // Setter for price
//    public void setPrice(double price) {
//        if (price >= 0) {
//            this.price = price;
//        } else {
//            System.out.println("Price cannot be negative.");
//        }
//    }
//
//    // Setter for category
//    public void setCategory(String category) {
//        if (category != null && !category.isEmpty()) {
//            this.category = category;
//        } else {
//            System.out.println("Category cannot be empty.");
//        }
//    }
//
//    @Override
//    public String toString() {
//        return "Stock [name=" + name + ", quantity=" + quantity + ", price=£" + price + ", category=" + category + "]";
//    }
//}

package models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Stock {
    private final String name;
    private int quantity;
    private double price; // Removed final to allow updates
    private String category; // Removed final to allow updates
    private Date expiryDate; 

    // Default constructor
    public Stock() {
        this.name = "";
        this.quantity = 0;
        this.price = 0.0;
        this.category = "";
    }

    // Parameterized constructor
    public Stock(String name, int quantity, double price, String category, Date expiryDate) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
        this.expiryDate = expiryDate;
    }

    // Getters
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

    // Add quantity to stock
    public void addQuantity(int quantityToAdd) {
        quantity += quantityToAdd;
    }

    // Remove quantity from stock
    public void removeQuantity(int quantityToRemove) {
        if (quantityToRemove <= quantity) {
            quantity -= quantityToRemove;
        } else {
            System.out.println("Not enough quantity to remove.");
        }
    }

    // Setter for price
    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        } else {
            System.out.println("Price cannot be negative.");
        }
    }

    // Setter for category
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
        return String.format(
            "+----------------+----------+----------+---------------+------------+\n" +
            "| Name           | Quantity | Price    | Category      | Expiry Date|\n" +
            "+----------------+----------+----------+---------------+------------+\n" +
            "| %-14s | %-8d | £%-7.2f | %-13s | %-10s |\n" +
            "+----------------+----------+----------+---------------+------------+",
            name, quantity, price, category, expiryDate
        );
    }

    }
//}

