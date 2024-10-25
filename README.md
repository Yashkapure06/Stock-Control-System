# Stock Control System

## Project Overview

The **Stock Control System** is a backend-only, terminal-based application designed to manage stock inventory. This project does not require a frontend, and all interactions will be done through the terminal. The application is organized into various packages that separate different responsibilities such as models, services, repositories, and utilities. Additionally, the test cases are placed in a dedicated package.

---

## Folder Structure

Here’s an outline of the current folder structure and the role of each package:

```
StockControlSystem/
│
├── src/
│   ├── models/                           # Data models representing stock objects
│   │   └── Stock.java
│   │
│   ├── repositories/                     # Handles data persistence, e.g., file I/O or database interaction
│   │   └── StockRepository.java
│   │
│   ├── services/                         # Business logic for stock management
│   │   └── StockService.java
│   │
│   ├── stockcontrol/                     # Main entry point and application control
│   │   └── StockControlApplication.java
│   │
│   ├── testcases/                        # Unit test cases for models, services, repositories, utils, and main application
│   │   ├── LoggingUtilTest.java
│   │   ├── StockControlApplicationTest.java
│   │   ├── StockRepositoryTest.java
│   │   ├── StockServiceTest.java
│   │   ├── StockTest.java
│   │   └── ValidationUtilTest.java
│   │
│   └── utils/                            # Utility functions for logging, validation, and other common operations
│       ├── LoggingUtil.java
│       └── ValidationUtil.java
│
└── README.md                             # Project description and usage instructions
```

---

## Testing Structure

For testing, create individual test classes for each key module. Recommended test files include:

- **models**
  - `StockTest.java`: Test basic functionalities of the `Stock` class, such as quantity management and price updates.
- **services**
  - `StockServiceTest.java`: Test stock operations such as adding, removing, and updating stock items.
- **repositories**
  - `StockRepositoryTest.java`: Test data persistence operations, ensuring data is saved and retrieved correctly.
- **utils**
  - `ValidationUtilTest.java`: Test validation functions for input data.
  - `LoggingUtilTest.java`: Test logging functionality, confirming logs are generated as expected.
- **StockControlApplicationTest.java**:
  - Test high-level application workflows and menu operations.

---

## Usage Instructions

1. **Setup**:

   - Clone the repository and import the project into Eclipse.

2. **Run the Application**:

   - Run `StockControlApplication.java` to start the terminal-based application.
   - Use the provided menu to add, remove, and view stock data.

3. **Testing**:

   - Unit tests are located in `testcases/`. Run them using JUnit to ensure functionality. you can find all the files over here

4. **Logging**:

   - Logs will be saved in the `logs/` folder, helping in debugging and tracking system activity.

5. **Backup**:
   - Periodic backups of stock data will be stored in the `backup/` folder. Ensure this folder is accessible.

---

## Contribution Guidelines

Each team member is assigned specific roles (services, repositories, utilities, etc.). Maintain clear, well-documented code to ensure ease of collaboration. Utilize Git for version control to manage changes effectively.

---

## License

This project is licensed under the MIT License.

---

## Authors

Group Members:

- **Yash Kapure**: To Do some task
- **Harshwardhan Sonawane**: To Do some task
- **Alex Sibbick**: To Do some task
- **Gabrial Knight**: To Do some task
- **Shreyas Wankhade**: To Do some task
