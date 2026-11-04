import { Button } from "@mui/material";
import { formatDate } from "../../utils/formatDate";
import { useNavigate } from "react-router";
import { Favorite } from "@mui/icons-material";

const WishlistCard = ({ wishlistItem, onRemove }) => {
  const navigate = useNavigate();
  const isAvailable = wishlistItem.book.availableCopies > 0;
  return (
    <div
      className="bg-white rounded-xl shadow-md hover:shadow-xl transition-all duration-300 hover:-translate-y-2
                flex flex-col gap-3 p-5 overflow-hidden border border-gray-200 h-full"
    >
      <div className="relative p-4 pb-0">
        <button
          aria-label={`View ${wishlistItem.book.title}`}
          onClick={() => navigate(`/books/${wishlistItem.book.id}`)}
        >
          {wishlistItem.book.coverImageUrl ? (
            <div className="relative aspect-2/3 bg-linear-to-br from-indigo-100 to-purple-100 overflow-hidden">
              <img
                src={wishlistItem.book.coverImageUrl || "/book-placeholder.png"}
                alt={wishlistItem.book.title}
                onError={(e) => {
                  e.currentTarget.src = "/book-placeholder.png";
                }}
                className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
              />
            </div>
          ) : (
            <span className="relative aspect-2/3 bg-linear-to-br from-indigo-100 to-purple-100 overflow-hidden">
              {wishlistItem.book.title}
            </span>
          )}
        </button>
        <button
          aria-label={`Remove ${wishlistItem.book.title} from Wishlist`}
          onClick={() => onRemove(wishlistItem.book.id)}
          className="absolute top-6 right-6 w-10 h-10 rounded-full bg-white shadow-md flex items-center 
          justify-center text-pink-600 hover:bg-pink-50"
        >
          <Favorite sx={{ fontSize: 20 }} />
        </button>
      </div>
      <div className="flex flex-col flex-1">
        <span className="font-bold text-xl text-gray-900 mt-1">
          {wishlistItem.book.title}
        </span>
        <span className="text-base text-gray-500 mt-1">
          {wishlistItem.book.author}
        </span>
        {isAvailable ? (
          <span className="inline-flex self-start items-start gap-1.5 px-2 py-1 rounded-full text-xs text-green-700 bg-green-50 mt-2 min-w-0">
            {wishlistItem.book.availableCopies} available
          </span>
        ) : (
          <span className="inline-flex self-start items-start gap-1.5 px-2 py-1 rounded-full text-xs text-amber-700 mt-2 bg-amber-100 min-w-0">
            On loan - join the queue
          </span>
        )}
        {wishlistItem.notes && (
          <div className="bg-gray-50 rounded-lg text-gray-700 mt-2 p-2 text-sm space-y-2">
            <span className="italic">"{wishlistItem.notes}"</span>
          </div>
        )}
        <div>
          <span className="text-sm text-gray-400 ">
            Added {formatDate(wishlistItem.addedAt)}
          </span>
        </div>

        <Button
          variant={isAvailable ? "contained" : "outlined"}
          fullWidth
          //   onClick={() => handleReserveAgain()}
          sx={{
            height: "50px",
            textTransform: "none",
            fontWeight: 600,
            mt: "auto",
            ...(isAvailable
              ? {
                  bgcolor: "#4F46E5",
                  "&:hover": {
                    bgcolor: "#4338CA",
                    //   bgcolor: "rgba(102,126,234,0.05)",
                  },
                }
              : {
                  bgcolor: "white",
                  color: "#4F46E5",
                  "&:hover": {
                    bgcolor: "whitesmoke",
                    //   bgcolor: "rgba(102,126,234,0.05)",
                  },
                }),
          }}
        >
          {isAvailable ? "Borrow" : "Reserve"}
        </Button>
      </div>
    </div>
  );
};

export default WishlistCard;
