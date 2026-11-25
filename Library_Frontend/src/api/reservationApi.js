import api from "./client";

export const getMyReservations = async (params) => {
  const response = await api.get("/api/reservations/my", { params });
  return response.data;
};
