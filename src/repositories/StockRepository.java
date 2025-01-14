package repositories;

import models.Stock;

import java.util.*;

public class StockRepository {
    private final List<Stock> stocks = new ArrayList<>();
    private final Map<String, List<Stock>> categoryMap = new HashMap<>();

    // Add a new stock
    public void addStock(Stock stock) {
        stocks.add(stock);
        categoryMap.computeIfAbsent(stock.getCategory(), k -> new ArrayList<>()).add(stock);
    }

    // Get all stocks
    public List<Stock> getAllStocks() {
        return stocks;
    }

    // Find a stock by name
    public Optional<Stock> findStockByName(String itemName) {
        return stocks.stream()
                .filter(s -> s.getItemName().equalsIgnoreCase(itemName))
                .findFirst();
    }

    // Find stocks by category
    public List<Stock> findStocksByCategory(String category) {
        return categoryMap.getOrDefault(category, Collections.emptyList());
    }

    // Remove stock by name and quantity
    public boolean removeStock(String itemName, int quantity) {
        Optional<Stock> stock = findStockByName(itemName);
        if (stock.isPresent()) {
            Stock foundStock = stock.get();
            if (quantity > foundStock.getQuantity()) {
                System.out.println("Error: Quantity to remove exceeds available stock.");
                return false;
            }
            foundStock.setQuantity(foundStock.getQuantity() - quantity);
            return true;
        }
        return false;
    }

    // Find a stock by itemId
    public Optional<Stock> findStockById(String itemId) {
        return stocks.stream()
                .filter(stock -> stock.getItemId().equals(itemId))
                .findFirst();
    }

    // Check stocks below minimum stock level
    public List<Stock> getStocksBelowMinimum() {
        List<Stock> lowStocks = new ArrayList<>();
        for (Stock stock : stocks) {
            if (stock.getQuantity() < stock.getMinimumStock()) {
                lowStocks.add(stock);
            }
        }
        return lowStocks;
    }
}
