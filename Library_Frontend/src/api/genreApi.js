import api from "./client";

export const getGenres = async () => {
  const response = await api.get("/api/genres");
  return response.data;
};
