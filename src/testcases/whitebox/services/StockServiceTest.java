package testcases.whitebox.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Date;
import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import models.Stock;
import services.StockService;

class StockServiceTest {

    private StockService stockService;

    @BeforeEach
    void setup() {
        stockService = new StockService();
    }

    @Test
    void testAddStockForNewUser() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 10); 
        Date expiryDate = calendar.getTime();

        Stock newStock = new Stock("StockA", 10, 100.0, "CategoryA", expiryDate);

        stockService.addStockForUser("newUser", newStock);

        List<Stock> userStocks = stockService.getStocksForUser("newUser");
        assertEquals(1, userStocks.size(), "User should have one stock.");
        assertEquals("StockA", userStocks.get(0).getName(), "Stock name should match.");
        assertEquals("CategoryA", userStocks.get(0).getCategory(), "Stock category should match.");
        assertEquals(10, userStocks.get(0).getQuantity(), "Stock quantity should match.");
    }

    @Test    
    void testCalculateTotalInventoryValue() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7); // 7 days from today
        Date expiryDate1 = calendar.getTime();

        calendar.add(Calendar.DATE, 5); // 12 days from today
        Date expiryDate2 = calendar.getTime();

        stockService.addStockForUser("testUser", new Stock("StockA", 10, 100.0, "CategoryA", expiryDate1));
        stockService.addStockForUser("testUser", new Stock("StockB", 5, 200.0, "CategoryB", expiryDate2));

        double totalValue = stockService.calculateTotalValueForUser("testUser");

        assertTrue(totalValue == 2000.0, "Total inventory value should be calculated correctly.");
    }

    @Test
    void testGetStocksForNonExistingUser() {
        List<Stock> stocks = stockService.getStocksForUser("nonExistentUser");
        assertTrue(stocks.isEmpty(), "No stocks should be returned for a non-existing user.");
    }

    @Test
    void testIsExpired() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1); // 1 day ago
        Date pastExpiryDate = calendar.getTime();

        Stock expiredStock = new Stock("ExpiredStock", 5, 50.0, "CategoryC", pastExpiryDate);

        assertTrue(expiredStock.isExpired(), "Stock should be marked as expired.");
    }

    @Test
    public void testSearchStocksByName() {
        StockService stockService = new StockService();
        String username = "test_user";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7);
        Date expiryDate1 = calendar.getTime();
        stockService.addStockForUser(username, new Stock("Apple", 10, 150.0, "Tech", expiryDate1));

        List<Stock> foundStocks = stockService.searchStocksByName(username, "Apple");

        assertFalse(foundStocks.isEmpty());
        assertEquals("Apple", foundStocks.get(0).getName());
    }

    @Test
    public void testRemoveStockForUser() {
        StockService stockService = new StockService();
        String username = "test_user";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7); 
        Date expiryDate1 = calendar.getTime();
        stockService.addStockForUser(username, new Stock("Apple", 10, 150.0, "Tech", expiryDate1));
        stockService.addStockForUser(username, new Stock("Microsoft", 5, 200.0, "Tech", expiryDate1));

        stockService.removeStockForUser(username, "Apple");

        List<Stock> remainingStocks = stockService.getStocksForUser(username);
        assertEquals("Microsoft", remainingStocks.get(0).getName());
    }

    @Test
    public void testFilterStocksByCategory() {
        StockService stockService = new StockService();
        String username = "test_user";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7); 
        Date expiryDate1 = calendar.getTime();

        stockService.addStockForUser(username, new Stock("Apple", 10, 150.0, "Tech", expiryDate1));
        stockService.addStockForUser(username, new Stock("Banana", 20, 50.0, "Food", expiryDate1));

        List<Stock> techStocks = stockService.filterStocksByCategory(username, "Tech");

        assertEquals("Microsoft", techStocks.get(0).getName());
    }


}