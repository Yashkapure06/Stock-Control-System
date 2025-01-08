package stockcontrol;

import models.Stock;
import models.User;
import repositories.UserRepository;
import services.AuthService;
import services.StockService;

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
            System.out.println("7. Logout");
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

                    Stock newStock = new Stock(name, quantity, price, category);
                    stockService.addStockForUser(username, newStock);
                    System.out.println("Stock added successfully!");
                    break;

                case 2:
                    System.out.print("Enter stock name to remove: ");
                    String removeName = input.nextLine();
                    stockService.removeStockForUser(username, removeName);
                    System.out.println("Stock removed successfully!");
                    break;

                case 3:
                    System.out.println("Your Stocks:");
                    stockService.getStocksForUser(username).forEach(System.out::println);
                    break;

                case 4:
                    double totalValue = stockService.calculateTotalValueForUser(username);
                    System.out.printf("Total inventory value: £%.2f\n", totalValue);
                    break;
                case 5:
                	stockService.handleSearchOrFilter(input, stockService, username);
                    break;
                case 6:  // Add case for updating stock
                    System.out.print("Enter stock name to update: ");
                    String updateName = input.nextLine();
                    System.out.print("Enter new quantity: ");
                    int newQuantity = input.nextInt();
                    System.out.print("Enter new price: ");
                    double newPrice = input.nextDouble();
                    input.nextLine();  // Clear newline
                    System.out.print("Enter new category: ");
                    String newCategory = input.nextLine();

                    boolean updated = stockService.updateStockForUser(username, updateName, newQuantity, newPrice, newCategory);
                    if (updated) {
                        System.out.println("Stock updated successfully!");
                    } else {
                        System.out.println("Stock not found or update failed.");
                    }
                    break;


                case 7:
                    System.out.println("Logging out...");
                    break;

                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        } while (choice != 7);
        input.close();
    }
}
