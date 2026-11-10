import { Link } from "react-router";
import CurrentLoanCard from "./CurrentLoanCard";

const CurrentLoans = ({ loans }) => {
  if (loans.length === 0) {
    return (
      <div className="p-6 text-center text-gray-600">
        <p className="mb-3">No books on loan right now.</p>
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
        Books you're currently reading.
      </h3>
      <div>
        {/* List of current loans */}
        {loans.map((loan) => (
          <CurrentLoanCard key={loan.id} loan={loan} />
        ))}
      </div>
    </div>
  );
};

export default CurrentLoans;
