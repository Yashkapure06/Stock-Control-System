package testcases.blackbox.service;

import services.StockService;
import models.Stock;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class StockServiceBlackBoxTest {

    private StockService stockService;

    @Test
    public void testCalculateTotalValueForUser() {
        stockService = new StockService();
        Stock stock1 = new Stock("Laptop", 1, 150.0, "Electronics");
        Stock stock2 = new Stock("Smartphone", 1, 200.0, "Electronics");

        stockService.addStockForUser("user1", stock1);
        stockService.addStockForUser("user1", stock2);

        double totalValue = stockService.calculateTotalValueForUser("user1");
        assertEquals(350.0, totalValue);
        stockService.removeStockForUser("user1", "Laptop");
        stockService.removeStockForUser("user1", "Smartphone");
    }
    


    @Test
    public void testRemoveStockForUser_NonExistingUser() {
        stockService = new StockService();
        stockService.removeStockForUser("nonExistentUser", "Tablet");

        // Ensure no exception occurs and no unintended side effects
        List<Stock> stocks = stockService.getStocksForUser("nonExistentUser");
        assertTrue(stocks.isEmpty());
    }



    @Test
    public void testAddStockForUser_DuplicateStock_Failure() {
        Stock stock1 = new Stock("Laptop", 5, 150.0, "Electronics");
        Stock stock2 = new Stock("Laptop", 3, 145.0, "Electronics");

        stockService = new StockService();
        stockService.addStockForUser("user2", stock1);
        stockService.addStockForUser("user2", stock2);

        List<Stock> stocks = stockService.getStocksForUser("user2");
        long count = stocks.stream().filter(s -> s.getName().equals("Laptop")).count();
        assertEquals(1, count); // Duplicate stock should not be added
    }

    @Test
    public void testRemoveStockForUser_ExistingStock_Success() {


        Stock stock = new Stock("Laptop", 5, 150.0, "Electronics");

        stockService = new StockService();
        stockService.addStockForUser("user3", stock);
        stockService.removeStockForUser("user3", "Laptop");

        List<Stock> stocks = stockService.getStocksForUser("user3");
        assertFalse(stocks.stream().anyMatch(s -> s.getName().equals("Laptop")));
    }

    @Test
    public void testRemoveStockForUser_NonExistingStock_NoEffect() {

        Stock stock = new Stock("Laptop", 5, 150.0, "Electronics");

        stockService = new StockService();
        stockService.addStockForUser("user4", stock);
        stockService.removeStockForUser("user4", "Smartphone");

        List<Stock> stocks = stockService.getStocksForUser("user4");
        assertTrue(stocks.stream().anyMatch(s -> s.getName().equals("Laptop")));

    }

    @Test
    public void testSearchStocksByName_Found() {


        Stock stock = new Stock("Laptop", 5, 150.0, "Electronics");

        stockService = new StockService();
        stockService.addStockForUser("user5", stock);

        List<Stock> foundStocks = stockService.searchStocksByName("user5", "Laptop");
        assertTrue(foundStocks.stream().anyMatch(s -> s.getName().equals("Laptop")));


    }

    @Test
    public void testFilterStocksByCategory_Found() {

        Stock stock1 = new Stock("Laptop", 5, 150.0, "Electronics");
        Stock stock2 = new Stock("Smartphone", 3, 200.0, "Electronics");

        stockService = new StockService();
        stockService.addStockForUser("user6", stock1);
        stockService.addStockForUser("user6", stock2);

        List<Stock> techStocks = stockService.filterStocksByCategory("user6", "Electronics");
        assertTrue(techStocks.stream().allMatch(s -> s.getCategory().equals("Electronics")));

    }

    @Test
    public void testHandleSearchOrFilter_SearchByName() {
        stockService = new StockService();
        Stock stock = new Stock("Smartphone", 3, 200.0, "Electronics");
        stockService.addStockForUser("user7", stock);

        // Simulate user choice to search by name
        String simulatedInput = "1\nSmartphone\n";
        InputStream stdin = System.in;
        try {
            System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
            StockService.handleSearchOrFilter(new Scanner(System.in), stockService, "user7");
        } finally {
            System.setIn(stdin);
        }
        // Validate that no exception occurs (or capture stdout for assertions)
    }


    @Test
    public void testFilterStocksByCategory_NotFound() {

        Stock stock1 = new Stock("Laptop", 5, 150.0, "Electronics");
        Stock stock2 = new Stock("Smartphone", 3, 200.0, "Electronics");

        stockService = new StockService();
        stockService.addStockForUser("user8", stock1);
        stockService.addStockForUser("user8", stock2);

        List<Stock> healthStocks = stockService.filterStocksByCategory("user1", "Healthcare");
        assertTrue(healthStocks.isEmpty());

    }
    
    @Test
    public void testAddStockForUser_NewStock_Success() {

        Stock stock = new Stock("Laptop", 5, 150.0, "Electronics");

        stockService = new StockService();
        stockService.addStockForUser("user9", stock);

        List<Stock> stocks = stockService.getStocksForUser("user9");
        assertTrue(stocks.stream().anyMatch(s -> s.getName().equals("Laptop")));

    }
    
    @Test
    public void testUpdateStock_Success() {
        stockService = new StockService();
        Stock stock = new Stock("Laptop", 5, 150.0, "Electronics");
        stockService.addStockForUser("user10", stock);

        // Simulate user input for updating the stock
        String simulatedInput = "1\nadd\n3\n400\nComputing\n";
        InputStream stdin = System.in;
        try {
            System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
            stockService.updateStock(new Scanner(System.in), "user10");
        } finally {
            System.setIn(stdin);
        }

        List<Stock> stocks = stockService.getStocksForUser("user10");
        Stock updatedStock = stocks.stream()
                .filter(s -> s.getName().equals("Laptop"))
                .findFirst()
                .orElse(null);

        assertNotNull(updatedStock);
        assertEquals(8, updatedStock.getQuantity());
        assertEquals(400.0, updatedStock.getPrice());
        assertEquals("Computing", updatedStock.getCategory());
        stockService.removeStockForUser("user10", "Laptop");
        
    }
    
    
    @Test
    public void testSearchStocksByName_NoMatch() {
        stockService = new StockService();
        List<Stock> foundStocks = stockService.searchStocksByName("user11", "NonExistent");
        assertTrue(foundStocks.isEmpty());
    }

    @Test
    public void testFilterStocksByCategory_NoMatch() {
        stockService = new StockService();
        List<Stock> filteredStocks = stockService.filterStocksByCategory("user12", "NonExistent");
        assertTrue(filteredStocks.isEmpty());
    }

    @Test
    public void testCalculateTotalValueForUser_NoStocks() {
        stockService = new StockService();
        double totalValue = stockService.calculateTotalValueForUser("user13");
        assertEquals(0.0, totalValue);
    }


}
