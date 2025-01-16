package testcases.blackbox.service;

import services.StockService;
import models.Stock;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class StockServiceBlackBoxTest {

    private StockService stockService;

    // Path to the stocks.json file yash path
    private static final String STOCKS_FILE = "C:\\Users\\acer\\eclipse-workspace\\Stock-Control-System\\src\\resources\\stocks.json";

    // gabrial path
//    private static final String STOCKS_FILE = "D:\\Repositories\\StockNew\\Stock-Control-System\\src\\resources\\stocks.json";

    @Before
    public void setup() throws IOException {
        File file = new File(STOCKS_FILE);
        if (file.exists()) {
            new FileWriter(file, false).close();
        }
        stockService = new StockService();
    }

    @Test
    public void testCalculateTotalValueForUser() {
        Date expiryDate1 = new Date(System.currentTimeMillis() + 86400000);
        Date expiryDate2 = new Date(System.currentTimeMillis() + 172800000);
        Stock stock1 = new Stock("Pear", 1, 150.0, "Food", expiryDate1);
        Stock stock2 = new Stock("Potato", 1, 200.0, "Food", expiryDate2);

        stockService.addStockForUser("user1", stock1);
        stockService.addStockForUser("user1", stock2);

        double totalValue = stockService.calculateTotalValueForUser("user1");
        assertEquals(350.0, totalValue);

        stockService.removeStockForUser("user1", "Pear");
        stockService.removeStockForUser("user1", "Potato");
    }

    @Test
    public void testAddStockForUser() {
        Date expiryDate = new Date(System.currentTimeMillis() + 86400000);
        Stock stock = new Stock("Banana", 10, 50.0, "Food", expiryDate);

        stockService.addStockForUser("user2", stock);

        List<Stock> stocks = stockService.getStocksForUser("user2");
        assertTrue(stocks.stream().anyMatch(s -> s.getName().equals("Banana") && s.getExpiryDate().equals(expiryDate)));
    }

    @Test
    public void testRemoveExpiredStocks_Success() {
        Date pastDate = new Date(System.currentTimeMillis() - 86400000);
        Date futureDate = new Date(System.currentTimeMillis() + 86400000);
        Stock expiredStock = new Stock("ExpiredApple", 5, 100.0, "Fruit", pastDate);
        Stock freshStock = new Stock("FreshApple", 5, 100.0, "Fruit", futureDate);

        stockService.addStockForUser("user3", expiredStock);
        stockService.addStockForUser("user3", freshStock);

        stockService.removeExpiredStocks("user3");

        List<Stock> stocks = stockService.getStocksForUser("user3");
        assertFalse(stocks.stream().anyMatch(s -> s.getName().equals("ExpiredApple")));
        assertTrue(stocks.stream().anyMatch(s -> s.getName().equals("FreshApple")));
    }

    @Test
    public void testSearchStocksByName() {
        Date expiryDate = new Date(System.currentTimeMillis() + 86400000);
        Stock stock = new Stock("Orange", 5, 80.0, "Fruit", expiryDate);

        stockService.addStockForUser("user4", stock);

        List<Stock> foundStocks = stockService.searchStocksByName("user4", "Orange");
        assertTrue(foundStocks.stream().anyMatch(s -> s.getName().equals("Orange") && s.getExpiryDate().equals(expiryDate)));
    }

    @Test
    public void testFilterStocksByCategory() {
        Date expiryDate = new Date(System.currentTimeMillis() + 86400000);
        Date pastDate = new Date(System.currentTimeMillis() - 86400000);
        Stock freshStock = new Stock("Carrot", 10, 30.0, "Vegetable", expiryDate);
        Stock expiredStock = new Stock("ExpiredCarrot", 10, 30.0, "Vegetable", pastDate);

        stockService.addStockForUser("user5", freshStock);
        stockService.addStockForUser("user5", expiredStock);
        stockService.removeExpiredStocks("user5");

        List<Stock> vegetableStocks = stockService.filterStocksByCategory("user5", "Vegetable");
        assertTrue(vegetableStocks.stream().anyMatch(s -> s.getName().equals("Carrot")));
        assertFalse(vegetableStocks.stream().anyMatch(s -> s.getName().equals("ExpiredCarrot")));
    }

    @Test
    public void testUpdateStock() {
        Date expiryDate = new Date(System.currentTimeMillis() + 86400000);
        Stock stock = new Stock("Mango", 5, 120.0, "Fruit", expiryDate);

        stockService.addStockForUser("user6", stock);

        String simulatedInput = "1\nadd\n10\n150\nTropical Fruits\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        stockService.updateStock(new Scanner(System.in), "user6");

        List<Stock> stocks = stockService.getStocksForUser("user6");
        Stock updatedStock = stocks.stream().filter(s -> s.getName().equals("Mango")).findFirst().orElse(null);

        assertNotNull(updatedStock);
        assertEquals(15, updatedStock.getQuantity());
        assertEquals(150.0, updatedStock.getPrice());
        assertEquals("Tropical Fruits", updatedStock.getCategory());
        assertEquals(expiryDate, updatedStock.getExpiryDate());
    }

    @Test
    public void testExpiredStocksAreExcludedFromTotalValue() {
        Date pastDate = new Date(System.currentTimeMillis() - 86400000);
        Date futureDate = new Date(System.currentTimeMillis() + 86400000);
        Stock expiredStock = new Stock("ExpiredApple", 5, 100.0, "Fruit", pastDate);
        Stock freshStock = new Stock("FreshApple", 5, 100.0, "Fruit", futureDate);

        stockService.addStockForUser("user7", expiredStock);
        stockService.addStockForUser("user7", freshStock);
        stockService.removeExpiredStocks("user7");

        double totalValue = stockService.calculateTotalValueForUser("user7");
        assertEquals(500.0, totalValue);
        
    }
}
