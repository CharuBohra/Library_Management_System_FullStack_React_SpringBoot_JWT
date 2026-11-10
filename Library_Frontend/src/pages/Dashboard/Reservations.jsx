import { Link } from "react-router";
import { formatDateTime } from "../../utils/formatDate";
import ReservationCard from "./ReservationCard";

// Work out the one line of text and its colour for a reservation
const getStatusLine = (r) => {
  if (r.status === "AVAILABLE") {
    if (r.hoursUntilExpiry <= 12) {
      return {
        text: `Collect within ${r.hoursUntilExpiry} hours`,
        className: "text-red-700 font-semibold",
      };
    }
    return {
      text: `Ready to collect by ${formatDateTime(r.availableUntil)}`,
      className: "text-green-700",
    };
  }
  return {
    text: `Position ${r.queuePosition} in queue`,
    className: "text-gray-600",
  };
};

// Ready books first (most urgent first), then the queue (lowest position first)
const sortReservations = (a, b) => {
  const aReady = a.status === "AVAILABLE";
  const bReady = b.status === "AVAILABLE";
  if (aReady && !bReady) return -1;
  if (!aReady && bReady) return 1;
  if (aReady && bReady) return a.hoursUntilExpiry - b.hoursUntilExpiry;
  return a.queuePosition - b.queuePosition;
};

const Reservations = ({ reservations }) => {
  if (reservations.length === 0) {
    return (
      <div className="p-6 text-center text-gray-600">
        <p className="mb-3">No active reservations.</p>
        <Link
          to="/books"
          className="font-semibold text-indigo-600 hover:underline"
        >
          Browse books
        </Link>
      </div>
    );
  }

  const sorted = [...reservations].sort(sortReservations);
  return (
    <div className="p-6">
      <h3 className="text-2xl font-bold text-gray-900 mb-4">
        Your reservations
      </h3>

      <div className="flex flex-col gap-3">
        {sorted.map((reservation) => {
          const line = getStatusLine(reservation);
          return (
            <ReservationCard
              key={reservation.id}
              line={line}
              reservation={reservation}
            />
          );
        })}
      </div>

      <div className="mt-4 text-right">
        <Link
          to="/my-reservations"
          className="font-semibold text-indigo-600 hover:underline"
        >
          View all reservations →
        </Link>
      </div>
    </div>
  );
};

export default Reservations;
