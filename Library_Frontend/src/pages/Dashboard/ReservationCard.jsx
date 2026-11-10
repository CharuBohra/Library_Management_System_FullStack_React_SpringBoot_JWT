import { Link } from "react-router";
import GetStatusChip from "./GetStatusChip";

const ReservationCard = ({ line, reservation }) => {
  return (
    <div className="flex flex-wrap items-center justify-between gap-4 p-5 border border-gray-200 rounded-2xl">
      <div className="flex flex-col gap-1 min-w-0">
        <div className="flex flex-wrap items-center gap-2">
          <h4 className="text-lg font-bold text-gray-900">
            {reservation.bookTitle}
          </h4>
          <GetStatusChip status={reservation.status} />
        </div>
        <p className="text-gray-600">{reservation.bookAuthor}</p>
        <p className={`text-sm ${line.className}`}>{line.text}</p>
      </div>

      <Link
        to={`/books/${reservation.bookId}`}
        className="font-semibold text-indigo-600 hover:underline"
      >
        View book
      </Link>
    </div>
  );
};

export default ReservationCard;
