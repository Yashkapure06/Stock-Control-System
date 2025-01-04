package models;

public class Stock {
	private final String name;
    private int quantity;
    private final double price;
    private final String category;
    
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
	
	public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }

    
//    remove Quantity from Stock
    public void removeQuantity(int quantityToRemove) {
        if (quantityToRemove <= quantity) {
            quantity -= quantityToRemove;
        } else {
            System.out.println("Not enough quantity to remove.");
        }
    }
    
    //    add Quantity to Stock
    public void addQuantity(int quantityToAdd) {
        quantity += quantityToAdd;
    }

	@Override
	public String toString() {
		return "Stock [name=" + name + ", quantity=" + quantity + ", price=" + price + ", category=" + category + "]";
	}


}
