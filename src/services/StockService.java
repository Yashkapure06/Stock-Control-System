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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class StockService {
    private static final String STOCKS_FILE = "C:\\Users\\acer\\eclipse-workspace\\Stock-Control-System\\src\\resources\\stocks.json";
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

    
    
    
    
    
    
    
    
}
