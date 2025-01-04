package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.Stock;
import models.UserStocks;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StockService {
    private static final String STOCKS_FILE = "E:\\Eclipse\\Stock-Control-System\\src\\resources\\stocks.json";
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
        return new ArrayList<>(); // Return empty if no stocks found
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
}
