# Digital Wallet API

This is a **Digital Wallet Backend API** developed using **Java 21**, **Spring Boot**, and **PostgreSql Database**, containerized with **Docker** and managed through **Docker Compose**.  
An **API Gateway** is included to route requests to backend services.  
It allows customers and employees to manage wallets by creating wallets, depositing, withdrawing, and managing transactions.

---

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
- [Endpoints](#endpoints)
- [Notes](#notes)

---

## Features

- API Gateway support for routing
- Customer registration, login & password change
- Create Wallet (TRY, USD, EUR currencies)
- List Wallets
- Deposit Money (auto status handling: APPROVED/PENDING)
- Withdraw Money (with balance rules and settings)
- List Transactions
- Approve or Deny Transactions (PENDING → APPROVED/DENIED)


---

## Tech Stack

- Java 21
- Spring Boot 3.x
- Spring Cloud Gateway
- Spring Data JPA
- Spring Security
- PostgreSQL
- Docker & Docker Compose
- Maven

---

## Architecture

The project is structured as a multi-module Maven workspace, with three independently runnable Spring Boot applications:


- **gateway**
    - Acts as the single entry point for all HTTP traffic (port 8080).
    - Validates incoming JWT tokens on secured routes.
    - Routes requests to either the `auth` or `digitalwallet` service based on the URL path.

- **auth**
    - Handles user registration (`POST /customers`), login (`POST /customers/login`), and password changes (`PUT /customers/password`).
    - Issues JWT access tokens upon successful authentication.

- **digitalwallet**
    - Manages wallets and transactions: create/list wallets, deposit, withdraw, list/approve transactions.
    - Enforces business rules (e.g. PENDING vs. APPROVED logic, wallet feature flags).
    - Persists data in an PostgreSQL database.

### Request Flow

```text
[ Client ]
     ↓
[ API Gateway (gateway) ]  --– JWT validation
     ↓                    ↘
[ Auth Service ]            [ Wallet Service ]
     ↓                            ↓
  PostgreSQL DB              PostgreSQL DB

```
## Getting Started

This section explains how to set up and run the **Digital Wallet API** project locally using **Docker Compose**, **PostgreSQL**, and **Spring Boot**.

---

### Prerequisites

Before starting, make sure you have the following installed:

- [Java 21](https://adoptium.net/)
- [Maven 3.9+](https://maven.apache.org/)
- [Docker](https://www.docker.com/)

---

### Project Structure

The project is organized as a multi-module Maven workspace:


Each module is a standalone Spring Boot application that will run inside Docker containers.

---

### Clone the Repository

```bash
git clone https://github.com/eserefler/DigitalWallet.git

cd DigitalWallet

mvn clean install

Set up and start a PostgreSQL database container.

docker-compose up --build

This will build Docker images for:

Expose the following ports:

8080 → API Gateway (external access point)

8081 → Auth Service (internal)

8082 → Digital Wallet Service (internal)

5432 → PostgreSQL database

```

## Endpoints

All API requests must be sent through the **API Gateway** (`http://localhost:8080`).

---

### Customer Management Endpoints

| Method | Path                    | Description                           |
| ------ | ----------------------- | ------------------------------------- |
| POST   | `/customers`             | Register a new customer              |
| POST   | `/customers/login`       | Login and receive a JWT token         |
| PUT    | `/customers/password`    | Change the password of the authenticated customer |
| GET    | `/customers/{id}`        | Retrieve customer details by ID      |

---

### Wallet Management Endpoints

| Method | Path                                                              | Description                          |
| ------ | ----------------------------------------------------------------- | ------------------------------------ |
| POST   | `/wallets`                                                       | Create a new wallet                  |
| GET    | `/wallets`                                                       | List wallets  |
| PUT    | `/wallets/{walletId}/deposit`                                     | Deposit money into a wallet          |
| PUT    | `/wallets/{walletId}/withdraw`                                    | Withdraw money from a wallet         |
| GET    | `/wallets/{walletId}/transactions`                                | List transactions of a specific wallet |
| PUT    | `/wallets/{walletId}/transactions/{transactionId}/approve`        | Approve or deny a pending transaction |

---

### Notes

- 💸 **Amount Field Information**:
- The `amount` field is handled and stored as a `long` value.
- The **last two digits represent the decimal part**.
- Example:
  - `amount = 12345` → represents **123.45**
  - `amount = 500` → represents **5.00**
- Deposits and withdrawals behave differently depending on the amount (threshold: 100000).
- Wallet transactions must respect wallet settings like `activeForShopping` and `activeForWithdraw`.
-  **Swagger UI** is available at: 
- (http://localhost:8081/swagger-ui.html)
- (http://localhost:8082/swagger-ui.html) 📄
- You can view and test all API endpoints easily through the Swagger interface.
- **/customers/login** returns a JWT token that must be used in the `Authorization` header for protected endpoints.

Example Authorization Header after login:

```http
Authorization: Bearer <your-jwt-token>

