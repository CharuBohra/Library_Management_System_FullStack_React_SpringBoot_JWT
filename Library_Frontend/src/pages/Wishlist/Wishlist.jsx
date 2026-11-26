import { useState } from "react";
import PageContainer from "../components/PageContainer";
import WishlistCard from "./WishlistCard";
import PageHeader from "../components/PageHeader";
import { ArrowBack, Favorite } from "@mui/icons-material";
import { getMyWishlist } from "../../api/wishlistApi";
import { getErrorMessage } from "../../utils/getErrorMessage";
import { Link } from "react-router";
import { useEffect } from "react";

const Wishlist = () => {
  const [reloadCount, setReloadCount] = useState(0);
  const [result, setResult] = useState({
    key: null,
    items: [],
    error: null,
  });
  const requestKey = `${reloadCount}`;

  useEffect(() => {
    let ignore = false;
    const load = async () => {
      try {
        const data = await getMyWishlist({
          page: 0,
          size: 50,
        });
        if (!ignore) {
          setResult({
            key: requestKey,
            items: data,
            error: null,
          });
        }
      } catch (error) {
        if (!ignore) {
          setResult({
            key: requestKey,
            items: [],
            error: getErrorMessage(error),
          });
        }
      }
    };
    load();
    return () => {
      ignore = true;
    };
  }, [requestKey]);

  const loading = result.key !== requestKey;
  const items = result.items;

  const handleRemove = (bookId) => {
    setResult((prev) => ({
      ...prev,
      items: prev.items.filter((item) => item.book.id !== bookId),
    }));
    console.log("DELETE /api/wishlist/remove/" + bookId);
  };

  const renderWishlist = () => {
    // 1. Loading
    if (loading) {
      return (
        <div className="p-8 text-gray-600 text-center">
          Loading your Wishlist…
        </div>
      );
    }
    // 2. Error
    if (result.error) {
      return (
        <div
          role="alert"
          className="p-6 rounded-xl bg-red-50 text-red-700 text-center border border-red-700 flex flex-col items-center gap-3"
        >
          <p className="text-red-700 font-semibold">{result.error}</p>
          <button
            type="button"
            onClick={() => setReloadCount((prev) => prev + 1)}
            className="px-4 py-2 text-white rounded-lg font-semibold bg-indigo-600 hover:bg-indigo-700 "
          >
            Try again
          </button>
        </div>
      );
    }

    // 3.no data
    if (items.length === 0) {
      return (
        <div className="flex flex-col flex-1 items-center gap-4 text-indigo-500">
          <p className="p-8 text-center text-gray-500">
            Your wishlist is empty.
          </p>
          <Link
            to={"/books"}
            className="inline-flex items-center gap-1 font-semibold text-indigo-600 hover:underline"
          >
            <ArrowBack sx={{ fontSize: 18 }} />
            Browse books.
          </Link>
        </div>
      );
    }
    return (
      <div className="grid grid-cols-[repeat(auto-fill,minmax(210px,1fr))] gap-5">
        {items.map((myWishlist) => (
          <WishlistCard
            key={myWishlist.id}
            wishlistItem={myWishlist}
            onRemove={handleRemove}
          />
        ))}
      </div>
    );
  };
  return (
    <div className="min-h-screen bg-linear-to-br from-indigo-50 via-white to-purple-50 py-8">
      <PageContainer>
        <PageHeader
          title="My Wishlist"
          subtitle={
            !loading && items.length > 0
              ? `${items.length} ${items.length === 1 ? "book" : "books"} saved for later `
              : undefined
          }
          icon={<Favorite sx={{ fontSize: 36, color: "#4F46E5" }} />}
        />
        {renderWishlist()}
      </PageContainer>
    </div>
  );
};

export default Wishlist;
