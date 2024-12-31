package repositories;
 
import models.Stock;
 
import java.util.*;
 
public class StockRepository {
    private final List<Stock> stocks = new ArrayList<>();
    private final Map<String, List<Stock>> categoryMap = new HashMap<>();
 
    public void addStock(Stock stock) {
        stocks.add(stock);
        categoryMap.computeIfAbsent(stock.getCategory(), k -> new ArrayList<>()).add(stock);
    }
 
    public List<Stock> getAllStocks() {
        return stocks;
    }
 
    public Optional<Stock> findStockByName(String name) {
        return stocks.stream().filter(s -> s.getName().equalsIgnoreCase(name)).findFirst();
    }
 
    public List<Stock> findStocksByCategory(String category) {
        return categoryMap.getOrDefault(category, Collections.emptyList());
    }
 
    public boolean removeStock(String name, int quantity) {
        Optional<Stock> stock = findStockByName(name);
        if (stock.isPresent()) {
            stock.get().removeQuantity(quantity);
            return true;
        }
        return false;
    }
}