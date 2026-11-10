# 📚 ZoshBook – Library Management System

A full-stack application for running a subscription-based library: browse and borrow books, reserve titles that are on loan, track due dates, pay fines and manage memberships online.

- **Backend:** Java 21 · Spring Boot 4.1 · Spring Security (JWT) · JPA/Hibernate · MySQL · MapStruct · Razorpay
- **Frontend:** React 19 · Vite · React Router · Material UI · Tailwind CSS

> 🚧 **Work in progress – roughly 55% complete (Nov 2026).**
> The REST API is largely complete. The customer-facing UI is built on mock data; connecting it to the API (JWT login, data fetching) is the next phase. The admin UI, automated tests and deployment are still to come.

---

## 🧭 What it does

| Area | Customer | Admin |
|---|---|---|
| Catalogue | Browse, search and filter books; book details, reviews, wishlist | Manage books, genres, stock, damaged copies |
| Loans | Borrow (within plan limits), renew, return; see due dates and overdue status | Check out for a user, update overdue loans, search loans |
| Reservations | Join a queue for books on loan, cancel, see queue position | Fulfil reservations |
| Fines | See overdue / damage / loss fines and pay them online | Create and waive fines |
| Subscriptions | Choose a plan (books allowed, days per book), view usage, cancel | Manage plans |
| Account | Sign up, log in, forgot / reset password, profile | User list |

---

## 🏗️ Architecture

```text
┌──────────────────────────┐   HTTPS / JSON + JWT   ┌──────────────────────────────┐
│  React frontend (Vite)   │ ─────────────────────► │  Spring Boot REST API        │
│  localhost:5173          │ ◄───────────────────── │  localhost:8090              │
└──────────────────────────┘                        │  Controller → Service →      │
            ▲                                        │  Repository (JPA)            │
            │ redirect: /payment/result,             └──────────────┬───────────────┘
            │           /reset-password                             │
┌───────────┴──────────────┐                        ┌───────────────▼──────────────┐
│  Razorpay payment link   │ ◄── create / verify ── │  MySQL 8                     │
└──────────────────────────┘                        └──────────────────────────────┘
                                                     + SMTP for reset-password emails
```

- Stateless auth with JWT; roles `CUSTOMER` and `ADMIN`.
- Payments go through Razorpay payment links; Razorpay returns to the backend, which verifies and redirects to the frontend result page.

---

## 📁 Repository Structure

```text
.
├── Library_Backend/    # Spring Boot REST API  → see Library_Backend/README.md
├── Library_Frontend/   # React client          → see Library_Frontend/README.md
├── docs/               # Learning notes
└── README.md
```

---

## 🚀 Running Locally

### 1. Backend

Prerequisites: Java 21, Maven (or the included wrapper), MySQL 8.

1. Create a MySQL database.
2. Create `Library_Backend/.env` (git-ignored) with your own values for the variables referenced in `application.properties` – database credentials, JWT secret, mail credentials, Razorpay keys and the initial admin password. **Never commit this file.**
3. Run:

```bash
cd Library_Backend
./mvnw spring-boot:run
```

The API starts on **http://localhost:8090**. Full details: [Library_Backend/README.md](Library_Backend/README.md).

### 2. Frontend

Prerequisites: Node.js 20+, pnpm.

```bash
cd Library_Frontend
pnpm install
pnpm dev
```

The app opens on **http://localhost:5173**. It currently runs on mock data, so the backend isn't required yet. Full details: [Library_Frontend/README.md](Library_Frontend/README.md).

---

## 📊 Progress

| Area | Status |
|---|---|
| Backend API (auth, catalogue, loans, reservations, fines, subscriptions, payments) | 🟢 ~80% – known gaps listed in the backend README |
| Frontend – customer screens (13) | 🟢 Built on mock data |
| Frontend ↔ API integration (axios, JWT, protected routes) | ⬜ Next |
| Frontend – admin area | ⬜ Not started |
| Automated tests (JUnit/Mockito, Vitest/RTL) | ⬜ Manual Postman testing only so far |
| Deployment (Docker, CI/CD, hosting) | ⬜ Not started |

---

## 🗺️ Roadmap

1. **Frontend polish** – shared components on older pages, Dashboard data, mobile navigation.
2. **API integration** – API client, login and token handling, protected routes, replace mock data page by page.
3. **Backend hardening** – scheduled jobs (overdue loans, subscription expiry, reservation holds), Razorpay webhooks, validation on all request DTOs, Flyway migrations.
4. **Admin UI.**
5. **Tests** – service unit tests and integration tests; frontend component tests.
6. **Deployment** – Docker, GitHub Actions, cloud hosting.

---

## 🔐 Security Notes

- Secrets are read from environment variables / a git-ignored `.env`; none are committed.
- Passwords are hashed with BCrypt; JWTs expire after 1 hour (no refresh tokens yet).
- Users can only act on their own loans, fines and subscriptions; admin endpoints are role-protected.

---

## 👩‍💻 Author

Built by **Charu Bohra** as a full-stack learning and portfolio project.
