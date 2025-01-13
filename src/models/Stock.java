//package models;
//
//public class Stock {
//	private final String name;
//    private int quantity;
//    private final double price;
//    private final String category;
//    
//	public Stock() {
//		this.name = "";
//		this.quantity = 0;
//		this.price = 0.0;
//		this.category = "";
//	}
//    
//	public Stock(String name, int quantity, double price, String category) {
//		this.name = name;
//		this.quantity = quantity;
//		this.price = price;
//		this.category = category;
//	}
//	
//	public String getName() { return name; }
//    public int getQuantity() { return quantity; }
//    public double getPrice() { return price; }
//    public String getCategory() { return category; }
//
//    
////    remove Quantity from Stock
//    public void removeQuantity(int quantityToRemove) {
//        if (quantityToRemove <= quantity) {
//            quantity -= quantityToRemove;
//        } else {
//            System.out.println("Not enough quantity to remove.");
//        }
//    }
//    
//    //    add Quantity to Stock
//    public void addQuantity(int quantityToAdd) {
//        quantity += quantityToAdd;
//    }
//
//	@Override
//	public String toString() {
//		return "Stock [name=" + name + ", quantity=" + quantity + ", price=" + price + ", category=" + category + "]";
//	}
//
//
//}



package models;

import java.util.Objects;
import java.util.Date;

public class Stock {
    private String itemId;
    private String itemName;
    private int quantity;
    private int minimumStock; // New field

    // Constructor
    public Stock(String itemId, String itemName, int quantity, int minimumStock) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
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
        this.quantity = quantity;
    }

    public int getMinimumStock() {
        return minimumStock;
    }

    public void setMinimumStock(int minimumStock) {
        this.minimumStock = minimumStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return quantity == stock.quantity &&
               minimumStock == stock.minimumStock &&
               Objects.equals(itemId, stock.itemId) &&
               Objects.equals(itemName, stock.itemName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, itemName, quantity, minimumStock);
    }

    @Override
    public String toString() {
        return "Stock{" +
               "itemId='" + itemId + '\'' +
               ", itemName='" + itemName + '\'' +
               ", quantity=" + quantity +
               ", minimumStock=" + minimumStock +
               '}';
    }
}


