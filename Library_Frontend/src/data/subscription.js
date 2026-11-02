// Mirrors SubscriptionDTO. Real API: GET /api/subscriptions/active.
// "Today" = 2026-11-02. LocalDate fields (startDate/endDate) have no time part.

export const activeSubscription = {
  id: 501,
  userId: 1,
  userName: "John Doe",
  userEmail: "john.doe@example.com",
  planId: 2,
  planName: "Standard",
  planCode: "STANDARD",
  price: 999.0, // placeholder – use your real plan price
  currency: "INR",
  startDate: "2026-10-01",
  endDate: "2027-10-01", // durationDays 365
  active: true,
  maxBooksAllowed: 5,
  maxDaysPerBook: 21,
  autoRenew: false,
  cancelledAt: null,
  cancellationReason: null,
  notes: null,
  createdAt: "2026-10-01T09:00:00",
  updatedAt: "2026-10-01T09:00:00",
  daysRemaining: 333,
  isExpired: false,
  isValid: true,
};

// Swap this in to test the "Subscribe to borrow" state
export const noSubscription = null;
