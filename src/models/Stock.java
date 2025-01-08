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

public class Stock {
    private final String name; // Immutable
    private int quantity; // Mutable
    private double price; // Mutable
    private String category; // Mutable

    public Stock() {
        this.name = "";
        this.quantity = 0;
        this.price = 0.0;
        this.category = "";
    }

    public Stock(String name, int quantity, double price, String category) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
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

    // Setters
    public void setQuantity(int quantity) {
        if (quantity >= 0) { // Optional validation
            this.quantity = quantity;
        } else {
            System.out.println("Quantity cannot be negative.");
        }
    }

    public void setPrice(double price) {
        if (price >= 0) { // Optional validation
            this.price = price;
        } else {
            System.out.println("Price cannot be negative.");
        }
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Quantity adjustment methods
    public void removeQuantity(int quantityToRemove) {
        if (quantityToRemove <= quantity) {
            quantity -= quantityToRemove;
        } else {
            System.out.println("Not enough quantity to remove.");
        }
    }

    public void addQuantity(int quantityToAdd) {
        quantity += quantityToAdd;
    }

    @Override
    public String toString() {
        return "Stock [name=" + name + ", quantity=" + quantity + ", price=" + price + ", category=" + category + "]";
    }
}

