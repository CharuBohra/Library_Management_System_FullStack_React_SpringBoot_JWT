export const FINE_TYPES = {
  OVERDUE: { label: "Overdue", classes: "bg-amber-50 text-amber-700" },
  DAMAGE: { label: "Damage", classes: "bg-pink-50 text-pink-700" },
  LOSS: { label: "Loss", classes: "bg-gray-100 text-gray-700" },
  PROCESSING: { label: "Processing", classes: "bg-blue-50 text-blue-700" },
};

export const FINE_STATUSES = {
  PENDING: { label: "Pending", classes: "bg-red-50 text-red-700" },
  PARTIALLY_PAID: {
    label: "Partially paid",
    classes: "bg-amber-50 text-amber-700",
  },
  PAID: { label: "Paid", classes: "bg-green-50 text-green-700" },
  WAIVED: { label: "Waived", classes: "bg-blue-50 text-blue-700" },
};
