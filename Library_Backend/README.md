# Library Management System - Backend

A backend REST API for managing books, genres, users, authentication, and library inventory. The application is built using **Java 21** and **Spring Boot**, following a layered architecture with DTOs, MapStruct, Spring Data JPA, Bean Validation, global exception handling, pagination, filtering, sorting, and JWT-based authentication.

---

## 📌 Project Overview

The Library Management System Backend provides REST APIs for managing a digital library.

### Core Features

- Book CRUD operations
- Bulk book creation
- Genre management
- Book inventory management
- Total and available copy tracking
- ISBN uniqueness validation
- Search by title, author, or ISBN
- Genre-based filtering
- Availability filtering
- Pagination
- Sorting
- Soft deletion
- Permanent deletion
- DTO-based API design
- Bean Validation
- Custom exceptions
- Global exception handling
- JWT authentication
- BCrypt password hashing
- Role-based authorization
- CORS configuration

---

## 🛠️ Technology Stack

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| Spring Boot | Backend framework |
| Spring Web | REST API development |
| Spring Data JPA | Database access and repository abstraction |
| Hibernate | ORM / persistence |
| Spring Security | Authentication and authorization |
| JWT | Stateless authentication |
| BCrypt | Password hashing |
| Jakarta Bean Validation | Request validation |
| MapStruct | Entity ↔ DTO mapping |
| Lombok | Boilerplate code reduction |
| Maven | Build and dependency management |
| H2 / Configured Database | Data persistence |

---

# 🏗️ Architecture

The application follows a layered architecture:

```text
Client
   |
   v
Controller
   |
   v
DTO + Validation
   |
   v
Service Interface
   |
   v
Service Implementation
   |
   +------> Mapper
   |
   v
Repository
   |
   v
Database
```

### Security Architecture

```text
Request
   |
   v
Spring Security Filter Chain
   |
   v
JwtFilter
   |
   v
JwtService
   |
   v
CustomUserDetailsService
   |
   v
CustomUserDetails
   |
   v
SecurityContext
```

---

# 📂 Project Structure

```text
src
└── main
    ├── java
    │   └── com.charu.library_management_system
    │
    │       ├── controller
    │       │   ├── BookController.java
    │       │   └── ...
    │       │
    │       ├── dto
    │       │   ├── BookDTO.java
    │       │   │
    │       │   ├── requestDTO
    │       │   │   ├── BookSearchRequestDTO.java
    │       │   │   └── ...
    │       │   │
    │       │   └── responseDTO
    │       │       ├── ApiResponse.java
    │       │       ├── PageResponseDTO.java
    │       │       └── ...
    │       │
    │       ├── exception
    │       │   ├── BookNotFoundException.java
    │       │   ├── GenreNotFoundException.java
    │       │   ├── DuplicateIsbnException.java
    │       │   └── GlobalExceptionHandler.java
    │       │
    │       ├── mapper
    │       │   └── BookMapper.java
    │       │
    │       ├── models
    │       │   ├── Book.java
    │       │   └── Genre.java
    │       │
    │       ├── repository
    │       │   ├── BookRepository.java
    │       │   └── GenreRepository.java
    │       │
    │       ├── security
    │       │   ├── SecurityConfig.java
    │       │   ├── JwtFilter.java
    │       │   ├── JwtService.java
    │       │   ├── CustomUserDetails.java
    │       │   └── CustomUserDetailsService.java
    │       │
    │       └── service
    │           ├── BookService.java
    │           └── implementation
    │               └── BookServiceImpl.java
    │
    └── resources
        ├── application.properties
        └── ...
```

---

# 📚 Book Management

The Book module is responsible for managing the library inventory.

A book contains information such as:

- ID
- ISBN
- Title
- Author
- Genre
- Publisher
- Publication date
- Language
- Number of pages
- Description
- Total copies
- Available copies
- Price
- Cover image URL
- Active status
- Created timestamp
- Updated timestamp

---

## 📖 Book and Genre Relationship

A book belongs to one genre.

The entity relationship is represented using:

```java
@ManyToOne
@JoinColumn(name = "genre_id", nullable = false)
private Genre genre;
```

This means multiple books can belong to the same genre.

The entity contains:

```java
private Genre genre;
```

while the DTO can expose:

```java
private Long genreId;
private String genreCode;
private String genreName;
```

MapStruct is responsible for converting between the entity and DTO representations.

---

# 📖 Genres

Example genres currently used by the application:

| Genre ID | Genre |
|---:|---|
| 8 | Fiction |
| 9 | Non-fiction |
| 10 | Fantasy |
| 11 | Mystery |
| 13 | Biography |

When creating or updating a book, the service verifies that the requested genre exists.

If the genre does not exist:

```text
GenreNotFoundException
```

is thrown.

---

# 📦 Inventory Management

The system maintains two important inventory fields:

```text
totalCopies
availableCopies
```

For example:

```text
totalCopies = 10
availableCopies = 7
```

This means the library owns 10 copies and 7 are currently available.

The main inventory rule is:

```text
availableCopies >= 0
availableCopies <= totalCopies
```

### Field Validation

Individual fields can use Bean Validation:

```java
@NotNull
@Min(0)
private Integer totalCopies;

@NotNull
@Min(0)
private Integer availableCopies;
```

### Cross-Field Validation

The relationship between `totalCopies` and `availableCopies` can be validated using:

```java
@AssertTrue(message = "Available copies should not be greater than total copies")
@JsonIgnore
public boolean isAvailableCopiesValid() {

    if (totalCopies == null || availableCopies == null) {
        return true;
    }

    return availableCopies <= totalCopies;
}
```

`@JsonIgnore` prevents the validation helper from appearing in API responses as:

```json
"availableCopiesValid": true
```

---

# 🔎 Search and Filtering

The application provides flexible book search.

The `searchTerm` can search across:

- Title
- ISBN
- Author

Additional filters include:

- Genre
- Availability

For example:

```text
searchTerm = "Harry"
```

can match a book where `"Harry"` occurs in:

```text
Title
Author
ISBN
```

---

## Availability Filter

When:

```text
availableOnly = false
```

the availability condition is not applied.

All active matching books can be returned.

When:

```text
availableOnly = true
```

only books satisfying:

```text
availableCopies > 0
```

are returned.

Only active books are included:

```text
active = true
```

---

# 📄 Pagination

Book search uses Spring Data `Pageable`.

Supported parameters:

```text
page
pageSize
sortBy
sortDirection
```

Example:

```http
GET /api/books?page=0&pageSize=10&sortBy=createdAt&sortDirection=DESC
```

Pages are zero-based:

```text
page = 0 → first page
page = 1 → second page
page = 2 → third page
```

The API returns a custom `PageResponseDTO`.

Example:

```json
{
  "content": [],
  "pageNumber": 0,
  "pageSize": 10,
  "totalPages": 2,
  "totalElements": 12,
  "first": true,
  "last": false,
  "empty": false
}
```

### Pagination Fields

| Field | Meaning |
|---|---|
| `content` | Records returned on the current page |
| `pageNumber` | Current zero-based page |
| `pageSize` | Number of records requested |
| `totalPages` | Total number of pages |
| `totalElements` | Total matching records |
| `first` | Whether this is the first page |
| `last` | Whether this is the last page |
| `empty` | Whether the current page is empty |

### Example

Suppose there are:

```text
23 matching books
pageSize = 10
```

Then:

```text
totalElements = 23
totalPages = 3
```

The pages contain approximately:

```text
Page 0 → 10 books
Page 1 → 10 books
Page 2 → 3 books
```

---

# 🔀 Sorting

Books can be sorted using:

```text
sortBy
sortDirection
```

Example:

```text
sortBy = title
sortDirection = ASC
```

or:

```text
sortBy = createdAt
sortDirection = DESC
```

The service creates the `Pageable` using `PageRequest` and `Sort`.

Page size is also bounded in the service to prevent unnecessarily large requests.

---

# 🔐 Authentication and Security

The backend uses **Spring Security with JWT-based stateless authentication**.

The security architecture contains:

```text
SecurityConfig
      |
      +---- AuthenticationManager
      |
      +---- AuthenticationProvider
      |
      +---- PasswordEncoder
      |
      +---- JwtFilter
               |
               v
           JwtService
               |
               v
       CustomUserDetailsService
               |
               v
        CustomUserDetails
```

---

# 🔑 Login Flow

The authentication process follows this flow:

```text
Login Request
      |
      v
AuthenticationManager
      |
      v
DaoAuthenticationProvider
      |
      v
CustomUserDetailsService
      |
      v
Database User
      |
      v
BCrypt Password Verification
      |
      v
Authentication Successful
      |
      v
JwtService
      |
      v
JWT Token
```

The application does not manually compare the login password.

`DaoAuthenticationProvider` uses the configured `PasswordEncoder` to verify the password.

---

# 🔐 AuthenticationManager

`AuthenticationManager` acts as the main authentication coordinator.

Example:

```java
authenticationManager.authenticate(
    new UsernamePasswordAuthenticationToken(
        email,
        password
    )
);
```

It receives the authentication request and delegates the authentication work to a suitable `AuthenticationProvider`.

---

# 🔐 AuthenticationProvider

The application uses:

```text
DaoAuthenticationProvider
```

The provider works with:

- `CustomUserDetailsService`
- `PasswordEncoder`

Its responsibility includes:

1. Loading the user.
2. Obtaining the stored password.
3. Comparing the entered password with the stored password.
4. Returning successful authentication or throwing an authentication exception.

The simplified flow is:

```text
AuthenticationManager
        |
        v
DaoAuthenticationProvider
        |
        v
CustomUserDetailsService
        |
        v
User from Database
        |
        v
PasswordEncoder
        |
        v
Success / Failure
```

---

# 🔑 Password Security

Passwords are never stored as plain text.

BCrypt is configured using:

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

### Registration

```text
Plain Password
      |
      v
BCrypt
      |
      v
Hashed Password
      |
      v
Database
```

### Login

During login:

```text
Entered Password
      |
      v
DaoAuthenticationProvider
      |
      v
PasswordEncoder
      |
      v
Stored BCrypt Password
      |
      v
Match / No Match
```

The password comparison is handled automatically by Spring Security.

---

# 👤 User Roles

The application uses a `UserRole` enum.

Example:

```java
public enum UserRole {
    ADMIN,
    CUSTOMER
}
```

A user's role is converted into a Spring Security authority using:

```java
new SimpleGrantedAuthority(
    "ROLE_" + auth.getRole().name()
);
```

Therefore:

```text
ADMIN
   ↓
ROLE_ADMIN
```

and:

```text
CUSTOMER
   ↓
ROLE_CUSTOMER
```

This allows role-based authorization.

Example:

```java
@PreAuthorize("hasRole('ADMIN')")
```

or:

```java
.hasAnyRole("ADMIN", "CUSTOMER")
```

---

# 🪪 JWT Processing

JWT-related functionality is handled by `JwtService`.

The token can contain claims such as:

```json
{
  "sub": "user@example.com",
  "role": "ADMIN",
  "iat": 1754620000,
  "exp": 1754623600
}
```

The application can extract individual claims using a generic method.

For example:

```java
public String extractRole(String token) {
    return extractClaim(
        token,
        claims -> claims.get("role", String.class)
    );
}
```

The JWT processing flow is:

```text
JWT
 |
 v
Verify Signature
 |
 v
Parse Token
 |
 v
Extract Claims
 |
 +----> Email
 |
 +----> Role
 |
 +----> Expiration
```

---

# 🛡️ JWT Filter

The `JwtFilter` processes incoming requests containing:

```text
Authorization: Bearer <token>
```

The filter:

1. Reads the Authorization header.
2. Extracts the JWT.
3. Extracts the user's email/username.
4. Validates the token.
5. Loads the user's details.
6. Creates an authenticated `Authentication` object.
7. Places it into the `SecurityContext`.

Simplified flow:

```text
Request
   |
   v
Authorization Header
   |
   v
Bearer Token
   |
   v
JwtService
   |
   v
Validate Token
   |
   v
Load User
   |
   v
Set Authentication
   |
   v
SecurityContext
```

---

# 🌐 CORS Configuration

The backend configures CORS so that the frontend can communicate with the API.

Development frontend:

```text
http://localhost:5173
```

Typical allowed methods:

```text
GET
POST
PUT
DELETE
PATCH
OPTIONS
```

A `CorsConfigurationSource` bean can provide the configuration:

```java
@Bean
public CorsConfigurationSource corsConfigurationSource() {

    CorsConfiguration configuration = new CorsConfiguration();

    configuration.setAllowedOrigins(
        List.of("http://localhost:5173")
    );

    configuration.setAllowedMethods(
        List.of(
            "GET",
            "POST",
            "PUT",
            "DELETE",
            "PATCH",
            "OPTIONS"
        )
    );

    configuration.setAllowedHeaders(
        List.of("*")
    );

    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source =
        new UrlBasedCorsConfigurationSource();

    source.registerCorsConfiguration(
        "/**",
        configuration
    );

    return source;
}
```

Spring Security can then use the configured CORS source with:

```java
.cors(Customizer.withDefaults())
```

---

# ⚠️ Exception Handling

The application uses custom exceptions for business errors.

Examples:

```text
BookNotFoundException
GenreNotFoundException
DuplicateIsbnException
```

A global exception handler converts these exceptions into appropriate HTTP responses.

Recommended mapping:

| Exception / Situation | HTTP Status |
|---|---:|
| Book not found | 404 Not Found |
| Genre not found | 404 Not Found |
| Duplicate ISBN | 409 Conflict |
| Validation failure | 400 Bad Request |
| Authentication failure | 401 Unauthorized |
| Insufficient permissions | 403 Forbidden |

This prevents business exceptions from incorrectly appearing as generic:

```text
500 Internal Server Error
```

---

# 🗑️ Delete Operations

The backend supports two deletion strategies.

## Soft Delete

The book remains in the database but is marked inactive:

```text
active = false
```

Normal search queries only return:

```text
active = true
```

### Advantages

- Preserves historical data
- Prevents accidental permanent deletion
- Allows future audit/history functionality

---

## Permanent Delete

Hard deletion permanently removes the record from the database.

```http
DELETE /api/books/{id}/permanent
```

This should be used carefully because the record cannot be recovered through the application.

---

# 📚 Book API Endpoints

Base URL:

```text
/api/books
```

---

## 1. Create Book

```http
POST /api/books
```

Example request:

```json
{
  "isbn": "9780061120084",
  "title": "To Kill a Mockingbird",
  "author": "Harper Lee",
  "genreId": 8,
  "publisher": "Harper Perennial",
  "publicationDate": "1960-07-11",
  "language": "English",
  "pages": 336,
  "description": "A classic novel about justice and equality.",
  "totalCopies": 12,
  "availableCopies": 9,
  "price": 449.99,
  "coverImageUrl": "https://example.com/images/book.jpg",
  "active": true
}
```

---

## 2. Bulk Create Books

```http
POST /api/books/create/bulk
```

Accepts a JSON array.

Example:

```json
[
  {
    "isbn": "9780061120084",
    "title": "To Kill a Mockingbird",
    "author": "Harper Lee",
    "genreId": 8,
    "totalCopies": 12,
    "availableCopies": 9,
    "price": 449.99
  },
  {
    "isbn": "9780399590504",
    "title": "Educated",
    "author": "Tara Westover",
    "genreId": 13,
    "totalCopies": 8,
    "availableCopies": 6,
    "price": 599.99
  }
]
```

---

## 3. Get Book by ID

```http
GET /api/books/{id}
```

Example:

```http
GET /api/books/1
```

---

## 4. Get Book by ISBN

```http
GET /api/books/isbn/{isbn}
```

Example:

```http
GET /api/books/isbn/9780061120084
```

---

## 5. Update Book

```http
PUT /api/books/{id}
```

When ISBN should not be editable, a dedicated update DTO can exclude the ISBN field.

Example:

```json
{
  "title": "Updated Book Title",
  "author": "Author Name",
  "genreId": 8,
  "publisher": "Publisher",
  "publicationDate": "2020-01-01",
  "language": "English",
  "pages": 300,
  "description": "Updated description",
  "totalCopies": 20,
  "availableCopies": 15,
  "price": 499.99,
  "coverImageUrl": "https://example.com/book.jpg",
  "active": true
}
```

---

## 6. Soft Delete

```http
DELETE /api/books/{id}/soft-delete
```

Marks the book as inactive.

---

## 7. Permanent Delete

```http
DELETE /api/books/{id}/permanent
```

Permanently removes the book.

---

## 8. Search Books

```http
GET /api/books
```

Supported parameters:

| Parameter | Description | Example |
|---|---|---|
| `genreId` | Filter by genre | `10` |
| `availableOnly` | Only books with available copies | `true` |
| `page` | Zero-based page | `0` |
| `pageSize` | Number of records per page | `10` |
| `sortBy` | Field to sort by | `title` |
| `sortDirection` | ASC or DESC | `ASC` |

Example:

```http
GET /api/books?genreId=10&availableOnly=true&page=0&pageSize=10&sortBy=title&sortDirection=ASC
```

---

## 9. Advanced Search

```http
POST /api/books/search
```

Example:

```json
{
  "searchTerm": "Harry",
  "genreId": 10,
  "availableOnly": true,
  "page": 0,
  "pageSize": 10,
  "sortBy": "title",
  "sortDirection": "ASC"
}
```

The same service method can process both the GET search request and the POST advanced-search request by converting the GET parameters into a `BookSearchRequestDTO`.

---

## 10. Book Statistics

```http
GET /api/books/stats
```

Example response:

```json
{
  "totalActiveBooks": 12,
  "totalAvailableBooks": 9
}
```

---

# 🔎 Search Query Logic

The repository uses a custom JPQL query similar to:

```java
@Query("""
    SELECT b FROM Book b
    WHERE
    (:searchTerm IS NULL OR
        LOWER(b.title) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
        OR LOWER(b.isbn) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
        OR LOWER(b.author) LIKE LOWER(CONCAT('%', :searchTerm, '%')))
    AND (:genreId IS NULL OR b.genre.id = :genreId)
    AND (:availableOnly = false OR b.availableCopies > 0)
    AND b.active = true
""")
Page<Book> searchBooksWithFilters(
    @Param("searchTerm") String searchTerm,
    @Param("genreId") Long genreId,
    @Param("availableOnly") Boolean availableOnly,
    Pageable pageable
);
```

### How the filters work

#### Search term

```text
searchTerm = null
```

means no text search is applied.

Otherwise:

```text
title contains searchTerm
OR
ISBN contains searchTerm
OR
author contains searchTerm
```

#### Genre

```text
genreId = null
```

means no genre filter is applied.

Otherwise:

```text
book.genre.id = genreId
```

#### Availability

```text
availableOnly = false
```

means all active books are considered.

```text
availableOnly = true
```

means:

```text
availableCopies > 0
```

#### Active status

Every search includes:

```text
active = true
```

Therefore soft-deleted books do not appear in normal search results.

---

# 🧩 DTO Design

The application uses DTOs rather than directly exposing JPA entities.

### Benefits

- Separates API and database models
- Controls which fields clients can submit
- Controls which fields are returned
- Enables request-specific validation
- Reduces coupling
- Makes API changes easier
- Prevents exposing internal entity relationships unnecessarily

For example, the entity contains:

```java
private Genre genre;
```

while the DTO can contain:

```java
private Long genreId;
private String genreCode;
private String genreName;
```

MapStruct handles the conversion.

---

# 🗺️ MapStruct

MapStruct is used to convert between DTOs and entities.

Typical flow during creation:

```text
BookDTO
   |
   v
BookMapper
   |
   v
Book Entity
   |
   v
BookRepository
```

For a response:

```text
Book Entity
   |
   v
BookMapper
   |
   v
BookDTO
```

This keeps mapping logic outside the service layer.

---

# ✅ Validation

The application uses Jakarta Bean Validation.

Common annotations include:

```text
@NotBlank
@NotNull
@Size
@Min
@DecimalMin
@Digits
@AssertTrue
```

Examples:

```java
@NotBlank(message = "ISBN is mandatory for books")
private String isbn;
```

```java
@NotBlank(message = "Title is mandatory")
private String title;
```

```java
@NotNull
@Min(0)
private Integer totalCopies;
```

```java
@NotNull
@Min(0)
private Integer availableCopies;
```

Validation is triggered in controllers using:

```java
@Valid
```

For example:

```java
@PostMapping
public ResponseEntity<?> addBook(
        @Valid @RequestBody BookDTO bookDTO) {

    ...
}
```

---

# 🧠 Service Layer Responsibilities

The service layer contains the business logic.

For example, creating a book follows:

```text
BookDTO
   |
   v
Check ISBN
   |
   +---- Duplicate → DuplicateIsbnException
   |
   v
Find Genre
   |
   +---- Not found → GenreNotFoundException
   |
   v
Map DTO → Entity
   |
   v
Set Genre
   |
   v
Save Book
   |
   v
Map Entity → DTO
   |
   v
Return Response
```

This keeps the controller thin and makes the business logic easier to test.

---

# 🧪 Testing

The API can be tested using:

- Postman
- Swagger UI
- IntelliJ HTTP Client
- curl
- Unit tests
- Integration tests

---

## Important Book Creation Tests

Test the following:

- Valid book creation
- Duplicate ISBN
- Missing ISBN
- Missing title
- Missing author
- Missing genre
- Non-existent genre
- Negative total copies
- Negative available copies
- Available copies greater than total copies
- Invalid price
- Invalid field length

---

## Important Update Tests

Test:

- Valid update
- Non-existent book ID
- Non-existent genre ID
- Invalid total/available copies
- Attempt to modify protected fields such as ISBN
- Validation failures

---

## Important Search Tests

Test:

- No filters
- Search by title
- Search by author
- Search by ISBN
- Genre filtering
- `availableOnly = true`
- `availableOnly = false`
- Multiple filters together
- No matching results
- Pagination
- Sorting
- Page beyond the last page

---

## Important Delete Tests

Test:

- Soft delete existing book
- Soft delete non-existent book
- Verify soft-deleted book is excluded from search
- Permanent delete existing book
- Permanent delete non-existent book

---

## Important Security Tests

Test:

- Successful login
- Incorrect password
- Non-existent user
- Request without JWT
- Invalid JWT
- Expired JWT
- Customer accessing admin-only endpoint
- Admin accessing permitted endpoint

---

# ⚙️ Setup

## Prerequisites

Install:

- Java 21
- Maven
- Git
- IntelliJ IDEA or another Java IDE
- Configured relational database if not using an in-memory database

Check Java:

```bash
java -version
```

Check Maven:

```bash
mvn -version
```

---

# 📥 Clone the Project

```bash
git clone <repository-url>
cd library-management-system
```

Replace:

```text
<repository-url>
```

with the actual repository URL.

---

# 🗄️ Database Configuration

Configure the database in:

```text
src/main/resources/application.properties
```

Typical configuration:

```properties
spring.datasource.url=...
spring.datasource.username=...
spring.datasource.password=...

spring.jpa.hibernate.ddl-auto=update
```

Do not commit real passwords or JWT secrets to Git.

Use environment variables or local configuration for sensitive values.

---

# ▶️ Running the Application

Using Maven:

```bash
./mvnw spring-boot:run
```

For Windows:

```bash
mvnw.cmd spring-boot:run
```

Alternatively, run the main Spring Boot application class directly from IntelliJ IDEA.

Default development URL:

```text
http://localhost:8080
```

---

# 📖 API Documentation

If Swagger/OpenAPI is configured, the API documentation can be accessed at:

```text
http://localhost:8080/swagger-ui/index.html
```

Swagger can be used to:

- View available endpoints
- Inspect request parameters
- Test API requests
- View request/response models
- Test authenticated endpoints

---

# 🔒 Security Best Practices

The following practices should be followed:

- Never store plain-text passwords.
- Use BCrypt for password hashing.
- Keep JWT signing secrets outside source control.
- Validate JWT signatures before trusting claims.
- Use appropriate token expiration.
- Never expose passwords in response DTOs.
- Restrict protected endpoints using Spring Security.
- Use HTTPS in production.
- Configure CORS for trusted origins.
- Avoid exposing stack traces in production.
- Return appropriate `401` and `403` responses.

---

# 📌 Design Decisions

### Unique ISBN

ISBN is treated as a unique identifier for a book edition.

Duplicate ISBNs are rejected before creating a new book.

### ISBN is not editable

ISBN is treated as a protected identifying field during updates.

A separate update request DTO can exclude ISBN.

### Soft Delete

Normal deletion uses soft delete to preserve historical data.

The book remains in the database but:

```text
active = false
```

### Flexible Search

A single repository query supports combinations of:

```text
Search term
+
Genre
+
Availability
+
Pagination
+
Sorting
```

instead of creating a separate query for every possible combination.

### Business Logic in Services

Controllers handle HTTP-related concerns.

Services handle:

- Business validation
- Database interaction orchestration
- Entity relationships
- Business rules

### DTO-Based API

DTOs are used to keep API contracts separate from database entities.

### Stateless Authentication

JWT authentication is configured with:

```java
SessionCreationPolicy.STATELESS
```

The server does not maintain traditional HTTP sessions for authenticated requests.

---

# 🚀 Future Enhancements

Possible future improvements include:

- Book borrowing and return
- Member management
- Librarian management
- Due-date tracking
- Fine calculation
- Book reservations
- Waitlists
- Email notifications
- Refresh tokens
- Audit logging
- Permission-based authorization
- Automated integration testing
- Docker support
- CI/CD pipeline
- Production database deployment
- Centralized logging
- API rate limiting

---

# 🎯 Learning Objectives

This project demonstrates practical implementation of:

- REST API development
- Spring Boot
- Spring Data JPA
- Hibernate
- Entity relationships
- DTO design
- MapStruct
- Bean Validation
- Custom exceptions
- Global exception handling
- Pagination
- Sorting
- Dynamic filtering
- Transactions
- Spring Security
- JWT authentication
- BCrypt password hashing
- Role-based authorization
- CORS
- Layered architecture

---

# 👩‍💻 Author

**Charu N Bohra**

Java | Spring Boot | REST APIs | Spring Security | JWT | JPA

---

# 📄 License

This project is currently intended for educational and development purposes.

If the project is later published as an open-source project, an appropriate license such as MIT can be added.