package testcases.random.services;

import models.Stock;
import models.UserStocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.StockService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StockServiceRandomTest {

    private StockService stockService;

    @BeforeEach
    public void setUp() {
        stockService = new StockService(); // Initialize StockService
    }

    @Test
    public void testGetStocksForUser_NoStocks() {
        List<Stock> stocks = stockService.getStocksForUser("unknown_user");
        assertTrue(stocks.isEmpty());
    }

    @Test
    public void testAddStockForUser() {
        Stock newStock = new Stock("AAPL", "Technology", 150.0, 10);
        stockService.addStockForUser("testuser", newStock);

        List<Stock> stocks = stockService.getStocksForUser("testuser");
        assertEquals(1, stocks.size());
        assertEquals("AAPL", stocks.get(0).getName());
    }

    @Test
    public void testAddDuplicateStockForUser() {
        Stock stock1 = new Stock("AAPL", "Technology", 150.0, 10);
        Stock stock2 = new Stock("AAPL", "Technology", 155.0, 5);

        stockService.addStockForUser("testuser", stock1);
        stockService.addStockForUser("testuser", stock2); // Duplicate stock

        List<Stock> stocks = stockService.getStocksForUser("testuser");
        assertEquals(1, stocks.size()); // Duplicate should not be added
    }

    @Test
    public void testRemoveStockForUser() {
        Stock stock = new Stock("AAPL", "Technology", 150.0, 10);
        stockService.addStockForUser("testuser", stock);

        stockService.removeStockForUser("testuser", "AAPL");
        List<Stock> stocks = stockService.getStocksForUser("testuser");
        assertTrue(stocks.isEmpty());
    }

    @Test
    public void testCalculateTotalValueForUser() {
        stockService.addStockForUser("testuser", new Stock("AAPL", "Technology", 150.0, 10));
        stockService.addStockForUser("testuser", new Stock("GOOGL", "Technology", 2800.0, 2));

        double totalValue = stockService.calculateTotalValueForUser("testuser");
        assertEquals(6100.0, totalValue, 0.01);
    }

    @Test
    public void testSearchStocksByName() {
        stockService.addStockForUser("testuser", new Stock("AAPL", "Technology", 150.0, 10));
        stockService.addStockForUser("testuser", new Stock("GOOGL", "Technology", 2800.0, 2));

        List<Stock> foundStocks = stockService.searchStocksByName("testuser", "AAPL");
        assertEquals(1, foundStocks.size());
        assertEquals("AAPL", foundStocks.get(0).getName());
    }

    @Test
    public void testFilterStocksByCategory() {
        stockService.addStockForUser("testuser", new Stock("AAPL", "Technology", 150.0, 10));
        stockService.addStockForUser("testuser", new Stock("WMT", "Retail", 140.0, 20));

        List<Stock> filteredStocks = stockService.filterStocksByCategory("testuser", "Technology");
        assertEquals(1, filteredStocks.size());
        assertEquals("AAPL", filteredStocks.get(0).getName());
    }

    @Test
    public void testRemoveExpiredStocks() {
        Stock validStock = new Stock("AAPL", "Technology", 150.0, 10);
        Stock expiredStock = new Stock("OLD", "Obsolete", 50.0, 5);
        expiredStock.setExpired(true);

        stockService.addStockForUser("testuser", validStock);
        stockService.addStockForUser("testuser", expiredStock);

        stockService.removeExpiredStocks("testuser");

        List<Stock> stocks = stockService.getStocksForUser("testuser");
        assertEquals(1, stocks.size());
        assertEquals("AAPL", stocks.get(0).getName());
    }
}
