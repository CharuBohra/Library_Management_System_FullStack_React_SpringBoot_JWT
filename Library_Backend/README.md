# 📚 Library Management System – Backend

A REST API for running a subscription-based library: books and stock, loans and renewals, reservations, fines, subscriptions and online payments.

Built with **Java 21** and **Spring Boot 4.1** using a layered architecture (Controller → Service → Repository), DTOs with MapStruct, Bean Validation, Spring Security with JWT, role-based access, and Razorpay payment links.

---

## 📌 Features

### Catalogue
- 📚 Book CRUD, bulk create, soft delete and guarded hard delete
- 🏷️ Genres with parent/child hierarchy
- 🔍 Paged search and advanced search, book statistics
- ⭐ Reviews and ❤️ wishlist

### Circulation
- 📖 Checkout (self-service and admin-on-behalf), check-in and renewal
- 🧮 Stock integrity: `available + on loan + damaged = total` is maintained on every operation
- 🛠️ Damaged-copy handling: return as **GOOD / DAMAGED / LOST**, then repair or write off
- 📅 Reservations with a queue; only the first in the queue can be fulfilled

### Fines
- ⏰ Automatic fines on return: **OVERDUE** (per day, capped at the book price), **DAMAGE** (% of price), **LOSS** (full price)
- 🔁 One fine per loan per type (no duplicates); idempotent "mark as paid"
- 🚫 Checkout and renewal blocked while a fine is unpaid
- 🧾 Admin waive with reason

### Subscriptions and payments
- 📋 Subscription plans (books allowed, days per book, price)
- 🔄 One active subscription per user (a second subscribe returns 409)
- 💳 Razorpay payment links for fines and memberships
- ↪️ Payment callback verifies with Razorpay and redirects to the frontend result page

### Security
- 🔐 JWT authentication (stateless), BCrypt password hashing
- 🛡️ Roles: `ADMIN`, `CUSTOMER`, enforced by URL rules **and** `@PreAuthorize`
- 👤 Ownership checks before state checks (users can only act on their own loans, fines and subscriptions)
- 🧱 Mass-assignment protection: client-supplied `id`, stock counts, roles and start dates are ignored
- 🔒 Optimistic locking (`@Version`) on books, so two users can't take the last copy
- 🔑 Forgot / reset password by email (time-limited token)
- 🌐 CORS and frontend URL driven by configuration

---

## 🛠️ Technology Stack

| Technology | Purpose |
|---|---|
| Java 21 | Language |
| Spring Boot 4.1 (Web MVC, Data JPA, Security, Validation, Mail) | Framework |
| Hibernate | ORM |
| MySQL 8 | Database |
| JJWT 0.13 | JWT creation and validation |
| BCrypt | Password hashing |
| MapStruct 1.6 | Entity ↔ DTO mapping |
| Lombok | Boilerplate reduction |
| Razorpay Java SDK | Payment links and verification |
| Maven | Build |

---

## 🏗️ Architecture

```text
Client (React / Postman)
   │  JWT in Authorization header
   ▼
Security filter chain ── JwtFilter → URL role rules → 401 / 403 JSON
   ▼
Controller  (thin: validation + HTTP status)
   ▼
Service     (business rules, @Transactional, @PreAuthorize, ownership checks)
   ├──► Mapper (MapStruct)
   ▼
Repository  (Spring Data JPA)
   ▼
MySQL

GlobalExceptionHandler → consistent error JSON for every failure
```

**Design notes**

- Services call each other through private `doX` methods, so `@Transactional` and `@PreAuthorize` aren't bypassed by self-invocation.
- Business rules that need the database live in services; Bean Validation handles request shape only.
- Order of checks: authentication → authorisation → validation → ownership → state.

---

## 📏 Business Rules

| Area | Rule |
|---|---|
| Stock | `availableCopies` and `damagedCopies` are read-only to clients and derived from `totalCopies` and loan activity |
| Checkout | Requires an active subscription, an available active book, no duplicate active loan, under the plan's book limit, no overdue loans and no unpaid fines |
| Loan period | Defaults to the plan's `maxDaysPerBook`; longer requests are rejected (400) |
| Renewal | Owner only, not overdue, before the due date, at most 2 renewals, no pending reservations on the book, no unpaid fines, active subscription |
| Return | Records real overdue days; GOOD → available, DAMAGED → damaged stock, LOST → removed from stock |
| Fines | Overdue = rate × days (capped at book price); Damage = % of price; Loss = price; unknown price uses a configured default |
| Reservations | Queue per book; fulfilment only for queue position 1 and when a copy is available |
| Hard delete | Blocked when a book has any loan history (409) |
| Subscriptions | One active subscription per user; start date is set by the server |

---

## ⚠️ Error Responses

Every error, including security errors, returns the same shape:

```json
{ "message": "Human-readable reason", "status": false }
```

| Status | When |
|---|---|
| 400 | Validation failure, malformed JSON, bad parameter, loan period too long |
| 401 | Missing, invalid or expired JWT |
| 403 | Authenticated but not allowed (role or ownership) |
| 404 | Resource not found |
| 405 | Wrong HTTP method |
| 409 | Business conflict: duplicate fine, unpaid fines, no stock, active subscription exists, concurrent update, data integrity |
| 500 | Unexpected error (logged server-side; no stack trace returned) |

Creates return **201**; other successful calls return **200**.

---

## 🚀 Getting Started

### Prerequisites
- Java 21, Maven (or the included `mvnw`)
- MySQL 8 with an empty database: `CREATE DATABASE library_management_system;`
- A Gmail app password (for reset emails) and Razorpay **test** keys

### 1. Environment variables

Create a `.env` file in `Library_Backend/` (it is git-ignored). **Never commit real values.**

```properties
SQL_USERNAME=
SQL_PASSWORD=
JWT_SECRET=            # long random string (at least 32 bytes, base64)
MAIL_USERNAME=
MAIL_PASSWORD=
RAZORPAY_KEY_ID=
RAZORPAY_KEY_SECRET=
ADMIN_EMAIL=
ADMIN_PASSWORD=
```

No spaces around `=`. In IntelliJ, point the run configuration's **Environment file** at `.env`.

### 2. Other settings (`application.properties`)

| Property | Default | Purpose |
|---|---|---|
| `server.port` | `8090` | API port |
| `app.frontend.url` | `http://localhost:5173` | Reset-password links and payment redirects |
| `cors.allowed-origins` | `http://localhost:5173` | Comma-separated allowed origins |
| `razorpay.callback.base-url` | `http://localhost:8090` | Base URL Razorpay redirects back to |
| `jwt.expiration` | `3600000` | Token lifetime (ms) |
| `app.fines.overdue-per-day` | `10` | Overdue fine per day |
| `app.fines.damage-percentage` | `50` | Damage fine as % of book price |
| `app.fines.default-book-price` | `500` | Used when a book has no price |

### 3. Run

```bash
./mvnw spring-boot:run
```

On first start, an admin user is created from `ADMIN_EMAIL` / `ADMIN_PASSWORD` if it doesn't exist.

---

## 🔗 API Overview

Base URL: `http://localhost:8090`. All `/api/**` endpoints need `Authorization: Bearer <jwt>` unless stated.

### Auth (public)
| Method | Endpoint | Notes |
|---|---|---|
| POST | `/auth/signup` | Always creates a `CUSTOMER` |
| POST | `/auth/login` | Returns `jwt` and user |
| POST | `/auth/forgot-password?email=` | Emails a reset link |
| POST | `/auth/reset-password` | `{ token, password }` |

### Books
| Method | Endpoint | Role |
|---|---|---|
| GET | `/api/books`, `/api/books/{id}`, `/api/books/isbn/{isbn}`, `/api/books/stats` | Any |
| POST | `/api/books/search` | Any |
| POST | `/api/books`, `/api/books/create/bulk` | Admin |
| PUT | `/api/books/{id}` | Admin |
| DELETE | `/api/books/{id}/soft-delete`, `/api/books/{id}/permanent` | Admin |
| POST | `/api/books/{id}/damaged-copies/repair`, `/write-off` | Admin |

### Genres
| Method | Endpoint | Role |
|---|---|---|
| GET | `/api/genres`, `/{id}`, `/top-level-genres`, `/count`, `/{id}/book-count` | Any |
| POST / PUT / DELETE | `/api/genres/create`, `/{id}`, `/{id}/soft-delete`, `/{id}/hard` | Admin |

### Loans
| Method | Endpoint | Role |
|---|---|---|
| POST | `/api/book-loans/checkout` | Customer |
| POST | `/api/book-loans/checkout/user/{userId}` | Admin |
| POST | `/api/book-loans/checkin` | Owner |
| POST | `/api/book-loans/renew` | Owner |
| GET | `/api/book-loans/my` | Customer |
| POST | `/api/book-loans/search`, `/update-overdue` | Admin |

### Reservations
| Method | Endpoint | Role |
|---|---|---|
| POST | `/api/reservations` | Customer |
| POST | `/api/reservations/create/{userId}` | Admin |
| PUT | `/api/reservations/{id}/cancel` | Owner |
| PUT | `/api/reservations/{id}/fulfill` | Admin |
| GET | `/api/reservations/my` / `/api/reservations` | Customer / Admin |

### Fines
| Method | Endpoint | Role |
|---|---|---|
| GET | `/api/fines/my` | Customer |
| POST | `/api/fines/{id}/pay` | Owner (returns payment link) |
| POST | `/api/fines`, `/api/fines/waive` | Admin |
| GET | `/api/fines` | Admin |

### Subscriptions and payments
| Method | Endpoint | Role |
|---|---|---|
| GET | `/api/subscription-plans` | Any |
| POST / PUT / DELETE | `/api/subscription-plans`, `/{id}` | Admin |
| POST | `/api/subscriptions/subscribe` | Customer (returns payment link) |
| GET | `/api/subscriptions/active` | Customer |
| POST | `/api/subscriptions/{id}/cancel` | Owner |
| GET | `/api/subscriptions` | Admin |
| POST | `/api/subscriptions/deactivate-expired` | Admin |
| GET | `/api/payments` | Admin |
| POST | `/api/payments/verify` | Authenticated |
| GET | `/api/payments/payment_success/{paymentId}` | Public (Razorpay callback) |

### Users, reviews, wishlist
| Method | Endpoint | Role |
|---|---|---|
| GET | `/api/user/profile` / `/api/user/list` | Any / Admin |
| POST / PUT / DELETE | `/api/reviews`, `/{reviewId}` | Customer |
| GET | `/api/reviews/book/{bookId}` | Any |
| POST / DELETE / GET | `/api/wishlist/add/{bookId}`, `/remove/{bookId}`, `/my` | Customer |

---

## 💳 Payment Flow

```text
1. POST /api/fines/{id}/pay  or  POST /api/subscriptions/subscribe
       → Payment row (PENDING → PROCESSING) + Razorpay payment link (checkoutUrl)
2. User pays on Razorpay
3. Razorpay redirects → GET /api/payments/payment_success/{paymentId}?razorpay_payment_id=...
4. Backend verifies with Razorpay → SUCCESS / FAILURE
       → success event marks the fine PAID or activates the subscription
5. 302 redirect → {app.frontend.url}/payment/result?status=SUCCESS|FAILURE&paymentId=...
```

Test mode: use Razorpay **Netbanking → mock bank page → Success / Failure**.

---

## 🧪 Testing

- Manual end-to-end testing with a Postman collection (collection variables `base_url`, `adminToken`, `custToken`).
- Covered: auth and 401/403 shapes, stock invariants, checkout/check-in/renew rules, fine creation, payment and waiver, reservation queue, payment callback edge cases (missing, blank and fake payment ids), mass-assignment attempts on signup and subscribe, duplicate-subscription guard.
- Automated tests: see *Future work*.

---

## 🚧 Known Limitations

- **No payment webhook.** Failed or abandoned payments stay `PROCESSING`; only the redirect callback updates status.
- **Callback not fully idempotent** for subscriptions; re-opening a success URL re-runs verification. The payment id in the URL isn't cross-checked against Razorpay's notes.
- **No scheduled jobs.** Marking loans overdue and expiring subscriptions run through admin endpoints, not automatically.
- **Reserved books aren't held on return**; any eligible user can check out a returned copy before the queue is fulfilled.
- **Forgot-password response reveals whether an email is registered.**
- Customers self-declare return condition (DAMAGED / LOST); no staff-only check-in.
- **No refresh tokens or logout**; JWTs expire after 1 hour.
- Unpaid subscription attempts remain as inactive rows.
- Schema managed by `ddl-auto=update` rather than migrations.

---

## 🔭 Future Work

- Unit and integration tests (JUnit 5, Mockito, Testcontainers) for loan, fine and payment services
- Razorpay webhooks, idempotent callbacks and amount verification
- Scheduled jobs for overdue loans, subscription expiry and reservation holds
- Refresh tokens and logout
- Flyway migrations, unique constraint on `users.email`
- Fetch-join or entity-graph tuning for N+1 queries; replace `@Data` on entities
- Remove the unused `spring-boot-starter-data-jdbc` dependency

---

## 📁 Related

- Frontend: `../Library_Frontend` (React + Vite)
