import { Button } from "@mui/material";
import { AccessTimeFilled } from "@mui/icons-material";
import { formatDate } from "../../utils/formatDate";
import { Link } from "react-router";

const ReadingHistoryCard = ({ loan }) => {
  return (
    <div className="flex items-center justify-between p-6 border border-gray-200 rounded-2xl">
      <div className="flex items-center space-x-4 flex-1">
        <div className="">
          <img
            src={loan.bookCoverImageUrl}
            alt={loan.bookTitle}
            className="w-16 h-24 rounded-lg"
          />
        </div>
        <div className="flex-1">
          <h4 className="text-lg font-bold text-gray-900 mb-1">
            {loan.bookTitle}
          </h4>
          <p className="text-gray-600 mb-2">{loan.bookAuthor}</p>
          <div className="flex items-center space-x-4 text-sm">
            <div className="flex items-center space-x-1 text-sm">
              <AccessTimeFilled sx={{ fontSize: 16 }} />
              <span>Returned: {formatDate(loan.returnDate)}</span>
            </div>
          </div>
        </div>
      </div>
      <div>
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

export default ReadingHistoryCard;
