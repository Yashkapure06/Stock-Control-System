package testcases.random.services;

import models.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.StockService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StockServiceRandomTest {

    private StockService stockService;

    @BeforeEach
    public void setUp() {
        stockService = new StockService();
    }

    private Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException("Invalid date format: " + dateString, e);
        }
    }

    @Test
    public void testGetStocksForUser_NoStocks() {
        
        List<Stock> stocks = stockService.getStocksForUser("unknown_user");
        assertTrue(stocks.isEmpty(), "Stocks list should be empty for unknown user.");
    }

   
   
    

    @Test
    public void testRemoveStocksExpiringToday() {
        Stock expiringToday = new Stock("EXP_TODAY", 15, 100.0, "Daily", new Date());
        Stock validStock = new Stock("AAPL", 10, 150.0, "Technology", parseDate("2025-01-25"));

        stockService.addStockForUser("testuser", expiringToday);
        stockService.addStockForUser("testuser", validStock);

        stockService.removeExpiredStocks("testuser");

        List<Stock> stocks = stockService.getStocksForUser("testuser");
 }

    @Test
    public void testStocksNotExpiring() {
        
        Stock stock1 = new Stock("AAPL", 10, 150.0, "Technology", parseDate("2025-01-25"));
        Stock stock2 = new Stock("GOOGL", 2, 2800.0, "Technology", parseDate("2025-02-25"));

        stockService.addStockForUser("testuser", stock1);
        stockService.addStockForUser("testuser", stock2);

        stockService.removeExpiredStocks("testuser");

        List<Stock> stocks = stockService.getStocksForUser("testuser");
        assertEquals(2, stocks.size(), "Both stocks should remain as they are not expired.");
    }
}
