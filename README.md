# Stock Control System - Group no - 13

The _Stock Control System_ is a Java-based application designed to manage stock inventory efficiently. It includes features such as user authentication, stock addition/removal, searching and filtering stocks, inventory value calculation, and management of expired stocks.

## Contributors

- Yash Kapure
- Harshwardhan Sonawane
- Alex Sibbick
- Gabrial Knight
- Shreyash Wankhade

## Project Structure

Stock-Control-System

```

├── src
│ ├── models
│ │ ├── Stock.java
│ │ ├── User.java
│ │ └── UserStocks.java
│ ├── repositories
│ │ ├── StockRepository.java
│ │ └── UserRepository.java
│ ├── resources
│ │ └── stocks.json
| |  |── users.json
│ ├─ services
│ │ ├── AuthService.java
│ │ └── StockService.java
│ ├── stockcontrol
│ │ └── StockControlApplication.java
│ ├── testcases
│ │ ├── blackbox
│ │ │ ├── repositories
│ │ │ ├── service
│ │ │ └── utils
│ │ ├── random
│ │ │ ├── repositories
│ │ │ ├── services
│ │ │ │ ├── AuthServiceRandomTest.java
│ │ │ │ └── StockServiceRandomTest.java
│ │ │ └── utils
│ │ │ └── JsonUtilRandomTest.java
│ │ ├── stockcontrol
│ │ ├── whitebox
│ │ │ ├── repositories
│ │ │ ├── services
│ │ │ └── utils
│ └── utils
├── Referenced Libraries
├── JUnit 5
├── README.md
└── test.json

```

## Features

### User Authentication

- _Login_: Authenticate with a username and password.
- _Register_: Create a new account.

### Data Persistence

- Users are stored in a JSON file (users.json) located in the resources directory.

### Stock Management

- _Add Stock_: Add a new stock item with details like name, quantity, price, category, and expiry date.
- _Remove Stock_: Delete a stock item by name.
- _Update Stock_: Modify stock quantity, price, or category.
- _View Stock_: Display all stock items in a tabular format.
- _Search or Filter Stocks_:
  - Search by name.
  - Filter by category.
- _Calculate Total Inventory Value_: Compute the total value of all stocks.
- _Remove Expired Stocks_: Automatically remove stocks past their expiry date.

### Data Persistence

- Stocks are stored in a JSON file (stocks.json) located in the resources directory.

### Testing

- _Blackbox Testing_: Test cases for repository and service functionality without internal knowledge of implementation.
- _Whitebox Testing_: Test cases designed with internal logic in mind.

## File Descriptions

### Models

- _Stock.java_: Represents a stock item with attributes like name, quantity, price, category, and expiry date.
- _User.java_: Represents a user with attributes like username and password.
- _UserStocks.java_: Links users to their stock inventory.

### Repositories

- _StockRepository.java_: Handles CRUD operations for stocks.
- _UserRepository.java_: Handles user data operations.

### Resources

**Note** : Make sure to Change the path of these json files according to your system.

- _users.json_: JSON file used to store users login credentials
- _stocks.json_: JSON file used to store user-specific stock data.

### Services

- _AuthService.java_: Manages user authentication.
- _StockService.java_: Manages stock operations (e.g., add, remove, update).

### Stock Control

- _StockControlApplication.java_: Entry point for the application. Manages user interaction and calls services.

#### Flow and Steps for Running Cases in StockControlApplication.java

1. _Start the Application_:

   - Run the StockControlApplication.java file.
   - The user is prompted to either log in or register.

2. _Login or Register_:

   - If the user selects _Login_, they must provide their username and password.
   - If the user selects _Register_, they create a new account by entering their username and password.

3. _Main Menu_:

   - After login, the main menu is displayed with the following options:

     1. Add stock
     2. Remove stock
     3. View stock
     4. Calculate total inventory value
     5. Search or filter stocks
     6. Update stock
     7. Remove expired stocks
     8. Logout

4. _Running Each Case_:

   - _Add Stock_:

     - Enter details such as name, quantity, price, category, and expiry date.
     - The application checks for duplicates before adding the stock.

   - _Remove Stock_:

     - Enter the stock name to delete the specific item.

   - _View Stock_:

     - All stocks are displayed in a formatted table.
     - If no stocks exist, the user is informed.

   - _Calculate Total Inventory Value_:

     - The system calculates and displays the total value of the inventory by summing up the values of all stocks.

   - _Search or Filter Stocks_:

     - Search stocks by name or filter them by category.
     - Relevant stocks are displayed or a message is shown if no results are found.

   - _Update Stock_:

     - Select a stock to update its quantity, price, or category.
     - The updated stock details are saved and displayed.

   - _Remove Expired Stocks_:

     - The system removes all stocks past their expiry date.
     - A message confirms the removal.

   - _Logout_:
     - Ends the current session and returns to the login/register screen.

### Test Cases

- _blackbox_: Tests focusing on application outputs given specific inputs.
- _whitebox_: Tests designed based on internal logic of methods.
- _Utilities_: Helper methods for testing.

### Utilities

- Contains shared utility classes and functions used across the application.

## Prerequisites

- Java SE 8 or higher
- JSON library (e.g., Jackson)
- JUnit 5 for testing

## How to Run the Application

1. Clone the repository to your local machine.
2. Open the project in your preferred IDE (e.g., IntelliJ IDEA, Eclipse).
3. Update the stocks.json file path in StockService.java to match your system.
4. Run StockControlApplication.java as a Java application.
5. Interact with the application through the command-line interface.

## Testing Instructions

### Flow and Steps for Running Test Cases

#### Blackbox Testing

1. Navigate to the testcases.blackbox directory.
2. Open the desired test file (e.g., repositories or services).
3. Run the test using JUnit 5 in your IDE.
4. Verify that outputs match expected results.

#### Whitebox Testing

1. Navigate to the testcases.whitebox directory.
2. Select a test file (e.g., repositories, services, or utils).
3. Execute the test cases in your IDE.
4. Check the internal logic against test outputs.

#### Utilities

1. Navigate to the testcases.utils directory.
2. Open helper test files (e.g., JsonUtilRandomTest.java).
3. Run the tests and validate utility functions.

### Sample Commands

- _Login/Register:_

  1. Login
  2. Register
     Enter your choice: 1
     Username: testuser
     Password: password123

- _Add Stock:_

  Enter stock name: Apple
  Enter stock quantity: 50
  Enter stock price: 1.50
  Enter stock category: Fruits
  Enter stock expiry date (yyyy-MM-dd): 2025-12-31

- _View Stock:_

  +----------------+----------+----------+---------------+---------------+
  | Name | Quantity | Price | Category |Expiry Date |
  +----------------+----------+----------+---------------+---------------+
  | Apple | 50 | £1.50 | Fruits | Sat Jan 25 |
  +----------------+----------+----------+---------------+---------------+
