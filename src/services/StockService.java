package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Stock;
import models.UserStocks;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class StockService {
//	this is yash path:
	//private static final String STOCKS_FILE  = "E:\\Eclipse\\Stock-Control-System\\src\\resources\\stocks.json";
	//This is Harsh Path:
    private static final String STOCKS_FILE = "C:\\Users\\acer\\eclipse-workspace\\Stock-Control-System\\src\\resources\\stocks.json";
	//This is ALex Path:
//	private static final String STOCKS_FILE = "C:\\Users\\alexa\\Stock-Control-System\\src\\resources\\stocks.json";
    private final ObjectMapper objectMapper;
    private List<UserStocks> allUserStocks;

    public StockService() {
        this.objectMapper = new ObjectMapper();
        this.allUserStocks = loadAllUserStocks();
    }

    private List<UserStocks> loadAllUserStocks() {
        try {
            File file = new File(STOCKS_FILE);
            if (!file.exists()) {
                file.createNewFile();
                objectMapper.writeValue(file, new ArrayList<>());
            }
            return objectMapper.readValue(file, new TypeReference<List<UserStocks>>() {});
        } catch (IOException e) {
            System.out.println("Error loading stocks: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void saveAllUserStocks() {
        try {
            File file = new File(STOCKS_FILE);
            objectMapper.writeValue(file, allUserStocks);
        } catch (IOException e) {
            System.out.println("Error saving stocks: " + e.getMessage());
        }
    }
    public List<Stock> getStocksForUser(String username) {
        for (UserStocks userStock : allUserStocks) {
            if (userStock.getUsername().equals(username)) {
                List<Stock> stocks = userStock.getStocks();
                if (stocks.isEmpty()) {
                    // *Potential Redundancy:* "No stocks available" is printed here but not always relevant.
                    System.out.println("No stocks available.");
                }
                return stocks;
            }
        }
        return new ArrayList<>();
    }


    public void addStockForUser(String username, Stock newStock) {
        System.out.println("Attempting to add stock: " + newStock.getName());
        UserStocks userStock = allUserStocks.stream()
                .filter(us -> us.getUsername().equals(username))
                .findFirst()
                .orElse(null);

        if (userStock == null) {
            System.out.println("UserStocks not found. Creating a new one.");
            userStock = new UserStocks(username, new ArrayList<>());
            allUserStocks.add(userStock);
        }

        // Check if a stock with the same name and category exists
        boolean stockExists = userStock.getStocks().stream()
                .anyMatch(stock -> stock.getName().equalsIgnoreCase(newStock.getName()) &&
                                   stock.getCategory().equalsIgnoreCase(newStock.getCategory()));
        System.out.println("Stock exists: " + stockExists);

        if (stockExists) {
            // Print error message and exit the method
            System.out.println("A stock with the name '" + newStock.getName() + 
                               "' in the category '" + newStock.getCategory() + 
                               "' already exists. Cannot add duplicate stock.");
            return; // Exit method here
        }

        // Add the stock if it doesn't exist
        userStock.getStocks().add(newStock);
        saveAllUserStocks(); // Save changes
        System.out.println("Stock added successfully!"); // Print success message only if stock is added
    }





    public void removeStockForUser(String username, String stockName) {
        UserStocks userStock = allUserStocks.stream()
                .filter(us -> us.getUsername().equals(username))
                .findFirst()
                .orElse(null);

        if (userStock != null) {
            userStock.getStocks().removeIf(stock -> stock.getName().equalsIgnoreCase(stockName));
            saveAllUserStocks();
        }
    }

    public double calculateTotalValueForUser(String username) {
        return getStocksForUser(username).stream()
                .mapToDouble(stock -> stock.getPrice() * stock.getQuantity())
                .sum();
    }

    public List<Stock> searchStocksByName(String username, String name) {
        return getStocksForUser(username).stream()
                .filter(stock -> stock.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    public List<Stock> filterStocksByCategory(String username, String category) {
        return getStocksForUser(username).stream()
                .filter(stock -> stock.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }
    
    //new alex
    
    public void removeExpiredStocks(String username) {
        UserStocks userStock = allUserStocks.stream()
                .filter(us -> us.getUsername().equals(username))
                .findFirst()
                .orElse(null);

        if (userStock != null) {
            List<Stock> stocks = userStock.getStocks();
            System.out.println("Before removal:\n " + stocks); // Debugging
            stocks.removeIf(Stock::isExpired); // Remove stocks where isExpired() returns true
            System.out.println("After removal: " + stocks); // Debugging
            saveAllUserStocks();
            System.out.println("Expired stocks removed successfully.");
        } else {
            System.out.println("No stocks found for user.");
        }
    }

    
    
    
    
    public static void handleSearchOrFilter(Scanner input, StockService stockService, String username) {
        System.out.println("\nSearch or Filter Stocks:");
        System.out.println("1. Search by name");
        System.out.println("2. Filter by category");
        System.out.print("Enter your choice: ");
        int searchFilterChoice = input.nextInt();
        input.nextLine();

        switch (searchFilterChoice) {
            case 1:
                System.out.print("Enter stock name to search: ");
                String searchName = input.nextLine();
                List<Stock> foundStocks = stockService.searchStocksByName(username, searchName);
                if (foundStocks.isEmpty()) {
                    System.out.println("No stocks found with the name: " + searchName);
                } else {
                    System.out.println("Stocks found:");
                    foundStocks.forEach(System.out::println);
                }
                break;

            case 2:
                System.out.print("Enter category to filter stocks: ");
                String filterCategory = input.nextLine();
                List<Stock> filteredStocks = stockService.filterStocksByCategory(username, filterCategory);
                if (filteredStocks.isEmpty()) {
                    System.out.println("No stocks found in the category: " + filterCategory);
                } else {
                    System.out.println("Stocks in category '" + filterCategory + "':");
                    filteredStocks.forEach(System.out::println);
                }
                break;

            default:
                System.out.println("Invalid choice!");
                break;
        }
    }
    
      public void updateStock(Scanner input, String username) {
        List<Stock> userStocks = getStocksForUser(username);

        if (userStocks.isEmpty()) {
            System.out.println("No stocks available to update.");
            return; 
        }

        System.out.println("Your Stocks:");
        for (int i = 0; i < userStocks.size(); i++) {
            System.out.printf("%d. %s (Quantity: %d, Price:  %.2f, Category: %s)\n",
                    i + 1,
                    userStocks.get(i).getName(),
                    userStocks.get(i).getQuantity(),
                    userStocks.get(i).getPrice(),
                    userStocks.get(i).getCategory());
        }

        System.out.print("Enter the number of the stock you want to update: ");
        int stockIndex = input.nextInt() - 1;
        input.nextLine(); // Consume newline

        if (stockIndex < 0 || stockIndex >= userStocks.size()) {
            System.out.println("Invalid selection. Returning to menu.");
            return; // *Correct behavior for invalid index.*
        }

        Stock selectedStock = userStocks.get(stockIndex);

        // Update quantity
        System.out.print("Do you want to add or subtract quantity? (Enter 'add' or 'subtract'): ");
        String quantityAction = input.nextLine().toLowerCase();

        if (quantityAction.equals("add") || quantityAction.equals("subtract")) {
            System.out.print("Enter the amount: ");
            int quantity = input.nextInt();
            input.nextLine(); // Consume newline

            if (quantityAction.equals("add")) {
                selectedStock.addQuantity(quantity);
                System.out.println("Quantity added successfully.");
            } else {
                selectedStock.removeQuantity(quantity);
                System.out.println("Quantity subtracted successfully.");
            }
        }

        // Update price
        System.out.print("Enter the new price (or press Enter to skip): ");
        String newPriceInput = input.nextLine();
        if (!newPriceInput.isEmpty()) {
            double newPrice = Double.parseDouble(newPriceInput);
            selectedStock.setPrice(newPrice);
            System.out.println("Price updated successfully.");
        }

        // Update category
        System.out.print("Enter the new category (or press Enter to skip): ");
        String newCategory = input.nextLine();
        if (!newCategory.isEmpty()) {
            selectedStock.setCategory(newCategory);
            System.out.println("Category updated successfully.");
        }

        // Save the updates
        saveAllUserStocks(); // *Ensure this saves the updated state only.*
        System.out.println("Stock updated successfully!");
    }
    
      
      
      
      
      
      
      
      
      
}
