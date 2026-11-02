import { books } from "./books";

// Mirrors WishlistDTO. Real API: GET /api/wishlist/my returns a PageResponseDTO (size 10).
const bookById = (id) => books.find((b) => b.id === id);

export const wishlist = [
  {
    id: 401,
    userId: 1,
    userFullName: "John Doe",
    book: bookById(1),
    addedAt: "2026-10-28T19:20:00",
    notes: "Read before the team code review",
  },
  {
    id: 402,
    userId: 1,
    userFullName: "John Doe",
    book: bookById(5),
    addedAt: "2026-10-25T08:45:00",
    notes: "Gift idea for Riya",
  },
  {
    id: 403,
    userId: 1,
    userFullName: "John Doe",
    book: bookById(4),
    addedAt: "2026-10-20T21:05:00",
    notes: null,
  },
  {
    id: 404,
    userId: 1,
    userFullName: "John Doe",
    book: bookById(8),
    addedAt: "2026-10-12T13:10:00",
    notes: null,
  },
  {
    id: 405,
    userId: 1,
    userFullName: "John Doe",
    book: bookById(6),
    addedAt: "2026-10-03T17:55:00",
    notes: null,
  },
  {
    id: 406,
    userId: 1,
    userFullName: "John Doe",
    book: bookById(3),
    addedAt: "2026-09-28T10:30:00",
    notes: "After finishing Clean Code",
  },
];
