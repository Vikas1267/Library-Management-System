📚 Library Management System

Spring Boot | JWT Authentication | Role-Based Access | Pagination & Search

A production-style backend application for managing library operations, built with Spring Boot and JWT-based authentication.
Designed with clean architecture, DTO-based APIs, and scalable REST patterns.

🚀 Features
🔐 Authentication & Security

JWT-based authentication (stateless)

Secure login endpoint (/auth/login)

Role-based access control:

ADMIN → Add books

MEMBER → View, search, issue, and return books

Spring Security with custom JWT filter

Password encryption using BCrypt

📚 Book Management

Add new books (Admin only)

View all books (paginated)

Search books by title or author

Track book availability

Prevent double-issuing of books

🔄 Book Issue & Return

Issue books to authenticated users

Prevent issuing already issued books

Return books safely

Proper exception handling with meaningful error responses

📄 Pagination & Search

Pagination using Spring Data PageRequest

Clean paged API response using custom DTO

Efficient search with pagination support

🏗️ Tech Stack

Java 17

Spring Boot

Spring Security

JWT (JSON Web Tokens)

Spring Data JPA

Hibernate

MySQL

Maven

🧱 Project Architecture
src/main/java/com/library/librarymanagement
├── config        # Security, JWT filter, JWT utility
├── controller    # REST controllers
│   └── dto       # Request & Response DTOs
├── entity        # JPA entities
├── repository    # Spring Data JPA repositories
├── service       # Business logic
├── exception     # Custom exceptions & global handler


✔ Entities are not exposed directly
✔ DTOs used for clean API contracts
✔ Global exception handling implemented

🔐 Authentication Flow (JWT)

User logs in using email & password

Server generates a JWT token

Client sends token in header:

Authorization: Bearer <JWT_TOKEN>


JWT filter validates token for every request

Access granted based on role

📌 API Endpoints
🔑 Auth
Method	Endpoint	Description
POST	/auth/login	Login & get JWT token
📚 Books
Method	Endpoint	Access
POST	/books	ADMIN
GET	/books?page=0&size=5	MEMBER / ADMIN
GET	/books/search?title=java	MEMBER / ADMIN
GET	/books/search?author=martin	MEMBER / ADMIN
🔄 Issue / Return
Method	Endpoint	Access
POST	/books/issue	MEMBER
POST	/books/return/{bookId}	MEMBER
🧪 Sample Requests
Login
POST /auth/login
Content-Type: application/json

{
  "email": "member@library.com",
  "password": "member123"
}

Authorized Request
GET /books
Authorization: Bearer <JWT_TOKEN>

❗ Error Handling

Custom exceptions:

Book not found

Book already issued

Book not issued

Global exception handler

Clean JSON error responses with HTTP status codes

📈 Why This Project Matters

This project demonstrates:

Real-world backend design

Secure authentication using JWT

Clean separation of concerns

Scalable API design (pagination & search)

Industry-standard Spring Security practices

✔ Resume-ready
✔ Interview-defensible
✔ Production-aligned

🧠 Future Enhancements

Docker support

Refresh tokens

API documentation (Swagger/OpenAPI)

Fine-grained permissions

Caching for read-heavy endpoints

👨‍💻 Author

Omvikas
Java Backend Engineer
