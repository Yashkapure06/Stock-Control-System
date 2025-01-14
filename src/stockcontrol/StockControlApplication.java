package stockcontrol;

import models.Stock;
import models.User;
import repositories.PurchaseOrderRepository;
import repositories.UserRepository;
import services.AuthService;
import services.StockService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
            System.out.println("9. view expired stock");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
	            case 1:
	            	try {
                        System.out.print("Enter stock ID: ");
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
                        input.nextLine(); // Consume newline

                        System.out.print("Enter batch ID: ");
                        String batchId = input.nextLine();

                        System.out.print("Enter expiry date (yyyy-MM-dd): ");
                        String expiryDateInput = input.nextLine();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date expiryDate = dateFormat.parse(expiryDateInput);

                        Stock newStock = new Stock(itemId, itemName, quantity, price, category, minimumQuantity, batchId, expiryDate);
                        stockService.addStockForUser(username, newStock);
                        System.out.println("Stock added successfully!");
                    } catch (ParseException e) {
                        System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                    }
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
                    
                case 9:
                    // Handle expired stocks
                    System.out.println("\nExpired Stocks:");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    // Retrieve expired stocks
                    List<Stock> expiredStocks = stockService.getExpiredStocks();

                    if (expiredStocks.isEmpty()) {
                        System.out.println("No expired stocks found.");
                    } else {
                        // Display expired stocks
                        for (int i = 0; i < expiredStocks.size(); i++) {
                            Stock stock = expiredStocks.get(i);
                            System.out.printf(
                                "%d. %s (Batch: %s, Expiry: %s, Quantity: %d, Category: %s)%n",
                                i + 1,
                                stock.getItemName(),
                                stock.getBatchId(),
                                dateFormat.format(stock.getExpiryDate()),
                                stock.getQuantity(),
                                stock.getCategory()
                            );
                        }

                        // User actions
                        System.out.println("\nWhat would you like to do?");
                        System.out.println("1. Remove all expired stocks");
                        System.out.println("2. Generate a report of expired stocks");
                        System.out.println("3. Take no action");
                        System.out.print("Enter your choice: ");

                        int actionChoice = input.nextInt();
                        input.nextLine(); // Consume newline

                        switch (actionChoice) {
                            case 1:
                                // Remove all expired stocks
                                stockService.removeExpiredStocks(expiredStocks);
                                System.out.println("All expired stocks have been removed.");
                                break;

                            case 2:
                                // Generate a report
                                System.out.println("\nGenerating expired stock report...");
                                for (Stock stock : expiredStocks) {
                                    System.out.printf(
                                        "Item: %s, Batch: %s, Expiry: %s, Quantity: %d, Category: %s%n",
                                        stock.getItemName(),
                                        stock.getBatchId(),
                                        dateFormat.format(stock.getExpiryDate()),
                                        stock.getQuantity(),
                                        stock.getCategory()
                                    );
                                }
                                System.out.println("Report generation complete.");
                                break;

                            case 3:
                                // Take no action
                                System.out.println("No action taken.");
                                break;

                            default:
                                System.out.println("Invalid choice. Returning to main menu.");
                        }
                    }
                    break;

                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        } while (choice != 8);
        input.close();
    }
}
