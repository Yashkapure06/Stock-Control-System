package testcases.blackbox.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import models.Stock;
import repositories.StockRepository;

public class StockRepositoryBlackBoxTest {

    @Test
    public void testAddStock() {
        StockRepository repository = new StockRepository();
        Date expiryDate = new Date(System.currentTimeMillis() + 86400000);
        Stock apple = new Stock("Apple", 100, 5.50, "Fruit", expiryDate);

        repository.addStock(apple);

        List<Stock> allStocks = repository.getAllStocks();
        assertEquals(1, allStocks.size());
        assertEquals("Apple", allStocks.get(0).getName());
        assertEquals(expiryDate, allStocks.get(0).getExpiryDate());
    }

    @Test
    public void testFindStockByName() {
        StockRepository repository = new StockRepository();
        Date expiryDate = new Date(System.currentTimeMillis() + 86400000);
        Stock apple = new Stock("Apple", 100, 5.50, "Fruit", expiryDate);

        repository.addStock(apple);

        Optional<Stock> foundStock = repository.findStockByName("Apple");
        assertTrue(foundStock.isPresent());
        assertEquals("Apple", foundStock.get().getName());
        assertEquals(expiryDate, foundStock.get().getExpiryDate());

        Optional<Stock> notFoundStock = repository.findStockByName("NonExistent");
        assertFalse(notFoundStock.isPresent());
    }

    @Test
    public void testFindStocksByCategory() {
        StockRepository repository = new StockRepository();
        Date expiryDate1 = new Date(System.currentTimeMillis() + 86400000);
        Date expiryDate2 = new Date(System.currentTimeMillis() + 172800000); 
        Date expiryDate3 = new Date(System.currentTimeMillis() + 259200000);

        Stock apple = new Stock("Apple", 100, 5.50, "Fruit", expiryDate1);
        Stock banana = new Stock("Banana", 50, 2.50, "Fruit", expiryDate2);
        Stock laptop = new Stock("Laptop", 10, 299.99, "Electronics", expiryDate3);

        repository.addStock(apple);
        repository.addStock(banana);
        repository.addStock(laptop);

        List<Stock> fruits = repository.findStocksByCategory("Fruit");
        assertEquals(2, fruits.size());
        assertTrue(fruits.contains(apple));
        assertTrue(fruits.contains(banana));

        List<Stock> electronics = repository.findStocksByCategory("Electronics");
        assertEquals(1, electronics.size());
        assertTrue(electronics.contains(laptop));

        List<Stock> nonExistentCategory = repository.findStocksByCategory("Toys");
        assertTrue(nonExistentCategory.isEmpty());
    }

    @Test
    public void testRemoveStock() {
        StockRepository repository = new StockRepository();
        Date expiryDate = new Date(System.currentTimeMillis() + 86400000);
        Stock apple = new Stock("Apple", 100, 5.50, "Fruit", expiryDate);
        repository.addStock(apple);

        boolean removed = repository.removeStock("Apple", 50);
        assertTrue(removed);
        assertEquals(50, apple.getQuantity());

        boolean notRemoved = repository.removeStock("NonExistent", 10);
        assertFalse(notRemoved);
    }

    @Test
    public void testExpiredStocks() {
        StockRepository repository = new StockRepository();
        Date pastDate = new Date(System.currentTimeMillis() - 86400000);
        Date futureDate = new Date(System.currentTimeMillis() + 86400000);

        Stock expiredApple = new Stock("ExpiredApple", 10, 3.00, "Fruit", pastDate);
        Stock freshBanana = new Stock("Banana", 20, 2.00, "Fruit", futureDate);

        repository.addStock(expiredApple);
        repository.addStock(freshBanana);

        List<Stock> allStocks = repository.getAllStocks();
        assertEquals(2, allStocks.size());

        assertTrue(expiredApple.isExpired());
        assertFalse(freshBanana.isExpired());
    }
}