package services;

import models.Stock;
import repositories.StockRepository;

import java.util.List;

public class StockService {
    private final StockRepository repository;

    public StockService(StockRepository repository) {
        this.repository = repository;
    }

    public double calculateTotalInventoryValue() {
        return repository.getAllStocks()
                .stream()
                .mapToDouble(stock -> stock.getQuantity() * stock.getPrice())
                .sum();
    }

    public void displayInventoryReport() {
        repository.getAllStocks().forEach(stock -> {
            double value = stock.getQuantity() * stock.getPrice();
            System.out.printf("%s: Quantity=%d, Price=%.2f, Total Value=%.2f\n",
                    stock.getName(), stock.getQuantity(), stock.getPrice(), value);
        });
    }
}
