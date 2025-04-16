# 🛒 E-commerce Order Processing System (Java Console App)

A simple console-based Java project for e-commerce order processing. It supports customer registration, product management, order placement, payment handling, and order tracking using multithreading. Data is stored in a remote Oracle database.

## 💡 Features

- **Customer Registration**
  - Name, email, mobile number, address, payment method
  - Auto-generated alphanumeric account number

- **Product Management**
  - Add products with name, category, price, and stock
  - Generates unique product ID

- **Order Placement**
  - Browse and order products
  - Order saved with status "New"

- **Order Processing**
  - Multithreaded check for new orders every 1 min
  - Updates order status based on stock and availability

- **Payment Handling**
  - Simulates payment success/failure
  - Updates order based on result

## 🛠️ Tech Stack

- Core Java
- JDBC
- Oracle Database
- Multithreading

## ✅ Notes

- Modular code using classes and interfaces
- Console output in table format
- Proper error handling and status messages
