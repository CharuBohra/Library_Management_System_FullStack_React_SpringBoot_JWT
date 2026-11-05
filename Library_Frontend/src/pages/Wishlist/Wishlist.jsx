import { useState } from "react";
import { wishlist } from "../../data/wishlist";
import PageContainer from "../components/PageContainer";
import WishlistCard from "./WishlistCard";
import PageHeader from "../components/PageHeader";
import { Favorite } from "@mui/icons-material";

const Wishlist = () => {
  const [items, setItems] = useState(wishlist);

  const handleRemove = (bookId) => {
    setItems((prev) => prev.filter((item) => item.book.id !== bookId));
    console.log("DELETE /api/wishlist/remove/" + bookId);
  };
  return (
    <div className="min-h-screen bg-linear-to-br from-indigo-50 via-white to-purple-50 py-8">
      <PageContainer>
        <PageHeader
          title="My Wishlist"
          subtitle={`${items.length} ${items.length === 1 ? "book" : "books"} saved for later`}
          icon={<Favorite sx={{ fontSize: 36, color: "#4F46E5" }} />}
        />
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
