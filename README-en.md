# 🎯 "Totolotek" Lottery System - A Java Simulator

This is an advanced simulator of the popular "Totolotek" number game, implemented in Java using object-oriented programming principles. The project simulates the full lifecycle of a lottery – from players purchasing tickets at collection points, through conducting draws, to calculating prize pools and paying out winnings.

## 🚀 Key Features

- **Lottery Headquarters**: Manages draws, finances, prize pools, and the network of collection points.
- **Collection Points**: Ticket sales points that act as intermediaries in transactions between players and the headquarters.
- **Tickets**: Unique, identifiable documents that grant participation in draws and the right to claim prizes.
- **Players**: Four different types of players with unique playing strategies:
  - **Minimalist**: Plays cautiously, buying one bet for a single draw.
  - **Random**: Dynamically buys a random number of tickets with a random number of bets.
  - **Constant Numbers**: Always plays with the same, favorite set of 6 numbers.
  - **Constant Form**: Uses a fixed, predefined form for regular purchases.
- **Draws**: Cyclical drawing of 6 out of 49 numbers.
- **Prize System**: Dynamic calculation of first, second, third, and fourth-tier prize pools, including rollovers.
- **State Budget**: Models the financial flows related to gaming taxes and subsidies.

## 🛠️ Technologies

- **Language**: Java 11+
- **Paradigm**: Object-Oriented Programming (OOP)
- **Testing**: JUnit 5
- **Build & Dependencies**: Maven (recommended)
- **Structure**: The project is divided into logical packages (`model`, `player`, `system`, etc.).

## ⚙️ Getting Started

### Prerequisites
- JDK 11 or newer
- Apache Maven (for dependency management and building the project)

### Steps
1.  **Clone the repository:**
    ```
    git clone <repository-url>
    cd <repository-name>
    ```
2.  **Build the project using Maven:**
    ```
    mvn clean install
    ```
3.  **Run the simulation:**
    The main `Main` class runs a full demonstration of the system. You can run it directly from your IDE or from the command line:
     ```bash
    chmod +x test.sh
    ./test.sh
    ```

## 🧪 Unit Tests

The project includes an extensive suite of unit tests written using **JUnit 5**, which verify the correct operation of key system components.

### Test Scope
- **Coupon Business Logic**: Correctness of identifier generation, price calculations, draw verification.
- **Financial Calculations**: Accuracy of calculating prize pools, minimum guaranteed amounts, and accumulation
- **Sales and Taxes**: Verification of transactions, tax collection, and budget updates.
- **Data Correctness**: Checking input data validation in Zaklad, Blankiet, and player classes.
- **Error Detection**: Tests checking system resilience to incorrect data (e.g., duplicate numbers, out-of-range values).

## 🏗️ Project Structure

The project is organized into logical packages to ensure high readability and ease of maintenance:

- `src/main/java`:
  - `model`: Core domain classes (`Ticket`, `Bet`, `Draw`).
  - `player`: The abstract `Player` class and its concrete implementations.
  - `system`: System management classes (`Headquarters`, `CollectionPoint`, `StateBudget`).
  - `Main.java`: The main executable class that demonstrates the simulation.
- `src/test/java`:
  - Unit tests for individual classes, mirroring the source code's package structure.

## ✍️ Author

Wiktor Gerałtowski
