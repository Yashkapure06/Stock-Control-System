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
    private final String name;
    private final String itemId;
    private int quantity;
    private double price; // Removed final to allow updates
    private String category; // Removed final to allow updates
    private StockHistory stockHistory;

    // Default constructor
    public Stock() {
        this.name = "";
        this.itemId="";
        this.quantity = 0;
        this.price = 0.0;
        this.category = "";
        this.stockHistory = new StockHistory(itemId);
    }

    // Parameterized constructor
    public Stock(String name, String itemId, int quantity, double price, String category) {
        this.name = name;
        this.itemId = itemId;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
        this.stockHistory = new StockHistory(itemId);;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getItemId() { 
        return itemId;
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

    public String getStockHistory() {
        return stockHistory.toString();
    }

    // Add quantity to stock
    public void addQuantity(int quantityToAdd) {
        quantity += quantityToAdd;
        stockHistory.addHistoryRecord(quantityToAdd, "Addition", "No Comment");
    }

    // Add quantity to stock with a reason
    public void addQuantity(int quantityToAdd, String description) {
        quantity += quantityToAdd;
        stockHistory.addHistoryRecord(quantityToAdd, "Addition", description);
    }

    // Remove quantity from stock
    public void removeQuantity(int quantityToRemove) {
        if (quantityToRemove <= quantity) {
            quantity -= quantityToRemove;
            stockHistory.addHistoryRecord(quantityToRemove, "Removal", "No Comment");
        } else {
            System.out.println("Not enough quantity to remove.");
        }
    }

    // Remove quantity from stock with a reason
    public void removeQuantity(int quantityToRemove, String description) { 
        if (quantityToRemove <= quantity) {
            quantity -= quantityToRemove;
            stockHistory.addHistoryRecord(quantityToRemove, "Removal", description);
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

    @Override
    public String toString() {
        return "Stock [name=" + name + ", ID= " + itemId + ", quantity=" + quantity + ", price=" + price + ", category=" + category + ", Stock History= " + stockHistory.toString() + "}]";
    }
}


