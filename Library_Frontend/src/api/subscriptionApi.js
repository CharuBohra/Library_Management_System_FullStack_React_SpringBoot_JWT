import api from "./client";

export const getActiveSubscription = async () => {
  try {
    const response = await api.get("/api/subscriptions/active");
    return response.data;
  } catch (error) {
    if (error.response?.status === 404) {
      return null;
    }
    throw error;
  }
};
