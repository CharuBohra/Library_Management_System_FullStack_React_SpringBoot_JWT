import api from "./client";

export const getProfile = async () => {
  const response = await api.get("/api/user/profile");
  return response.data; // UserDTO
};
