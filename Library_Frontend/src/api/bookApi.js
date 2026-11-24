import api from "./client";

export const getBooks = async (params) => {
  const response = await api.get("/api/books", {
    params,
  });
  return response.data;
};

export const getBookById = async (id) => {
  const response = await api.get(`/api/books/${id}`);
  return response.data;
};
