# 📚 Library Management System (Backend)

**Spring Boot REST API** implementing a **secure, production-style Library Management System** with  
**JWT Authentication, Role-Based Access Control, Pagination, and Search**.

Built to demonstrate **real-world backend engineering practices**, not just CRUD.

---

## ✨ Key Highlights

- 🔐 **JWT-based Authentication (Stateless)**
- 👥 **Role-Based Authorization (ADMIN / MEMBER)**
- 📚 **Book Management with Issue & Return Flow**
- 🔍 **Pagination & Search (Title / Author)**
- 🧱 **Clean Architecture with DTOs**
- ⚠️ **Global Exception Handling**
- 🗄️ **MySQL + JPA/Hibernate**
- 🚀 **Resume & Interview Ready**

---

## 🛠️ Tech Stack

| Layer | Technology |
|------|-----------|
| Language | Java 17 |
| Framework | Spring Boot |
| Security | Spring Security + JWT |
| Persistence | Spring Data JPA, Hibernate |
| Database | MySQL |
| Build Tool | Maven |

---

## 🧩 System Architecture

controller → service → repository → database
↓ ↓
DTOs business logic

markdown
Copy code

✔ Controllers expose **DTOs only**  
✔ Entities are **never leaked**  
✔ Business rules live in **service layer**  
✔ Centralized error handling

---

## 🔐 Authentication & Authorization

### Authentication
- Stateless JWT authentication
- Secure `/auth/login` endpoint
- Passwords encrypted using BCrypt

### Authorization
- **ADMIN**
    - Add books
- **MEMBER**
    - View books
    - Search books
    - Issue books
    - Return books

JWT is required for **all protected endpoints**.

---

## 🔁 JWT Flow

1. User logs in using email & password
2. Server returns a **JWT token**
3. Client sends token in every request:
   Authorization: Bearer <JWT_TOKEN>

yaml
Copy code
4. JWT filter validates token & sets security context

---

## 📚 Core Features

### Book Management
- Add new books (Admin only)
- Track availability status
- Prevent duplicate issue of books

### Book Issue & Return
- Issue books to authenticated users
- Block issuing already issued books
- Return books safely with validation

### Pagination & Search
- Paginated book listing
- Search by **title** or **author**
- Optimized responses using custom paged DTO

---

## 🌐 API Endpoints

### 🔑 Authentication
| Method | Endpoint | Description |
|------|---------|-------------|
| POST | `/auth/login` | Login & generate JWT |

---

### 📚 Books
| Method | Endpoint | Access |
|------|---------|--------|
| POST | `/books` | ADMIN |
| GET | `/books?page=0&size=5` | ADMIN, MEMBER |
| GET | `/books/search?title=java` | ADMIN, MEMBER |
| GET | `/books/search?author=martin` | ADMIN, MEMBER |

---

### 🔄 Issue / Return
| Method | Endpoint | Access |
|------|---------|--------|
| POST | `/books/issue` | MEMBER |
| POST | `/books/return/{bookId}` | MEMBER |

---

## 🧪 Sample Requests

### Login
```http
POST /auth/login
Content-Type: application/json

{
"email": "member@library.com",
"password": "member123"
}
Authorized Request
http
Copy code
GET /books
Authorization: Bearer <JWT_TOKEN>
⚠️ Error Handling
Custom domain exceptions:

BookNotFoundException

BookAlreadyIssuedException

BookNotIssuedException

Centralized @ControllerAdvice

Clean JSON error responses with proper HTTP status codes

📈 Why This Project Stands Out
This project demonstrates:

Real-world JWT security implementation

Clean separation of concerns

Scalable API design (pagination + search)

Industry-standard Spring Security usage

Backend design expected from professional Java developers

This is not a tutorial clone.

🚀 Future Enhancements
Docker & Docker Compose

Refresh tokens

Swagger / OpenAPI documentation

Caching for high-read endpoints

Admin analytics

👨‍💻 Author
Vikas
Java Backend Developer
