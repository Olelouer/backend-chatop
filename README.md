# Chatop API

## Overview
Chatop is a Spring Boot-based REST API for managing rental properties, users, and messages. It includes authentication, authorization, and file handling using JWT and Spring Security.

## Features
- User authentication (JWT-based login and registration)
- CRUD operations for rentals
- Messaging system for users to communicate
- File upload and retrieval functionality
- Secure API with role-based access control
- API documentation via Swagger (Springdoc OpenAPI)

## Technologies Used
- **Spring Boot 3.4.1**
- **Spring Security & JWT**
- **Spring Data JPA (MySQL)**
- **Lombok**
- **Springdoc OpenAPI (Swagger)**
- **Multipart file handling**
- **Maven for dependency management**

## Prerequisites
- **Java 21**
- **Maven 3+**
- **MySQL Database**

## Installation

### 1. Clone the Repository
```sh
git clone https://github.com/your-repository/chatop.git
cd chatop
```

### 2. Configure Environment Variables
Duplicate `.env-example` as `.env` and configure your database and JWT settings:

```sh
cp .env-example .env
```
Edit `.env` and update:
```sh
# Database Configuration
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password
DB_URL=your_db_url

# JWT Configuration
JWT_SECRET=your_jwt_secret
```

### 3. Database Setup
Before running the application, you **must manually create the database**:
```sql
CREATE DATABASE chatop;
```
The application **only creates the necessary tables**, but it does **not** create the database itself.

### 4. Build and Run the Application
To start the application:
```sh
mvn clean install
mvn spring-boot:run
```
The server runs on **port 3001** by default.

## API Endpoints

### Authentication
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | `/api/auth/register` | Register a new user |
| POST   | `/api/auth/login` | Authenticate user and get JWT |
| GET    | `/api/auth/me` | Get current user details |

### Rentals
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | `/api/rentals` | Create a new rental |
| GET    | `/api/rentals` | Get all rentals |
| GET    | `/api/rentals/{id}` | Get rental by ID |
| PUT    | `/api/rentals/{id}` | Update rental |

### Messages
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | `/api/messages` | Send a message to a rental owner |

### File Handling
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | `/uploads/{filename}` | Retrieve uploaded files |

## Swagger API Documentation
Swagger UI is available at:
```
http://localhost:3001/swagger-ui/index.html
```

## Project Structure
```
chatop/
 ├── src/main/java/com/openclassrooms/chatop
 │   ├── controller/    # API controllers
 │   ├── service/       # Business logic services
 │   ├── model/         # JPA entities
 │   ├── repository/    # Database repositories
 │   ├── security/      # JWT and security configuration
 │   ├── config/        # Application configurations
 ├── src/main/resources
 │   ├── application.properties  # Spring Boot properties
 ├── uploads    # Uploaded pictures
 ├── pom.xml    # Maven dependencies
 ├── .env-example    # Example env file
 ├── README.md  # Documentation
```

## Notes
- The application **requires a pre-existing MySQL database** named `chatop`.
- API authentication uses **JWT**. Users need to include a **Bearer Token** in the request headers after login.
- The **uploads** directory is used to store rental images.