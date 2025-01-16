# Stock Control System - Group no - 13

The **Stock Control System** is a Java-based application designed to manage stock inventory efficiently. It includes features such as user authentication, stock addition/removal, searching and filtering stocks, inventory value calculation, and management of expired stocks.

## Contributors

- Yash Kapure
- Harshwardhan Sonawane
- Alex Sibbick
- Gabrial Knight
- Shreyash Wankhade

## Project Structure

```
Stock-Control-System
├── src
│   ├── models
│   │   ├── Stock.java
│   │   ├── User.java
│   │   └── UserStocks.java
│   ├── repositories
│   │   ├── StockRepository.java
│   │   └── UserRepository.java
│   ├── resources
│   │   └── stocks.json
│   ├── services
│   │   ├── AuthService.java
│   │   └── StockService.java
│   ├── stockcontrol
│   │   └── StockControlApplication.java
│   ├── testcases
│   │   ├── blackbox
│   │   │   ├── repositories
│   │   │   ├── service
│   │   │   └── utils
│   │   ├── random
│   │   │   ├── repositories
│   │   │   ├── services
│   │   │   │   ├── AuthServiceRandomTest.java
│   │   │   │   └── StockServiceRandomTest.java
│   │   │   └── utils
│   │   │       └── JsonUtilRandomTest.java
│   │   ├── stockcontrol
│   │   ├── whitebox
│   │   │   ├── repositories
│   │   │   ├── services
│   │   │   └── utils
│   └── utils
├── Referenced Libraries
├── JUnit 5
├── README.md
└── test.json
```

## Features

### User Authentication

- **Login**: Authenticate with a username and password.
- **Register**: Create a new account.

### Data Persistence

- Users are stored in a JSON file (`users.json`) located in the `resources` directory.

### Stock Management

- **Add Stock**: Add a new stock item with details like name, quantity, price, category, and expiry date.
- **Remove Stock**: Delete a stock item by name.
- **Update Stock**: Modify stock quantity, price, or category.
- **View Stock**: Display all stock items in a tabular format.
- **Search or Filter Stocks**:
  - Search by name.
  - Filter by category.
- **Calculate Total Inventory Value**: Compute the total value of all stocks.
- **Remove Expired Stocks**: Automatically remove stocks past their expiry date.

### Data Persistence

- Stocks are stored in a JSON file (`stocks.json`) located in the `resources` directory.

### Testing

- **Blackbox Testing**: Test cases for repository and service functionality without internal knowledge of implementation.
- **Whitebox Testing**: Test cases designed with internal logic in mind.

## File Descriptions

### Models

- **`Stock.java`**: Represents a stock item with attributes like name, quantity, price, category, and expiry date.
- **`User.java`**: Represents a user with attributes like username and password.
- **`UserStocks.java`**: Links users to their stock inventory.

### Repositories

- **`StockRepository.java`**: Handles CRUD operations for stocks.
- **`UserRepository.java`**: Handles user data operations.

### Resources

- **`users.json`**: JSON file used to store users login credentials
- **`stocks.json`**: JSON file used to store user-specific stock data.

### Services

- **`AuthService.java`**: Manages user authentication.
- **`StockService.java`**: Manages stock operations (e.g., add, remove, update).

### Stock Control

- **`StockControlApplication.java`**: Entry point for the application. Manages user interaction and calls services.

#### Flow and Steps for Running Cases in `StockControlApplication.java`

1. **Start the Application**:

   - Run the `StockControlApplication.java` file.
   - The user is prompted to either log in or register.

2. **Login or Register**:

   - If the user selects **Login**, they must provide their username and password.
   - If the user selects **Register**, they create a new account by entering their username and password.

3. **Main Menu**:

   - After login, the main menu is displayed with the following options:
     ```
     1. Add stock
     2. Remove stock
     3. View stock
     4. Calculate total inventory value
     5. Search or filter stocks
     6. Update stock
     7. Remove expired stocks
     8. Logout
     ```

4. **Running Each Case**:

   - **Add Stock**:

     - Enter details such as name, quantity, price, category, and expiry date.
     - The application checks for duplicates before adding the stock.

   - **Remove Stock**:

     - Enter the stock name to delete the specific item.

   - **View Stock**:

     - All stocks are displayed in a formatted table.
     - If no stocks exist, the user is informed.

   - **Calculate Total Inventory Value**:

     - The system calculates and displays the total value of the inventory by summing up the values of all stocks.

   - **Search or Filter Stocks**:

     - Search stocks by name or filter them by category.
     - Relevant stocks are displayed or a message is shown if no results are found.

   - **Update Stock**:

     - Select a stock to update its quantity, price, or category.
     - The updated stock details are saved and displayed.

   - **Remove Expired Stocks**:

     - The system removes all stocks past their expiry date.
     - A message confirms the removal.

   - **Logout**:
     - Ends the current session and returns to the login/register screen.

### Test Cases

- **`blackbox`**: Tests focusing on application outputs given specific inputs.
- **`whitebox`**: Tests designed based on internal logic of methods.
- **Utilities**: Helper methods for testing.

### Utilities

- Contains shared utility classes and functions used across the application.

## Prerequisites

- Java SE 8 or higher
- JSON library (e.g., Jackson)
- JUnit 5 for testing

## How to Run the Application

1. Clone the repository to your local machine.
2. Open the project in your preferred IDE (e.g., IntelliJ IDEA, Eclipse).
3. Update the `stocks.json` file path in `StockService.java` to match your system.
4. Run `StockControlApplication.java` as a Java application.
5. Interact with the application through the command-line interface.

## Testing Instructions

### Flow and Steps for Running Test Cases

#### Blackbox Testing

1. Navigate to the `testcases.blackbox` directory.
2. Open the desired test file (e.g., `repositories` or `services`).
3. Run the test using JUnit 5 in your IDE.
4. Verify that outputs match expected results.

#### Whitebox Testing

1. Navigate to the `testcases.whitebox` directory.
2. Select a test file (e.g., `repositories`, `services`, or `utils`).
3. Execute the test cases in your IDE.
4. Check the internal logic against test outputs.

#### Utilities

1. Navigate to the `testcases.utils` directory.
2. Open helper test files (e.g., `JsonUtilRandomTest.java`).
3. Run the tests and validate utility functions.

### Sample Commands

- **Login/Register:**

  ```
  1. Login
  2. Register
  Enter your choice: 1
  Username: testuser
  Password: password123
  ```

- **Add Stock:**

  ```
  Enter stock name: Apple
  Enter stock quantity: 50
  Enter stock price: 1.50
  Enter stock category: Fruits
  Enter stock expiry date (yyyy-MM-dd): 2025-12-31
  ```

- **View Stock:**

  ```
  +----------------+----------+----------+---------------+---------------+
  | Name           | Quantity | Price    | Category      |Expiry Date    |
  +----------------+----------+----------+---------------+---------------+
  | Apple          | 50       | £1.50    | Fruits        | Sat Jan 25    |
  +----------------+----------+----------+---------------+---------------+
  ```
