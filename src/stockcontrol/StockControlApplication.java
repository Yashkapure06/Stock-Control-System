package stockcontrol;

import models.Stock;
import repositories.StockRepository;
import services.StockService;
import utils.ValidationUtil;

import java.util.Scanner;

public class StockControlApplication {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        StockRepository repository = new StockRepository();
        StockService service = new StockService(repository);


        int choice;

        do {
            System.out.println("\nMenu:");
            System.out.println("1. Add stock");
            System.out.println("2. Remove stock");
            System.out.println("3. View stock");
            System.out.println("4. Calculate total inventory value");
            System.out.println("5. Exit");
            choice = ValidationUtil.validateIntegerInput(input, "Enter your choice: ");

            switch (choice) {
                case 1:
                    input.nextLine();
                    System.out.print("Enter stock name: ");
                    String name = input.nextLine();
                    int quantity = ValidationUtil.validateIntegerInput(input, "Enter quantity: ");
                    System.out.print("Enter price: ");
                    double price = input.nextDouble();
                    input.nextLine();
                    System.out.print("Enter category: ");
                    String category = input.nextLine();


                    repository.addStock(new Stock(name, quantity, price, category));
                    System.out.println("Stock added successfully.");
                    break;

                case 2:
                    input.nextLine();
                    System.out.print("Enter stock name to remove: ");
                    String removeName = input.nextLine();
                    int removeQuantity = ValidationUtil.validateIntegerInput(input, "Enter quantity to remove: ");
                    if (repository.removeStock(removeName, removeQuantity)) {
                        System.out.println("Stock updated.");
                    } else {
                        System.out.println("Stock not found.");
                    }
                    break;
                case 3:
                    System.out.println("All Stocks:");
                    repository.getAllStocks().forEach(System.out::println);
                    break;
                case 4:
                    System.out.printf("Total Inventory Value: $%.2f\n", service.calculateTotalInventoryValue());
                    break;
 
                case 5:
                    System.out.println("Exiting...");
                    break;
 

                case 4:
                    System.out.printf("Total Inventory Value: $%.2f\n", service.calculateTotalInventoryValue());
                    break;

                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 5);
    input.close();
    }
}
