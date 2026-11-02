// Mirrors BookReviewDTO. Real API: GET /api/reviews/book/{bookId} returns a
// PageResponseDTO { content: [...], pageNumber, totalPages, totalElements, last, ... }.
// Logged-in mock user = id 1 (John Doe).

export const reviews = [
  // ── Book 1: Clean Code (John returned it → loan 108, so he CAN review) ──
  {
    id: 301,
    userId: 1,
    userName: "John Doe",
    bookId: 1,
    bookTitle: "Clean Code",
    rating: 5,
    title: "Changed how I name things",
    reviewText:
      "The chapters on naming and small functions alone were worth it. Some Java examples feel dated, but the principles hold.",
    isVerifiedReader: true,
    isActive: true,
    helpfulCount: 4,
    createdAt: "2026-06-20T10:15:00",
    updatedAt: "2026-06-20T10:15:00",
  },
  {
    id: 302,
    userId: 2,
    userName: "Meera Kapoor",
    bookId: 1,
    bookTitle: "Clean Code",
    rating: 4,
    title: "Great, but read it slowly",
    reviewText:
      "Dense in places. I read one chapter a week alongside my project and it stuck much better.",
    isVerifiedReader: true,
    isActive: true,
    helpfulCount: 2,
    createdAt: "2026-10-02T18:40:00",
    updatedAt: "2026-10-02T18:40:00",
  },
  {
    id: 303,
    userId: 3,
    userName: "Rohan Jain",
    bookId: 1,
    bookTitle: "Clean Code",
    rating: 5,
    title: "Every developer should read this",
    reviewText:
      "Practical and opinionated in a good way. The refactoring case study near the end is excellent.",
    isVerifiedReader: true,
    isActive: true,
    helpfulCount: 1,
    createdAt: "2026-09-21T09:05:00",
    updatedAt: "2026-09-21T09:05:00",
  },
  {
    id: 304,
    userId: 4,
    userName: "Ananya Iyer",
    bookId: 1,
    bookTitle: "Clean Code",
    rating: 3,
    title: null, // title is optional
    reviewText:
      "Good advice overall, though some rules felt too strict for small projects.",
    isVerifiedReader: true,
    isActive: true,
    helpfulCount: 0,
    createdAt: "2026-08-11T14:30:00",
    updatedAt: "2026-08-14T08:00:00", // edited later
  },

  // ── Book 4: The Hobbit ──
  {
    id: 305,
    userId: 2,
    userName: "Meera Kapoor",
    bookId: 4,
    bookTitle: "The Hobbit",
    rating: 5,
    title: "A perfect adventure",
    reviewText:
      "Charming from the first page. Bilbo is one of the most likeable heroes in fantasy.",
    isVerifiedReader: true,
    isActive: true,
    helpfulCount: 6,
    createdAt: "2026-07-03T20:10:00",
    updatedAt: "2026-07-03T20:10:00",
  },
  {
    id: 306,
    userId: 5,
    userName: "Kabir Mehta",
    bookId: 4,
    bookTitle: "The Hobbit",
    rating: 4,
    title: "Slow middle, brilliant ending",
    reviewText:
      "The Mirkwood chapters dragged a little for me, but the last third is fantastic.",
    isVerifiedReader: true,
    isActive: true,
    helpfulCount: 1,
    createdAt: "2026-09-15T11:25:00",
    updatedAt: "2026-09-15T11:25:00",
  },

  // ── Book 5: Harry Potter (John returned it → loan 106, but has NOT reviewed yet) ──
  {
    id: 307,
    userId: 3,
    userName: "Rohan Jain",
    bookId: 5,
    bookTitle: "Harry Potter and the Philosopher's Stone",
    rating: 5,
    title: "Still magical",
    reviewText:
      "Re-read it as an adult and enjoyed it just as much. Easy, warm and funny.",
    isVerifiedReader: true,
    isActive: true,
    helpfulCount: 3,
    createdAt: "2026-08-28T16:00:00",
    updatedAt: "2026-08-28T16:00:00",
  },

  // Books 2, 3, 6, 8 have no reviews → tests the "No reviews yet" empty state
];
