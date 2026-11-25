import api from "./client";

export const getMyFines = async () => {
  const response = await api.get("/api/fines/my");
  return response.data;
};
