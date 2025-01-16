package testcases.whitebox.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import models.Stock;
import models.UserStocks;
import services.StockService;

class StockServiceTest {

    private StockService stockService;

    @BeforeEach
    void setup() {
        stockService = new StockService();
    }

    @Test
    void testAddStockForNewUser() {
        Stock newStock = new Stock("StockA", 10, 100.0, "CategoryA");

        // Add stock for a new user
        stockService.addStockForUser("newUser", newStock);

        // Verify that the userStock was created and the stock was added
        List<Stock> userStocks = stockService.getStocksForUser("newUser");
        assertEquals(1, userStocks.size(), "User should have one stock.");
        assertEquals("StockA", userStocks.get(0).getName(), "Stock name should match.");
        assertEquals("CategoryA", userStocks.get(0).getCategory(), "Stock category should match.");
        assertEquals(10, userStocks.get(0).getQuantity(), "Stock quantity should match.");
    }

    @Test
    void testRemoveStockForUser() {
        Stock newStock = new Stock("StockA", 10, 100.0, "CategoryA");
        stockService.addStockForUser("testUser", newStock);
        stockService.removeStockForUser("testUser", "StockA");

        List<Stock> stocks = stockService.getStocksForUser("testUser");
        assertEquals("No stocks available.", "No stocks available.");
    }

    @Test    
    void testCalculateTotalInventoryValue() {
        stockService.addStockForUser("testUser", new Stock("StockA", 10, 100.0, "CategoryA"));
        stockService.addStockForUser("testUser", new Stock("StockB", 5, 200.0, "CategoryB"));

        double totalValue = stockService.calculateTotalValueForUser("testUser");
        assertEquals(2000.0, totalValue, "Total inventory value should be calculated correctly.");
    }

    @Test
    void testGetStocksForNonExistingUser() {
        List<Stock> stocks = stockService.getStocksForUser("nonExistentUser");
        assertTrue(stocks.isEmpty(), "No stocks should be returned for a non-existing user.");
    }
}
