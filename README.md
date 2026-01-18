📚 Library Management System
<div align="center">
Show Image
Show Image
Show Image
Show Image
Show Image
A production-ready RESTful backend for comprehensive library operations
Engineered with clean architecture, JWT authentication, and enterprise-grade security
Features • Tech Stack • Quick Start • API Docs • Architecture
</div>

🌟 Overview
A robust, scalable library management system built with Spring Boot, featuring JWT-based stateless authentication, role-based access control, and DTO-driven REST APIs. This project showcases industry-standard practices including pagination, advanced search, global exception handling, and secure credential management.
Why This Project Stands Out
✅ Production-Ready - Clean architecture with separation of concerns
✅ Secure by Design - JWT authentication with BCrypt password encryption
✅ Scalable APIs - Pagination, search, and optimized query patterns
✅ Best Practices - DTOs, global exception handling, role-based authorization
✅ Interview-Ready - Demonstrates deep understanding of Spring ecosystem

🚀 Features
🔐 Authentication & Security

JWT-Based Stateless Authentication - Secure, scalable session management
Role-Based Access Control (RBAC) - Granular permissions for ADMIN and MEMBER roles
Spring Security Integration - Custom JWT filter chain for request validation
Password Encryption - BCrypt hashing for secure credential storage
Token-Based Authorization - Bearer token validation on protected endpoints

📚 Book Management
FeatureDescriptionAccess LevelAdd BooksCreate new book entries with metadataADMINView BooksPaginated listing with customizable page sizeALLSearchTitle and author-based search with paginationALLAvailability TrackingReal-time book status monitoringALLDuplicate PreventionAutomatic validation against double-issuingSYSTEM
🔄 Issue & Return System

Smart Issuance - Validates availability before issuing
User-Specific Tracking - Links books to authenticated members
Safe Returns - Validates issue status before processing returns
Audit Trail - Tracks all transactions (future enhancement)

📄 Advanced Features

Pagination - Server-side pagination using PageRequest
Custom DTOs - Clean API contracts with PagedResponse<T>
Efficient Queries - Optimized JPA queries with Hibernate
Global Error Handling - Consistent error responses across all endpoints


🏗️ Tech Stack
CategoryTechnologiesLanguageJava 17FrameworkSpring Boot 3.xSecuritySpring Security + JWT (jjwt)PersistenceSpring Data JPA, Hibernate ORMDatabaseMySQL 8.xBuild ToolMavenArchitectureLayered Architecture (Controller → Service → Repository)

📁 Project Architecture
src/main/java/com/library/librarymanagement
├── 🔧 config/
│   ├── SecurityConfig.java          # Spring Security configuration
│   ├── JwtAuthenticationFilter.java # JWT validation filter
│   └── JwtUtil.java                 # Token generation & parsing
│
├── 🎮 controller/
│   ├── AuthController.java          # Authentication endpoints
│   ├── BookController.java          # Book management APIs
│   └── dto/                         # Request/Response DTOs
│       ├── LoginRequest.java
│       ├── JwtResponse.java
│       ├── BookRequest.java
│       ├── BookResponse.java
│       └── PagedResponse.java
│
├── 🗃️ entity/
│   ├── User.java                    # User entity with roles
│   ├── Book.java                    # Book entity
│   └── Role.java                    # Role enumeration
│
├── 📦 repository/
│   ├── UserRepository.java
│   └── BookRepository.java          # Custom search queries
│
├── ⚙️ service/
│   ├── AuthService.java
│   ├── BookService.java
│   └── UserDetailsServiceImpl.java
│
└── ❌ exception/
    ├── BookNotFoundException.java
    ├── BookAlreadyIssuedException.java
    ├── BookNotIssuedException.java
    └── GlobalExceptionHandler.java
Design Principles Applied

✔️ DTOs over Entities - Never expose JPA entities directly
✔️ Single Responsibility - Each layer has distinct concerns
✔️ Dependency Injection - Loose coupling via Spring IoC
✔️ Exception Centralization - Global handler for consistent errors


🔐 Authentication Flow
mermaidsequenceDiagram
    participant Client
    participant AuthController
    participant JwtUtil
    participant Database
    
    Client->>AuthController: POST /auth/login (email, password)
    AuthController->>Database: Validate credentials
    Database-->>AuthController: User found
    AuthController->>JwtUtil: Generate JWT
    JwtUtil-->>AuthController: JWT Token
    AuthController-->>Client: { token: "eyJhbG..." }
    
    Note over Client: Store token
    
    Client->>BookController: GET /books (Authorization: Bearer <JWT>)
    BookController->>JwtUtil: Validate token
    JwtUtil-->>BookController: Valid (User + Roles)
    BookController->>Database: Fetch books
    Database-->>BookController: Books data
    BookController-->>Client: Paginated response
```

---

## 📌 API Documentation

### Base URL
```
http://localhost:8080/api
🔑 Authentication
Login
httpPOST /auth/login
Content-Type: application/json

{
  "email": "member@library.com",
  "password": "member123"
}
Response:
json{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "expiresIn": 86400000
}

📚 Book Operations
Get All Books (Paginated)
httpGET /books?page=0&size=10
Authorization: Bearer <JWT_TOKEN>
Response:
json{
  "content": [
    {
      "id": 1,
      "title": "Clean Code",
      "author": "Robert C. Martin",
      "isbn": "978-0132350884",
      "available": true
    }
  ],
  "pageNumber": 0,
  "pageSize": 10,
  "totalElements": 45,
  "totalPages": 5
}
Search Books
httpGET /books/search?title=Java&page=0&size=5
GET /books/search?author=Martin&page=0&size=5
Authorization: Bearer <JWT_TOKEN>
Add New Book (Admin Only)
httpPOST /books
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

{
  "title": "Effective Java",
  "author": "Joshua Bloch",
  "isbn": "978-0134685991",
  "quantity": 5
}

🔄 Issue & Return
Issue Book
httpPOST /books/issue
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

{
  "bookId": 1
}
Success Response:
json{
  "message": "Book issued successfully",
  "bookTitle": "Clean Code",
  "dueDate": "2026-02-02"
}
Return Book
httpPOST /books/return/1
Authorization: Bearer <JWT_TOKEN>

❌ Error Responses
All errors follow a consistent format:
json{
  "timestamp": "2026-01-19T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Book with ID 999 not found",
  "path": "/api/books/999"
}
Common Status Codes:

400 - Bad Request (validation errors)
401 - Unauthorized (missing/invalid JWT)
403 - Forbidden (insufficient permissions)
404 - Not Found
409 - Conflict (e.g., book already issued)


🚀 Quick Start
Prerequisites

Java 17 or higher
Maven 3.8+
MySQL 8.x
Postman (for API testing)

Installation

Clone the repository

bashgit clone https://github.com/omvikas/library-management-system.git
cd library-management-system

Configure Database

properties# application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/library_db
spring.datasource.username=your_username
spring.datasource.password=your_password

# JWT Configuration
jwt.secret=your-256-bit-secret-key
jwt.expiration=86400000

Build the project

bashmvn clean install

Run the application

bashmvn spring-boot:run
```

5. **Access the API**
```
http://localhost:8080/api
Default Users
EmailPasswordRoleadmin@library.comadmin123ADMINmember@library.commember123MEMBER

🧪 Testing
Using Postman

Import the provided collection: Library-Management.postman_collection.json
Set environment variable JWT_TOKEN after login
Test all endpoints with pre-configured requests

Using cURL
bash# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"member@library.com","password":"member123"}'

# Get books (replace <TOKEN>)
curl -X GET http://localhost:8080/api/books?page=0&size=5 \
  -H "Authorization: Bearer <TOKEN>"

🎯 Key Learning Outcomes
This project demonstrates mastery of:
ConceptImplementationSecurityJWT authentication, BCrypt encryption, Spring SecurityREST APIsRESTful design, proper HTTP methods, status codesDatabaseJPA/Hibernate, custom queries, paginationArchitectureLayered architecture, DTOs, separation of concernsBest PracticesException handling, validation, logging

🔮 Future Enhancements

 Refresh Token Mechanism - Extend session management
 Swagger/OpenAPI Documentation - Interactive API docs
 Docker Support - Containerization with Docker Compose
 Fine-Grained Permissions - More granular role hierarchy
 Redis Caching - Cache frequently accessed books
 Email Notifications - Overdue reminders
 Audit Logging - Complete transaction history
 Book Reservations - Queue system for unavailable books
 Analytics Dashboard - Usage statistics and reports


🤝 Contributing
Contributions are welcome! Please follow these steps:

Fork the repository
Create a feature branch (git checkout -b feature/AmazingFeature)
Commit your changes (git commit -m 'Add some AmazingFeature')
Push to the branch (git push origin feature/AmazingFeature)
Open a Pull Request


📄 License
This project is licensed under the MIT License - see the LICENSE file for details.

👨‍💻 Author
Omvikas
Java Backend Engineer
Show Image
Show Image
Show Image

<div align="center">
⭐ If this project helped you, please give it a star!
Made with ❤️ and Spring Boot
</div>
