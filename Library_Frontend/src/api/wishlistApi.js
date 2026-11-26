import api from "./client";

export const getMyWishlist = async (params) => {
  const response = await api.get("/api/wishlist/my", { params });
  return response.data.content;
};
