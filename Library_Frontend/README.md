# 📚 ZoshBook – Library Management System (Frontend)

React client for the [Library Management System backend](../Library_Backend/README.md).

> 🚧 **Status (Nov 2026): UI complete on mock data, not yet connected to the API.**
> All 13 customer screens are built and routed, using mock data shaped exactly like the backend DTOs. The next phase is the API client, JWT login and protected routes. The admin area hasn't started.

---

## 🛠️ Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| React | 19 | UI library |
| Vite | 8 | Dev server and build tool |
| React Router | 8 | Routing (`BrowserRouter`, nested layout routes, URL/query params) |
| Material UI (MUI) | 9 | Components (Button, Select, Tabs, Rating, LinearProgress, Avatar) and icons |
| Tailwind CSS | 4 | Utility styling (layout, spacing, colours) |
| ESLint | 10 | Linting |
| pnpm | — | Package manager |

**Styling convention:** MUI components are styled with `sx`; plain HTML elements with Tailwind classes. One system per element.

---

## 🚀 Getting Started

### Prerequisites
- Node.js 20+
- pnpm (`npm install -g pnpm`)
- *(From the API phase onwards)* the backend running on `http://localhost:8090`

### Install and run

```bash
pnpm install
pnpm dev
```

The app runs at **http://localhost:5173**. The backend already allows this origin (`cors.allowed-origins`) and redirects here for password resets and payment results (`app.frontend.url`).

### Scripts

| Command | Purpose |
|---|---|
| `pnpm dev` | Start the dev server with hot reload |
| `pnpm build` | Production build to `dist/` |
| `pnpm preview` | Serve the production build locally |
| `pnpm lint` | Run ESLint |

### Environment (API phase)

A git-ignored `.env` in this folder will hold:

```properties
VITE_API_URL=http://localhost:8090
```

---

## 🖥️ Screens

| Route | Page | Status |
|---|---|---|
| `/` | Dashboard – stats, reading goal, tabs | ✅ UI (older mock data, to be updated) |
| `/books` | Browse Books – search, genre filter, availability, sort | ✅ UI |
| `/books/:id` | Book Details – info, availability, borrow / reserve / wishlist, rating summary, reviews | ✅ UI |
| `/my-loans` | My Loans – status tabs, due dates, renew / return | ✅ UI |
| `/my-reservations` | My Reservations – stats, tabs, queue position, collect-by, cancel / reserve again | ✅ UI |
| `/wishlist` | Wishlist – card grid, notes, remove, empty state | ✅ UI |
| `/my-fines` | My Fines – outstanding banner, totals, filterable table, pay | ✅ UI |
| `/subscriptions` | Subscriptions – current plan, usage, plan cards | ✅ UI |
| `/profile` | Profile – user header, quick stats, account details, security | ✅ UI |
| `/login` | Login / Sign up (one page, mode toggle) | ✅ UI |
| `/forgot-password` | Request a reset link | ✅ UI |
| `/reset-password?token=…` | Set a new password | ✅ UI |
| `/payment/result?status=…&paymentId=…` | Razorpay return page (success / failure / processing) | ✅ UI |
| `/settings` | — | ⬜ Placeholder (no backend endpoint yet) |

`/login`, `/forgot-password` and `/reset-password` sit **outside** the main layout (no sidebar). All other routes render inside `UserLayout` (sidebar + navbar).

---

## 📁 Project Structure

```text
src/
├── data/                  # Mock data, shaped like the backend DTOs
│   ├── books.js  genres.js  loans.js  reservations.js  reviews.js
│   ├── wishlist.js  fines.js  plans.js  subscription.js  payments.js  user.js
├── pages/
│   ├── components/        # Shared: PageContainer, PageHeader, ScrollTop
│   ├── UserLayout/        # Layout, Navbar, Sidebar, navigation config
│   ├── Dashboard/
│   ├── Book/              # Browse Books (BookCard, GenreFilter)
│   ├── BookDetails/
│   ├── MyLoans/
│   ├── MyReservations/
│   ├── Wishlist/
│   ├── MyFines/           # FineRow, fineConfig
│   ├── Subscription/      # PlanCard
│   ├── UserProfile/
│   ├── UserAuthentication/  # Login, ForgotPassword, ResetPassword
│   └── Payments/          # PaymentResult
├── utils/                 # formatDate, formatMoney, dateUtils, getInitials
├── App.jsx                # Routes
└── main.jsx               # Router + app entry
```

---

## 🧩 Conventions

- **Mock data mirrors the DTOs.** Field names and enum values (`CHECKED_OUT`, `PARTIALLY_PAID`, …) are copied exactly from the backend, so switching to the API only changes where data comes from.
- **Derive, don't store.** Filtered lists, totals, counts and averages are calculated from data and state on each render, not kept in extra state.
- **Config objects for enums.** Labels, colours and icons per status live in one lookup object (e.g. `FINE_STATUSES`, `RESULT_CONFIG`).
- **Arrays + `.map()`** for repeated UI (stat cards, detail grids, tabs, filter chips).
- **Controlled inputs** for every form field; one change handler per multi-field form.
- **Backend rules are reused, not re-invented** – e.g. `canBeCancelled`, `isValid`, the renew rule from `BookLoan.canRenew()`.
- Dates are formatted only for display (`formatDate`, `formatDateTime`); money with `formatMoney`.

---

## 🔗 Backend Contract Notes

- Auth: JWT in `Authorization: Bearer <token>`; tokens expire after 1 hour.
- Errors: `{ "message": "...", "status": false }`. `401` → log in again, `403` → not allowed.
- Backend redirects to:
  - `/reset-password?token=<token>`
  - `/payment/result?status=<SUCCESS|FAILURE|PENDING|PROCESSING>&paymentId=<id>`
- Paged endpoints return `PageResponseDTO` (`content`, `pageNumber`, `totalPages`, `totalElements`, `last`).
- Check boolean field names on real responses (`isExpired`, `isValid`) – Lombok/Jackson may drop the `is` prefix.

---

## 🗺️ Roadmap

- [x] Project setup (React + Vite, MUI, Tailwind, React Router)
- [x] Layout: sidebar, navbar, nested routes, scroll-to-top
- [x] All customer screens on mock data
- [x] Auth screens: login / sign up, forgot and reset password, payment result
- [ ] Polish pass: shared header/stat cards on older pages, Dashboard data, mobile drawer, wire "View" buttons to `/books/:id`
- [ ] API client (`axios`, `VITE_API_URL`) with a JWT interceptor
- [ ] Auth context, protected routes, logout, 401 handling
- [ ] Replace mock data page by page; loading, error and empty states
- [ ] Admin area: books, genres, users, loans, reservations, fines, plans, payments
- [ ] Tests (Vitest + React Testing Library)

---

## 🔗 Related

- Backend: [`../Library_Backend`](../Library_Backend/README.md)
- Project overview: [`../README.md`](../README.md)
