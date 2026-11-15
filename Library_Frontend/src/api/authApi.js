import api from "./client";

export const login = async (email, password) => {
  const response = await api.post("/auth/login", { email, password });
  return response.data; // { jwt, title, message, user }
};

export const signup = async (fullName, email, password, phone) => {
  const response = await api.post("/auth/signup", {
    fullName,
    email,
    password,
    phone,
  });
  return response.data; //{message,status}
};
