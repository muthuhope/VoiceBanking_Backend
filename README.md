#  Voice Banking System - Backend
Spring Boot backend for the Voice Banking System.  
This application provides secure REST APIs for user authentication, account management, balance inquiry, and fund transfers.

## Tech Stack
- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- MySQL
- Hibernate
- Lombok
- Maven

## Features
- User Registration & Login
- Account Creation
- Balance Inquiry
- Fund Transfer
- Transaction History
- RESTful API Architecture
- Database Integration with MySQL

## Project Structure
src/main/java/com/voicebanking
│
├── controller     # REST Controllers
├── service        # Business Logic
├── repository     # JPA Repositories
├── model          # Entity Classes
└── config         # Security / Configuration

## Setup Instructions

### 1. Clone Repository

```bash
git clone https://github.com/YOUR_USERNAME/VoiceBanking-Backend.git
cd VoiceBanking-Backend

### 2. Configure Database

Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/voicebanking
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

### 3. Run Application

```bash
mvn spring-boot:run
Application will start at:
http://localhost:8080

## API Endpoints

* Register User
POST /api/register

* Login
POST /api/login


* Check Balance
GET /api/account/{id}/balance

*Transfer Money
POST /api/transfer

* Testing APIs
Use:
- Postman

  ## Future Improvements

- JWT Authentication
- Role-Based Access Control
- Voice Recognition Integration
- Docker Deployment
- Unit & Integration Testing

Author
**Muthukumaran M**

