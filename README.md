# Leftover API

Leftover API is a personal finance backend system built with Java and Spring Boot. It helps users track income, pay periods, recurring expenses, transactions, and leftover balance summaries.

The project also includes a background notification worker service that handles asynchronous pay period summary generation using RabbitMQ messaging, scheduled jobs, and Redis caching.

## Tech Stack

- Java
- Spring Boot
- Spring Data JPA
- Spring Security
- MySQL
- Redis
- RabbitMQ
- Maven
- REST APIs

## Features

- User registration and login
- Password hashing and secured endpoints with Spring Security
- Pay period management
- Transaction tracking
- Recurring expense management
- Leftover balance summary calculation
- Notification worker service for background processing
- Asynchronous pay period summary generation using scheduled jobs
- RabbitMQ messaging for background job communication
- Redis caching to improve financial data retrieval
- RESTful API structure using controllers, services, repositories, DTOs, and entities

## Notification Worker Service

The Leftover API includes a background notification worker service built with Java, Spring Boot, Redis, and RabbitMQ.

The worker service handles asynchronous pay period summary generation using scheduled jobs and RabbitMQ messaging. Redis caching is used to offload repeated background processing and improve financial data retrieval performance.

## Project Highlight

**Notification Worker Service | Java, Spring Boot, Redis, RabbitMQ Messaging**

- Implemented asynchronous pay period summary generation using scheduled jobs and RabbitMQ messaging.
- Used Redis caching to offload background processing and improve financial data retrieval in Leftover API.

## Project Structure

```text
leftover-api
├── controller
├── service
├── repository
├── entity
├── dto
├── config
├── exception
└── worker
```

## API Overview

### Authentication

```http
POST /register
POST /login
POST /logout
```

### Pay Periods

```http
POST /api/pay-periods
GET /api/pay-periods
GET /api/pay-periods/{id}
PUT /api/pay-periods/{id}
DELETE /api/pay-periods/{id}
```

### Transactions

```http
POST /api/transactions
GET /api/transactions
GET /api/transactions/{id}
PUT /api/transactions/{id}
DELETE /api/transactions/{id}
```

### Recurring Expenses

```http
POST /api/recurring-expenses
GET /api/recurring-expenses
GET /api/recurring-expenses/{id}
PUT /api/recurring-expenses/{id}
DELETE /api/recurring-expenses/{id}
```

### Summary

```http
GET /api/summary
GET /api/pay-periods/{id}/summary
```

### Notifications / Worker

```http
GET /api/notifications
GET /api/notifications/{id}
POST /api/notifications/{id}/mark-as-read
```

## Running Locally

### 1. Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/leftover-api.git
cd leftover-api
```

### 2. Configure the database

Update `src/main/resources/application.properties` with your local database credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/leftover_api
spring.datasource.username=root
spring.datasource.password=your_password
```

### 3. Configure Redis and RabbitMQ

Make sure Redis and RabbitMQ are running locally.

Example configuration:

```properties
spring.data.redis.host=localhost
spring.data.redis.port=6379

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

### 4. Run the application

On macOS/Linux:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

## Current Version

This is the initial backend version of Leftover API, focused on authentication, personal finance tracking, pay period summaries, recurring expenses, transactions, Redis caching, RabbitMQ messaging, and background notification worker processing.

## Future Improvements

- JWT-based authentication
- Docker Compose setup
- OpenAPI/Swagger documentation
- Unit and integration tests
- Improved global exception handling
- Email or SMS notification delivery
- Dashboard-ready financial analytics endpoints

## Author

Built by Martin Alemajoh.
