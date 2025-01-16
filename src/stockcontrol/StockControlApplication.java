package stockcontrol;

import models.Stock;
import models.User;
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

        User loggedInUser = null;

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
        StockService stockService = new StockService();
        String username = loggedInUser.getUsername();

        do {
            System.out.println("\nMenu:");
            System.out.println("1. Add stock");
            System.out.println("2. Remove stock");
            System.out.println("3. View stock");
            System.out.println("4. Calculate total inventory value");
            System.out.println("5. Search or filter stocks");
            System.out.println("6. Update stock");
            System.out.println("7. Remove expired stocks");
            System.out.println("8. Logout");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter stock name: ");
                    String name = input.nextLine();
                    System.out.print("Enter stock quantity: ");
                    int quantity = input.nextInt();
                    System.out.print("Enter stock price: ");
                    double price = input.nextDouble();
                    input.nextLine();
                    System.out.print("Enter stock category: ");
                    String category = input.nextLine();
                    System.out.print("Enter stock expiry date (yyyy-MM-dd): ");
                    String expiryDateInput = input.nextLine();
                    
                    Date expiryDate = null;
                    try {
                        expiryDate = new SimpleDateFormat("yyyy-MM-dd").parse(expiryDateInput);
                    } catch (ParseException e) {
                        System.out.println("Invalid date format. Stock will have no expiry date.");
                    }

                    Stock newStock = new Stock(name, quantity, price, category, expiryDate);
                    stockService.addStockForUser(username, newStock);
                    break;

                case 2:
                    System.out.print("Enter stock name to remove: ");
                    String removeName = input.nextLine();
                    stockService.removeStockForUser(username, removeName);
                    System.out.println("Stock removed successfully!");
                    break;

                case 3:
                    List<Stock> stocks = stockService.getStocksForUser(username);
                    if (stocks.isEmpty()) {
                        System.out.println("No stocks available.");
                    } else {
                        System.out.println("+----------------+----------+----------+---------------+---------------+");
                        System.out.println("| Name           | Quantity | Price    | Category      | Expiry Date   |");
                        System.out.println("+----------------+----------+----------+---------------+---------------+");

                        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd"); 
                        
                        for (Stock stock : stocks) {
                            String formattedExpiryDate = stock.getExpiryDate() != null ? 
                                                         dateFormat.format(stock.getExpiryDate()) : 
                                                         "N/A";

                            System.out.printf("| %-14s | %-8d | £ %-7.2f | %-13s | %-13s |\n",
                                              stock.getName(), stock.getQuantity(), stock.getPrice(), 
                                              stock.getCategory(), formattedExpiryDate);
                        }

                        System.out.println("+----------------+----------+----------+---------------+---------------+");
                    }
                    break;

                case 4:
                    double totalValue = stockService.calculateTotalValueForUser(username);
                    System.out.printf("Total inventory value: ï¿½ %.2f\n", totalValue);
                    break;
                case 5:
                	stockService.handleSearchOrFilter(input, stockService, username);
                    break;

                case 6:
                    stockService.updateStock(input, username);
                    break;
                case 7 :
                	System.out.println("Removing expired stocks for user: " + username);
                    stockService.removeExpiredStocks(username);
                    System.out.println("Returning to main menu...");
                    break;
                    
                case 8:
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