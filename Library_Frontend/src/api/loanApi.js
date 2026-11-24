import api from "./client";

export const getMyLoans = async (params) => {
  const response = await api.get("/api/book-loans/my", { params });
  return response.data;
};
