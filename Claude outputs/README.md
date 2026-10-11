# 📚 Library Management System – Frontend

> 🚧 **Work in progress.** This is the React client for the [Library Management System backend](../Library_Backend/README.md). The project is set up and the first dashboard components are in place; feature screens are being built step by step.

---

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| React 19 | UI library |
| Vite | Dev server and build tool |
| Material UI (MUI) | Component library |
| Tailwind CSS 4 | Utility styling |
| ESLint | Linting |
| pnpm | Package manager |

**Planned additions:** `react-router-dom` (routing and protected routes), `axios` (API client with a JWT interceptor).

---

## 🚀 Getting Started

### Prerequisites
- Node.js 20+
- pnpm (`npm install -g pnpm`)
- The backend running on `http://localhost:8090` (see the backend README)

### Install and run

```bash
pnpm install
pnpm dev
```

The app runs at **http://localhost:5173**. This origin is already allowed by the backend's CORS settings (`cors.allowed-origins`), and it's where the backend sends password-reset links and payment redirects (`app.frontend.url`).

### Other scripts

| Command | Purpose |
|---|---|
| `pnpm build` | Production build to `dist/` |
| `pnpm preview` | Serve the production build locally |
| `pnpm lint` | Run ESLint |

### Environment (planned)

A `.env` file in this folder (git-ignored) will hold:

```properties
VITE_API_URL=http://localhost:8090
```

---

## 📁 Current Structure

```text
src/
├── assets/
├── pages/
│   └── Dashboard/
│       ├── Dashboard.jsx
│       ├── StateCard.jsx
│       └── StateConfig.jsx
├── App.jsx
├── main.jsx
└── index.css
```

---

## 🗺️ Roadmap

- [x] Project setup (React + Vite, MUI, Tailwind)
- [x] Dashboard layout and stat cards (initial)
- [ ] Routing, API client and auth context
- [ ] Auth: sign up, log in, log out, forgot and reset password
- [ ] Books: browse, search, details
- [ ] Customer: my loans (checkout, renew, return), my fines (pay), reservations, wishlist
- [ ] Subscriptions: plans, subscribe, payment result page (`/payment/result`)
- [ ] Admin: book management, users, loans, fines, payments, reservations

---

## 🔗 Backend Contract Notes

- Auth uses a JWT in the `Authorization: Bearer <token>` header; tokens expire after 1 hour.
- Every error response has the shape `{ "message": "...", "status": false }`.
- `401` means log in again; `403` means not allowed.
- Routes the backend will redirect to:
  - `/reset-password?token=<token>`
  - `/payment/result?status=SUCCESS|FAILURE&paymentId=<id>`
