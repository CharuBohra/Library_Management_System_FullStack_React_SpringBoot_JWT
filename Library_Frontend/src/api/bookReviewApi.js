import api from "./client";

export const getBookReviews = async (bookId) => {
  const response = await api.get(`/api/reviews/book/${bookId}`);
  return response.data.content;
};
