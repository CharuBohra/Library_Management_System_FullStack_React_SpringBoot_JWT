import { Link } from "react-router";
import ReadingHistoryCard from "./ReadingHistoryCard";

const ReadingHistory = ({ loans }) => {
  if (loans.length === 0) {
    return (
      <div className="p-6 text-center text-gray-600">
        <p className="mb-3">You haven't returned any books yet.</p>
        <Link
          to="/books"
          className="font-semibold text-indigo-600 hover:underline"
        >
          Browse books
        </Link>
      </div>
    );
  }
  return (
    <div className="p-6">
      <h3 className="text-2xl font-bold text-gray-900 mb-2">
        Books you've read.
      </h3>
      <div>
        {/* Returned Books */}
        {loans.map((loan) => (
          <ReadingHistoryCard key={loan.id} loan={loan} />
        ))}
      </div>
    </div>
  );
};

export default ReadingHistory;
