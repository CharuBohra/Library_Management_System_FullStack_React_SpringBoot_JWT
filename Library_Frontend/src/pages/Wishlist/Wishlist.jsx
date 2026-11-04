import { useState } from "react";
import { wishlist } from "../../data/wishlist";
import PageContainer from "../components/PageContainer";
import WishlistCard from "./WishlistCard";

const Wishlist = () => {
  const [items, setItems] = useState(wishlist);

  const handleRemove = (bookId) => {
    setItems((prev) => prev.filter((item) => item.book.id !== bookId));
    console.log("DELETE /api/wishlist/remove/" + bookId);
  };
  return (
    <div className="min-h-screen bg-linear-to-br from-indigo-50 via-white to-purple-50 py-8">
      <PageContainer>
        <div className="mb-8">
          <h1 className="text-4xl font-bold text-gray-900 mb-2 flex items-center gap-3">
            <span className="bg-linear-to-br from-indigo-600 to-purple-600 bg-clip-text text-transparent mb-1">
              My Wishlist
            </span>
          </h1>
          <p>
            {items.length} {items.length === 1 ? "book" : "books"} saved for
            later
          </p>
        </div>
        <div className="grid grid-cols-[repeat(auto-fill,minmax(210px,1fr))] gap-5">
          {items.map((myWishlist) => (
            <WishlistCard
              key={myWishlist.id}
              wishlistItem={myWishlist}
              onRemove={handleRemove}
            />
          ))}
        </div>
      </PageContainer>
    </div>
  );
};

export default Wishlist;
