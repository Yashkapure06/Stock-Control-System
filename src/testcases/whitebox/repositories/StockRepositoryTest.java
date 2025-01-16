package testcases.whitebox.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import models.Stock;
import repositories.StockRepository;

class StockRepositoryTest {

    private StockRepository stockRepository;
    private SimpleDateFormat dateFormat;

    @BeforeEach
    void setUp() {
        stockRepository = new StockRepository();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Test
    void testAddStock() throws ParseException {
        Date expiryDate = dateFormat.parse("2025-01-16");
        Stock stock = new Stock("Apple", 100, 1.2, "Fruits", expiryDate);
        stockRepository.addStock(stock);

        List<Stock> allStocks = stockRepository.getAllStocks();
        assertEquals(1, allStocks.size(), "Stock list should contain one item.");
        assertEquals("Apple", allStocks.get(0).getName(), "Stock name should match the added stock.");
        assertEquals(expiryDate, allStocks.get(0).getExpiryDate(), "Expiry date should match the added stock.");
    }

    @Test
    void testFindStockByNameWhenStockExists() throws ParseException {
        Date expiryDate = dateFormat.parse("2025-01-16");
        Stock stock = new Stock("Apple", 100, 1.2, "Fruits", expiryDate);
        stockRepository.addStock(stock);

        Optional<Stock> foundStock = stockRepository.findStockByName("Apple");
        assertTrue(foundStock.isPresent(), "Stock should be found by name.");
        assertEquals("Apple", foundStock.get().getName(), "Found stock name should match.");
        assertEquals(expiryDate, foundStock.get().getExpiryDate(), "Found stock expiry date should match.");
    }

    @Test
    void testFindStocksByCategoryWhenCategoryExists() throws ParseException {
        Date expiryDate1 = dateFormat.parse("2025-01-16");
        Date expiryDate2 = dateFormat.parse("2025-02-01");
        Stock stock1 = new Stock("Apple", 100, 1.2, "Fruits", expiryDate1);
        Stock stock2 = new Stock("Banana", 200, 0.8, "Fruits", expiryDate2);
        stockRepository.addStock(stock1);
        stockRepository.addStock(stock2);

        List<Stock> fruits = stockRepository.findStocksByCategory("Fruits");
        assertEquals(2, fruits.size(), "Should return two stocks for the 'Fruits' category.");
    }

    @Test
    void testFindStocksByCategoryWhenCategoryDoesNotExist() {
        List<Stock> vegetables = stockRepository.findStocksByCategory("Vegetables");
        assertTrue(vegetables.isEmpty(), "Should return an empty list for a non-existent category.");
    }

    @Test
    void testRemoveStockWhenStockExistsAndQuantityIsSufficient() throws ParseException {
        Date expiryDate = dateFormat.parse("2025-01-16");
        Stock stock = new Stock("Apple", 100, 1.2, "Fruits", expiryDate);
        stockRepository.addStock(stock);

        boolean isRemoved = stockRepository.removeStock("Apple", 50);
        assertTrue(isRemoved, "Stock should be removed successfully.");
        assertEquals(50, stockRepository.findStockByName("Apple").get().getQuantity(),
                     "Remaining quantity should be updated.");
    }

    @Test
    void testRemoveStockWhenStockDoesNotExist() {
        boolean isRemoved = stockRepository.removeStock("NonExistent", 10);
        assertFalse(isRemoved, "Removing a non-existent stock should return false.");
    }

    @Test
    void testGetAllStocks() throws ParseException {
        Date expiryDate1 = dateFormat.parse("2025-01-16");
        Date expiryDate2 = dateFormat.parse("2025-02-01");
        Stock stock1 = new Stock("Apple", 100, 1.2, "Fruits", expiryDate1);
        Stock stock2 = new Stock("Banana", 200, 0.8, "Fruits", expiryDate2);
        stockRepository.addStock(stock1);
        stockRepository.addStock(stock2);

        List<Stock> allStocks = stockRepository.getAllStocks();
        assertEquals(2, allStocks.size(), "Should return all added stocks.");
        assertEquals(expiryDate1, allStocks.get(0).getExpiryDate(), "First stock expiry date should match.");
        assertEquals(expiryDate2, allStocks.get(1).getExpiryDate(), "Second stock expiry date should match.");
    }
}
