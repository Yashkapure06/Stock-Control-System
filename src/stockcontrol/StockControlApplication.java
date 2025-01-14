package stockcontrol;

import models.Stock;
import models.User;
import repositories.PurchaseOrderRepository;
import repositories.UserRepository;
import services.AuthService;
import services.StockService;

import java.util.Scanner;

public class StockControlApplication {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        UserRepository userRepository = new UserRepository();
        AuthService authService = new AuthService(userRepository);

        // Initialize PurchaseOrderRepository and StockService
        PurchaseOrderRepository purchaseOrderRepository = new PurchaseOrderRepository();
        StockService stockService = new StockService(purchaseOrderRepository);

        User loggedInUser = null;

        // Login or Register loop
        while (loggedInUser == null) {
            System.out.println("\n1. Login\n2. Register\nEnter your choice: ");
            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    loggedInUser = authService.login(input);
                    break;
                case 2:
                    loggedInUser = authService.register(input);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }

        System.out.println("\nWelcome, " + loggedInUser.getUsername() + "!");

        int choice;
        String username = loggedInUser.getUsername();

        // Main menu loop
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Add stock");
            System.out.println("2. Remove stock");
            System.out.println("3. View stock");
            System.out.println("4. Calculate total inventory value");
            System.out.println("5. Search or filter stocks");
            System.out.println("6. Update stock");
            System.out.println("7. Update stock with purchase order check");
            System.out.println("8. Logout");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
	            case 1:
	                // Add new stock
	            	System.out.print("Enter stock name: ");
	                String itemId = input.nextLine();
	                System.out.print("Enter stock name: ");
	                String itemName = input.nextLine();
	                System.out.print("Enter stock quantity: ");
	                int quantity = input.nextInt();
	                System.out.print("Enter stock price: ");
	                double price = input.nextDouble();
	                input.nextLine(); // Consume newline
	                System.out.print("Enter stock category: ");
	                String category = input.nextLine();
	                System.out.print("Enter minimum stock quantity: ");
	                int minimumQuantity = input.nextInt();

	                Stock newStock = new Stock(itemId, itemName, quantity, price, category, minimumQuantity);

                    stockService.addStockForUser(username, newStock);
                    System.out.println("Stock added successfully!");
                    break;

                case 2:
                    // Remove stock
                    System.out.print("Enter stock name to remove: ");
                    String removeName = input.nextLine();
                    stockService.removeStockForUser(username, removeName);
                    System.out.println("Stock removed successfully!");
                    break;

                case 3:
                    // View all stocks
                    System.out.println("Your Stocks:");
                    stockService.getStocksForUser(username).forEach(System.out::println);
                    break;

                case 4:
                    // Calculate total inventory value
                    double totalValue = stockService.calculateTotalValueForUser(username);
                    System.out.printf("Total inventory value: £%.2f\n", totalValue);
                    break;

                case 5:
                    // Search or filter stocks
                    StockService.handleSearchOrFilter(input, stockService, username);
                    break;

                case 6:
                    // Update stock details
                    stockService.updateStock(input, username);
                    /*
    				 * case 6: // Add case for updating stock
    				 * System.out.print("Enter stock name to update: "); String updateName =
    				 * input.nextLine(); System.out.print("Enter new quantity: "); int newQuantity =
    				 * input.nextInt(); System.out.print("Enter new price: "); double newPrice =
    				 * input.nextDouble(); input.nextLine(); // Clear newline
    				 * System.out.print("Enter new category: "); String newCategory =
    				 * input.nextLine();
    				 * 
    				 * boolean updated = stockService.updateStockForUser(username, updateName,
    				 * newQuantity, newPrice, newCategory); if (updated) {
    				 * System.out.println("Stock updated successfully!"); } else {
    				 * System.out.println("Stock not found or update failed."); } break;
    				 */
                    break;

                case 7:
                    // Update stock and check for purchase order
                    System.out.print("Enter stock ID to update: ");
                    String stockId = input.nextLine();
                    System.out.print("Enter quantity change (positive to add, negative to subtract): ");
                    int changeInQuantity = input.nextInt();
                    input.nextLine(); // Consume newline
                    stockService.updateStockAndCheck(username, stockId, changeInQuantity);
                    break;

                case 8:
                    // Logout
                    System.out.println("Logging out...");
                    break;

                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        } while (choice != 8);
        input.close();
    }
}
