package testcases.stockcontrol;

import models.Stock;
import models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import repositories.StockRepository;
import repositories.UserRepository;
import services.AuthService;
import services.StockService;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class StockControlApplicationTest {

    private StockService stockService;
    private AuthService authService;
    private UserRepository userRepository;
    private StockRepository stockRepository;
    private String username; 

    @BeforeEach
    public void setUp() {
        userRepository = new UserRepository();
        authService = new AuthService(userRepository);
        stockService = new StockService();
        
        username = "user" + new Random().nextInt(1000); 

        stockService.addStockForUser(username, new Stock("Dumbbells", 10, 50.0, "Gym Equipment", null));
        stockService.addStockForUser(username, new Stock("Treadmill", 2, 500.0, "Cardio Equipment", null));
        stockService.addStockForUser(username, new Stock("Yoga Mat", 5, 20.0, "Accessories", null));
    }
    


    @Test
    public void testLoginInvalidEmail() {
        String simulatedInput = "invalid@example.com\npassword123\n";
        Scanner scanner = new Scanner(simulatedInput);

        User loggedInUser = authService.login(scanner);
        assertNull(loggedInUser); 
    }

    @Test
    public void testLoginInvalidPassword() {
        User user = new User("test@example.com", "testuser", "password123");
        userRepository.saveUser(user);

        String simulatedInput = "test@example.com\nwrongpassword\n";
        Scanner scanner = new Scanner(simulatedInput);

        User loggedInUser = authService.login(scanner);
        assertNull(loggedInUser); 
    }

    @Test
    public void testLoginEmptyFields() {
        String simulatedInput = "\n\n";
        Scanner scanner = new Scanner(simulatedInput);

        User loggedInUser = authService.login(scanner);
        assertNull(loggedInUser); 
    }



    @Test
    public void testRegisterWithDuplicateEmail() {
        userRepository.saveUser(new User("test@example.com", "testuser", "password123"));

        String simulatedInput = "test@example.com\nduplicateUser\npassword123\n";
        Scanner scanner = new Scanner(simulatedInput);

        User registeredUser = authService.register(scanner);
        assertNull(registeredUser); 
    }

    @Test
    public void testRegisterWithEmptyFields() {
        String simulatedInput = "\n\n\n";
        Scanner scanner = new Scanner(simulatedInput);

        User registeredUser = authService.register(scanner);
        assertNull(registeredUser);
    }

    @Test
    public void testAddStock() {
        User loggedInUser = new User("testuser", "password", "testuser@example.com");
        Stock stock = new Stock("TestStock", 100, 50.0, "Category1", new Date());
        stockService.addStockForUser(loggedInUser.getUsername(), stock);

        List<Stock> stocks = stockService.getStocksForUser(loggedInUser.getUsername());
    }

    @Test
    public void testRemoveStock() {
        User loggedInUser = new User("testuser", "password", "testuser@example.com");
        Stock stock = new Stock("TestStock", 100, 50.0, "Category1", new Date());
        stockService.addStockForUser(loggedInUser.getUsername(), stock);

        stockService.removeStockForUser(loggedInUser.getUsername(), "TestStock");

        List<Stock> stocks = stockService.getStocksForUser(loggedInUser.getUsername());
    }

    @Test
    public void testUpdateStock() {
        User loggedInUser = new User("testuser", "password", "testuser@example.com");
        
        Stock stock = new Stock("TestStock", 100, 50.0, "Category1", new Date());
        stockService.addStockForUser(loggedInUser.getUsername(), stock);

        stock.addQuantity(200); 
        stock.setPrice(75.0);   
        stockService.addStockForUser(loggedInUser.getUsername(), stock); 

        Stock updatedStock = stockService.getStocksForUser(loggedInUser.getUsername()).get(0);

    }


    @Test
    public void testViewStocks() {
        User loggedInUser = new User("testuser", "password", "testuser@example.com");
        Stock stock1 = new Stock("Stock1", 100, 50.0, "Category1", new Date());
        Stock stock2 = new Stock("Stock2", 50, 20.0, "Category2", new Date());

        stockService.addStockForUser(loggedInUser.getUsername(), stock1);
        stockService.addStockForUser(loggedInUser.getUsername(), stock2);

        List<Stock> stocks = stockService.getStocksForUser(loggedInUser.getUsername());
        assertEquals(2, stocks.size());
        assertTrue(stocks.stream().anyMatch(s -> s.getName().equals("Stock1")));
        assertTrue(stocks.stream().anyMatch(s -> s.getName().equals("Stock2")));
    }

    @Test
    public void testRemoveExpiredStocks() {
        User loggedInUser = new User("testuser", "password", "testuser@example.com");
        Stock expiredStock = new Stock("ExpiredStock", 50, 30.0, "Category1", new Date(0));
        Stock validStock = new Stock("ValidStock", 100, 50.0, "Category2", new Date());

        stockService.addStockForUser(loggedInUser.getUsername(), expiredStock);
        stockService.addStockForUser(loggedInUser.getUsername(), validStock);

        stockService.removeExpiredStocks(loggedInUser.getUsername());

        List<Stock> stocks = stockService.getStocksForUser(loggedInUser.getUsername());
    }

    @Test
    public void testTotalInventoryValue() {
        User loggedInUser = new User("testuser", "password", "testuser@example.com");
        Stock stock1 = new Stock("Stock1", 100, 50.0, "Category1", new Date());
        Stock stock2 = new Stock("Stock2", 50, 20.0, "Category2", new Date());

        stockService.addStockForUser(loggedInUser.getUsername(), stock1);
        stockService.addStockForUser(loggedInUser.getUsername(), stock2);

        double totalValue = stockService.calculateTotalValueForUser(loggedInUser.getUsername());
    }

    @Test
    void testCalculateTotalValueForRandomUser() {
        double expectedValue = (10 * 50.0) + (2 * 500.0) + (5 * 20.0);
        double actualValue = stockService.calculateTotalValueForUser(username);

        assertEquals(expectedValue, actualValue, 0.01);
    }

}