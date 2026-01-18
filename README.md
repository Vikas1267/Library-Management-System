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
## Screenshots(Results) :
<img width="908" height="787" alt="Screenshot 2026-01-17 220643" src="https://github.com/user-attachments/assets/39572268-089e-4478-ac9c-60af18fb73a9" />



<img width="900" height="815" alt="Screenshot 2026-01-17 220940" src="https://github.com/user-attachments/assets/4b85250b-a50d-41f6-a6da-db9f668c0d7e" />



<img width="905" height="770" alt="Screenshot 2026-01-18 162955" src="https://github.com/user-attachments/assets/16c35fed-e979-469f-a6df-875a24c18f70" />



<img width="886" height="801" alt="Screenshot 2026-01-18 221148" src="https://github.com/user-attachments/assets/097b9d60-d182-4c3c-beb3-ccc694cde870" />



<img width="1336" height="709" alt="Screenshot 2026-01-18 221221" src="https://github.com/user-attachments/assets/c032740d-897d-448e-b322-f19adcc9de36" />



<img width="955" height="890" alt="Screenshot 2026-01-18 230231" src="https://github.com/user-attachments/assets/3f00017e-47ce-4d5f-8fe4-21509ca38c7f" />



<img width="949" height="925" alt="Screenshot 2026-01-18 231747" src="https://github.com/user-attachments/assets/4dbf0f2a-fc4e-4cf1-a745-3c5b287deae8" />



<img width="931" height="900" alt="Screenshot 2026-01-18 233410" src="https://github.com/user-attachments/assets/3bb6a089-05f4-4ea7-8e34-d7bab18ee87b" />



<img width="899" height="891" alt="Screenshot 2026-01-19 001009" src="https://github.com/user-attachments/assets/786edbbd-5a55-45ea-8b0a-755970aeb760" />



<img width="927" height="973" alt="Screenshot 2026-01-19 001251" src="https://github.com/user-attachments/assets/5451e6a9-0373-44b8-9734-4c3b9cb1f26b" />



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
