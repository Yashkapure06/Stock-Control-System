package testcases.stockcontrol;

import models.Stock;
import models.User;
import org.junit.jupiter.api.*;
import repositories.UserRepository;
import services.AuthService;
import services.StockService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class StockControlApplicationTest {

    private UserRepository userRepository;
    private AuthService authService;
    private StockService stockService;
    private final String username = "testUser";

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
        authService = new AuthService(userRepository);
        stockService = new StockService();
    }


//    @Test
//    @DisplayName("Test adding stock")
//    void testAddStock() {
//        String stockName = "Apple";
//        int stockQuantity = 50;
//        double stockPrice = 2.5;
//        String stockCategory = "Fruit";
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DATE, 10); // 10 days from today
//        Date expiryDate = calendar.getTime();
//
//        Stock newStock = new Stock(stockName, stockQuantity, stockPrice, stockCategory, expiryDate);
//
//        stockService.addStockForUser(username, newStock);
//
//        List<Stock> userStocks = stockService.getStocksForUser(username);
//        Stock addedStock = userStocks.get(0);
//        assertEquals(stockName, addedStock.getName());
//    }

    @Test
    @DisplayName("Test searching stocks by category")
    void testSearchStocksByCategory() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 10); // 10 days from today
        Date expiryDate = calendar.getTime();

        stockService.addStockForUser(username, new Stock("Apple", 50, 2.5, "Fruit", expiryDate));
        stockService.addStockForUser(username, new Stock("Carrot", 20, 1.0, "Vegetable", expiryDate));

        List<Stock> filteredStocks = stockService.filterStocksByCategory(username, "Fruit");

        assertEquals("Apple", filteredStocks.get(0).getName());
    }

//    @Test
//    @DisplayName("Test viewing all stocks")
//    void testViewStocks() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DATE, 10); // 10 days from today
//        Date expiryDate = calendar.getTime();
//
//        stockService.addStockForUser(username, new Stock("Apple", 50, 2.5, "Fruit", expiryDate));
//        stockService.addStockForUser(username, new Stock("Orange", 30, 1.5, "Fruit", expiryDate));
//
//        List<Stock> retrievedStocks = stockService.getStocksForUser(username);
//
//        assertEquals("Apple", retrievedStocks.get(0).getName());
//        assertEquals("Orange", retrievedStocks.get(2).getName());
//    }
}