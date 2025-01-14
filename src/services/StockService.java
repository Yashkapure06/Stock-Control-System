//package services;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import models.Stock;
//import models.UserStocks;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class StockService {
//	//This is yash path
//    //private static final String STOCKS_FILE = "E:\\Eclipse\\Stock-Control-System\\src\\resources\\stocks.json";
//    //This is Harsh path
//	private static final String STOCKS_FILE ="C:\\Users\\acer\\eclipse-workspace\\Stock-Control-System\\src\\resources\\stocks.json";
//	private final ObjectMapper objectMapper;
//    private List<UserStocks> allUserStocks;
//
//    public StockService() {
//        this.objectMapper = new ObjectMapper();
//        this.allUserStocks = loadAllUserStocks();
//    }
//
//  
//    
//    
//    private List<UserStocks> loadAllUserStocks() {
//        try {
//            File file = new File(STOCKS_FILE);
//            if (!file.exists()) {
//                file.createNewFile();
//                objectMapper.writeValue(file, new ArrayList<>());
//            }
//            return objectMapper.readValue(file, new TypeReference<List<UserStocks>>() {});
//        } catch (IOException e) {
//            System.out.println("Error loading stocks: " + e.getMessage());
//            return new ArrayList<>();
//        }
//    }
//
//    private void saveAllUserStocks() {
//        try {
//            File file = new File(STOCKS_FILE);
//            objectMapper.writeValue(file, allUserStocks);
//        } catch (IOException e) {
//            System.out.println("Error saving stocks: " + e.getMessage());
//        }
//    }
//    public List<Stock> getStocksForUser(String username) {
//        for (UserStocks userStock : allUserStocks) {
//            if (userStock.getUsername().equals(username)) {
//                return userStock.getStocks();
//            }
//        }
//        return new ArrayList<>(); // Return empty if no stocks found
//    }
//
//    public void addStockForUser(String username, Stock newStock) {
//        UserStocks userStock = allUserStocks.stream()
//                .filter(us -> us.getUsername().equals(username))
//                .findFirst()
//                .orElse(null);
//
//        if (userStock == null) {
//            userStock = new UserStocks(username, new ArrayList<>());
//            allUserStocks.add(userStock);
//        }
//
//        userStock.getStocks().add(newStock);
//        saveAllUserStocks();
//    }
//
//    public void removeStockForUser(String username, String stockName) {
//        UserStocks userStock = allUserStocks.stream()
//                .filter(us -> us.getUsername().equals(username))
//                .findFirst()
//                .orElse(null);
//
//        if (userStock != null) {
//            userStock.getStocks().removeIf(stock -> stock.getName().equalsIgnoreCase(stockName));
//            saveAllUserStocks();
//        }
//    }
//
//    public double calculateTotalValueForUser(String username) {
//        return getStocksForUser(username).stream()
//                .mapToDouble(stock -> stock.getPrice() * stock.getQuantity())
//                .sum();
//    }
//}
package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Stock;
import models.UserStocks;
import models.PurchaseOrder;
import repositories.PurchaseOrderRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class StockService {
	//This is Harsh Path:
    private static final String STOCKS_FILE = "C:\\Users\\acer\\eclipse-workspace\\Stock-Control-System\\src\\resources\\stocks.json";
    private final ObjectMapper objectMapper;
    private List<UserStocks> allUserStocks;
    private final PurchaseOrderRepository purchaseOrderRepository;

    public StockService(PurchaseOrderRepository purchaseOrderRepository) {
        this.objectMapper = new ObjectMapper();
        this.allUserStocks = loadAllUserStocks();
        this.purchaseOrderRepository = purchaseOrderRepository;
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

    public void updateStockAndCheck(String username, String stockId, int changeInQuantity) {
        UserStocks userStock = allUserStocks.stream()
                .filter(us -> us.getUsername().equals(username))
                .findFirst()
                .orElse(null);

        if (userStock == null) {
            System.out.println("User does not exist!");
            return;
        }

        Stock stockToUpdate = userStock.getStocks().stream()
                .filter(stock -> stock.getItemId().equals(stockId))
                .findFirst()
                .orElse(null);

        if (stockToUpdate == null) {
            System.out.println("Stock does not exist!");
            return;
        }

        stockToUpdate.setQuantity(stockToUpdate.getQuantity() + changeInQuantity);

        if (stockToUpdate.getQuantity() < stockToUpdate.getMinimumStock()) {
            generatePurchaseOrder(stockToUpdate);
        }

        saveAllUserStocks();
    }

    private void generatePurchaseOrder(Stock stock) {
        int quantityToOrder = stock.getMinimumStock() - stock.getQuantity();
        PurchaseOrder order = new PurchaseOrder(stock.getItemId(), quantityToOrder, new Date());
        purchaseOrderRepository.save(order);
        System.out.println("Purchase order generated for stock: " + stock.getItemName());
    }

    public List<Stock> getStocksForUser(String username) {
        for (UserStocks userStock : allUserStocks) {
            if (userStock.getUsername().equals(username)) {
                return userStock.getStocks();
            }
        }
        return new ArrayList<>();
    }

    public void addStockForUser(String username, Stock newStock) {
        UserStocks userStock = allUserStocks.stream()
                .filter(us -> us.getUsername().equals(username))
                .findFirst()
                .orElse(null);

        if (userStock == null) {
            userStock = new UserStocks(username, new ArrayList<>());
            allUserStocks.add(userStock);
        }

        userStock.getStocks().add(newStock);
        saveAllUserStocks();
    }

    public void removeStockForUser(String username, String stockName) {
        UserStocks userStock = allUserStocks.stream()
                .filter(us -> us.getUsername().equals(username))
                .findFirst()
                .orElse(null);

        if (userStock != null) {
            userStock.getStocks().removeIf(stock -> stock.getItemName().equalsIgnoreCase(stockName));
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
                .filter(stock -> stock.getItemName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    public List<Stock> filterStocksByCategory(String username, String category) {
        return getStocksForUser(username).stream()
                .filter(stock -> stock.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }
    
    public List<Stock> getExpiredStocks() {
        Date currentDate = new Date();
        List<Stock> expiredStocks = new ArrayList<>();

        for (UserStocks userStock : allUserStocks) {
            expiredStocks.addAll(
                userStock.getStocks().stream()
                    .filter(stock -> stock.getExpiryDate().before(currentDate))
                    .collect(Collectors.toList())
            );
        }

        return expiredStocks;
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

        // List all stocks
        System.out.println("Your Stocks:");
        for (int i = 0; i < userStocks.size(); i++) {
            System.out.printf("%d. %s (Quantity: %d, Price: £%.2f, Category: %s)\n",
                    i + 1,
                    userStocks.get(i).getItemName(),
                    userStocks.get(i).getQuantity(),
                    userStocks.get(i).getPrice(),
                    userStocks.get(i).getCategory());
        }

        // Ask user to choose a stock
        System.out.print("Enter the number of the stock you want to update: ");
        int stockIndex = input.nextInt() - 1;
        input.nextLine(); // Consume newline

        if (stockIndex < 0 || stockIndex >= userStocks.size()) {
            System.out.println("Invalid selection. Returning to menu.");
            return;
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
        saveAllUserStocks();
        System.out.println("Stock updated successfully!");
    }

    public void removeExpiredStocks(List<Stock> expiredStocks) {
        for (Stock expiredStock : expiredStocks) {
            allUserStocks.forEach(userStock -> 
                userStock.getStocks().removeIf(stock -> stock.equals(expiredStock))
            );
        }
        saveAllUserStocks();
    }
    
    

}
