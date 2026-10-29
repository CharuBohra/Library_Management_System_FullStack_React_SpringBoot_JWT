import { Person } from "@mui/icons-material";
import { Button } from "@mui/material";

const BookCard = ({ book }) => {
  const handleViewDetails = () => {
    console.log("Detail panel clicked for : ", book.title);
  };
  return (
    <div
      className="group bg-white rounded-xl shadow-md hover:shadow-xl transition-all duration-300 overflow-hidden 
        cursor-pointer border border-gray-100 hover:-translate-y-1"
    >
      {/* Book COver Image  */}
      <div className="relative aspect-2/3 bg-linear-to-br from-indigo-100 to-purple-100 overflow-hidden">
        <img
          src={book.coverImageUrl || "/book-placeholder.png"}
          alt={book.title}
          onError={(e) => {
            e.currentTarget.src = "/book-placeholder.png";
          }}
          className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
        />
      </div>

      {/* Book details */}
      <div className="p-5">
        <h3 className="text-lg text-gray-500 font-bold mb-2 line-clamp-2 group-hover:text-indigo-600 transition-colors">
          {book.title}
        </h3>
        {/* Author */}
        <div className="flex items-center space-x-2 text-gray-600 mb-3">
          <Person sx={{ fontSize: 16 }} />
          <span className="text-sm line-clamp-1">{book.author}</span>
        </div>

        {/* ISBN and availability */}
        <div className="flex items-center justify-between text-xs text-gray-500 mb-4">
          <span>ISBN : {book.isbn}</span>
          <span>
            {book.availableCopies}/{book.totalCopies} copies
          </span>
        </div>

        {/* Description */}
        {book.description && (
          <p className="text-sm text-gray-600 mb-4 line-clamp-2">
            {book.description}
          </p>
        )}

        {/* Action Buttons */}
        <div className="flex gap-2">
          <Button
            variant="outlined"
            fullWidth
            onClick={handleViewDetails}
            sx={{
              textTransform: "none",
              borderColor: "#4F46E5",
              color: "#4F46E5",
              fontWeight: 600,
              "&:hover": {
                borderColor: "#4338CA",
                bgcolor: "#EEF2FF",
              },
            }}
          >
            View
          </Button>
        </div>
      </div>
    </div>
  );
};

export default BookCard;
