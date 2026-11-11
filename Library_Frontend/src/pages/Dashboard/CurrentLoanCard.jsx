import { AccessTimeFilled } from "@mui/icons-material";
import GetStatusChip from "./GetStatusChip";
import { Button, Chip } from "@mui/material";
import { formatDate } from "../../utils/formatDate";
import { Link } from "react-router";

const CurrentLoanCard = ({ loan }) => {
  return (
    <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 p-4 sm:p-6 border border-gray-200 rounded-2xl">
      <div className="flex items-center gap-4 flex-1 min-w-0">
        <div className="shrink-0">
          <img
            src={loan.bookCoverImageUrl}
            alt={loan.bookTitle}
            className="w-16 h-24 rounded-lg object-cover bg-gray-100"
          />
        </div>
        <div className="flex-1 min-w-0">
          <h4 className="text-lg font-bold text-gray-900 mb-1">
            {loan.bookTitle}
          </h4>
          <p className="text-gray-600 mb-2">{loan.bookAuthor}</p>
          <div className="flex flex-wrap items-center gap-2 text-sm">
            <div className="flex items-center space-x-1 text-sm">
              <AccessTimeFilled sx={{ fontSize: 16 }} />
              <span>Due: {formatDate(loan.dueDate)}</span>
            </div>
            <GetStatusChip status={loan.status} />
            <Chip
              label={`${loan.remainingDays > 0 ? loan.remainingDays : loan.overdueDays} days ${loan.remainingDays > 0 ? "remaining" : "overdue"}`}
              size="small"
              variant="outlined"
            />
          </div>
        </div>
      </div>
      <div className="self-end sm:self-auto">
        <Button
          variant="outlined"
          component={Link}
          to={`/books/${loan.bookId}`}
        >
          View
        </Button>
      </div>
    </div>
  );
};

export default CurrentLoanCard;
