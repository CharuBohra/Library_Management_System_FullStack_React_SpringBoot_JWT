import api from "./client";

export const getBooks = async (params) => {
  const response = await api.get("/api/books", {
    params,
  });
  return response.data;
};
