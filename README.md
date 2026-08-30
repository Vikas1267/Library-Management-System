# 🤖 AI-Powered Library Management System

![Java 17](https://img.shields.io/badge/Java-17-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-brightgreen.svg)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6.0-blue.svg)
![Swagger UI](https://img.shields.io/badge/Swagger-OpenAPI%203.0-green.svg)
![License](https://img.shields.io/badge/License-MIT-purple.svg)

A production-style, full-featured **AI-Powered Library Management System** built with **Spring Boot 3**, **Spring Security**, **JWT Authentication**, **Role-Based Access Control (RBAC)**, **AI Natural Language Assistant**, **Overdue Fine Engine**, **H2 / MySQL**, and **Interactive Swagger UI**.

---

## 🎯 Purpose of the Project

The goal of this system is to demonstrate modern, production-level backend engineering practices beyond simple CRUD operations:
1. **AI Integration**: Provide an interactive AI Library Assistant capable of parsing natural language prompts, analyzing reading history for personalized recommendations, and generating book executive summaries.
2. **Enterprise Security**: Implement stateless JWT authentication with role-based endpoint protection (`ADMIN` / `LIBRARIAN` vs `MEMBER`).
3. **Real-World Business Logic**: Enforce borrowing periods, auto-generated 14-day due dates, and automatic late return fine calculations ($1.00/day).
4. **Zero-Configuration Developer Experience**: Any developer cloning this repository can run and test it **instantly** without installing database software or configuring passwords.

---

## ✨ Key Features

- 🤖 **AI Assistant Chat (`POST /ai/chat`)**: Natural language library assistant for book recommendations, policy clarification, and semantic catalog search.
- 🎯 **Personalized AI Recommendations (`GET /ai/recommendations`)**: AI engine analyzing member reading history to suggest matching books.
- 📝 **AI Book Summarizer (`GET /ai/summarize/{bookId}`)**: Instant AI executive summary, key takeaways, and target audience profile.
- 🔐 **Stateless JWT Security**: Secure authentication via JJWT (HMAC SHA-256) and BCrypt password encryption.
- 👥 **Role-Based Authorization**: Fine-grained endpoint permissions for `ADMIN` and `MEMBER` users.
- ⏱️ **Auto Due Dates & Overdue Fines**: 14-day borrowing cycles with automatic late return fine calculations.
- 📖 **Member Self-Service**: Instant member registration (`POST /auth/register`), reading dashboard (`GET /books/my-borrowed`), and history tracking (`GET /books/my-history`).
- ⚡ **Zero-Config Out-of-the-Box Execution**: Default embedded **H2 Database** auto-seeds default accounts and sample books on startup.
- 🗄️ **H2 Web Console**: Visual database inspection at `http://localhost:8080/h2-console`.
- 📄 **Interactive Swagger UI**: Full OpenAPI 3 documentation with built-in JWT `Bearer` token authorization.

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.5 |
| Security | Spring Security 6 + JWT (JJWT 0.11.5) |
| AI Engine | Natural Language Semantic Matcher + LLM API Support |
| Documentation | Springdoc OpenAPI / Swagger UI 2.8 |
| Persistence | Spring Data JPA, Hibernate ORM |
| Database | H2 Embedded (Default) / MySQL 8+ (Optional) |
| Build Tool | Maven |

---

## 🚀 How to Run Locally (Step-by-Step)

### 1. Prerequisites
- **Java 17** or higher installed.
- Git installed.

### 2. Clone the Repository
```powershell
git clone https://github.com/Vikas1267/AI-Powered-Library-System.git
cd AI-Powered-Library-System
```

### 3. Run the Application (Zero Database Setup Required!)
Run the Maven wrapper command in terminal or click **Run** in IntelliJ IDEA / Eclipse:

```powershell
# Windows (PowerShell / CMD)
.\mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```

The application starts on `http://localhost:8080` and auto-initializes the database with pre-configured accounts and catalog books:

#### 🔑 Pre-Seeded Default Accounts:
- 👑 **Admin**: `admin@library.com` / `admin123` (Role: `ADMIN`)
- 👤 **Default Member**: `member@library.com` / `member123` (Role: `MEMBER`)

#### 📚 Pre-Loaded Sample Books:
1. *Clean Code* by Robert C. Martin (ISBN: `9780132350884`)
2. *Clean Architecture* by Robert C. Martin (ISBN: `9780134494166`)
3. *Design Patterns* by Erich Gamma (ISBN: `9780201633610`)

---

## 📄 How to Test via Interactive Swagger UI (Detailed Guide)

Open your browser and navigate to: **[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

Swagger UI provides an interactive web interface where you can test every feature of the Library System directly from your web browser!

---

### 👤 Testing Flow A: Self-Service Member Journey

#### Step 1: Self-Register a New Member (`POST /auth/register`)
1. Expand **`POST /auth/register`** under the **Authentication** section.
2. Click **Try it out**.
3. In the Request body box, enter new member details:
   ```json
   {
     "name": "Alex Smith",
     "email": "alex@library.com",
     "password": "user123",
     "role": "MEMBER"
   }
   ```
4. Click **Execute**. (Response: `200 OK` → `"User registered successfully"`).

#### Step 2: Login as Member & Get JWT Token (`POST /auth/login`)
1. Expand **`POST /auth/login`**.
2. Click **Try it out**, enter:
   ```json
   {
     "email": "alex@library.com",
     "password": "user123"
   }
   ```
   *(Or test with the default pre-seeded member: `member@library.com` / `member123`)*
3. Click **Execute**.
4. Copy the `token` string from the JSON response.

#### Step 3: Authorize Swagger UI
1. Scroll to the top right of Swagger UI and click the green **Authorize 🔓** button.
2. In the modal text box under `bearerAuth`, paste your JWT token string.
3. Click **Authorize** and then **Close**. *(All padlock icons will change to locked 🔒)*.

#### Step 4: Borrow a Book (`POST /books/issue`)
1. Expand **`POST /books/issue`** -> Click **Try it out**.
2. Enter: `{"bookId": 1}` and click **Execute**.
3. **Observe Response**: The system issues the book to your logged-in account, generates an automatic 14-day `dueDate`, and marks `overdue: false`.

#### Step 5: Check My Active Borrowed Books (`GET /books/my-borrowed`)
1. Expand **`GET /books/my-borrowed`** -> Click **Try it out** -> Click **Execute**.
2. Returns all books currently in your possession.

#### Step 6: Test AI Library Assistant Chat (`POST /ai/chat`)
1. Expand **`POST /ai/chat`** under **AI Library Assistant**.
2. Click **Try it out**, enter body:
   ```json
   {
     "message": "Suggest clean software architecture books"
   }
   ```
3. Click **Execute**.
4. The AI returns natural language answers and lists relevant catalog books!

#### Step 7: Return a Book & Check Late Fine (`POST /books/return/{bookId}`)
1. Expand **`POST /books/return/{bookId}`** -> Click **Try it out**.
2. Set `bookId: 1` and click **Execute**.
3. Book availability is restored to `true`, and return timestamp & fine calculations are recorded.

---

### 👑 Testing Flow B: Admin Management Journey

1. **Login as Admin**: Call `POST /auth/login` with `admin@library.com` / `admin123`.
2. **Authorize**: Paste Admin token into Swagger **Authorize 🔓** modal.
3. **Add Book**: Call `POST /books` with:
   ```json
   {
     "title": "Domain-Driven Design",
     "author": "Eric Evans",
     "isbn": "9780321125217"
   }
   ```
4. **Update Book**: Call `PUT /books/4` to update title/author.
5. **View All Library Issues**: Call `GET /books/admin/all-issued` to inspect active borrows across all members.
6. **Delete Book**: Call `DELETE /books/4` to remove a book from catalog.

---

## 🗄️ Visual Database Inspection via H2 Console

Open your browser and go to: **[http://localhost:8080/h2-console](http://localhost:8080/h2-console)**

- **JDBC URL**: `jdbc:h2:mem:librarydb`
- **User Name**: `sa`
- **Password**: *(leave blank)*
- Click **Connect** to query database tables (`users`, `books`, `borrow_records`) directly!

---

## 🌐 Complete API Endpoint Reference & Explanation

### 🤖 AI Library Assistant (`/ai`)
| Method | Endpoint | Access | Description |
|---|---|---|---|
| `POST` | `/ai/chat` | Authenticated | Interactive AI Assistant Chat & semantic catalog recommendation search |
| `GET` | `/ai/recommendations` | Authenticated | Personalized AI book recommendations based on user reading history |
| `GET` | `/ai/summarize/{bookId}` | Authenticated | AI executive summary, key takeaways, and target audience profile for a book |

### 🔑 Authentication (`/auth`)
| Method | Endpoint | Access | Description |
|---|---|---|---|
| `POST` | `/auth/register` | Public | Self-register a new library member (`MEMBER` or `ADMIN` role) |
| `POST` | `/auth/login` | Public | Authenticate credentials & generate JWT bearer token |

### 📚 Books Catalog (`/books`)
| Method | Endpoint | Access | Description |
|---|---|---|---|
| `GET` | `/books?page=0&size=5` | Public / All | Get paginated list of books in catalog |
| `GET` | `/books/search?title=...&author=...` | Public / All | Case-insensitive search books by title or author |
| `POST` | `/books` | ADMIN / LIBRARIAN | Add a new book to catalog |
| `PUT` | `/books/{id}` | ADMIN / LIBRARIAN | Update existing book details |
| `DELETE` | `/books/{id}` | ADMIN / LIBRARIAN | Delete a book from catalog |

### 🔄 Borrowing & Member Self-Service (`/books`)
| Method | Endpoint | Access | Description |
|---|---|---|---|
| `POST` | `/books/issue` | MEMBER / ADMIN | Borrow a book (sets 14-day due date) |
| `POST` | `/books/return/{bookId}` | MEMBER / ADMIN | Return borrowed book (calculates overdue fine if late) |
| `GET` | `/books/my-borrowed` | MEMBER | View active borrowed books for currently logged-in user |
| `GET` | `/books/my-history` | MEMBER | View complete borrow history & fine records for logged-in user |
| `GET` | `/books/admin/all-issued` | ADMIN / LIBRARIAN | View all active library issues across all members |

---

## 👨‍💻 Author
Developed by **Vikas** – Java Backend Developer.
