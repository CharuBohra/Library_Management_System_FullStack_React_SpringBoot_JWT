import { useState } from "react";
import { TOKEN_KEY } from "../api/client";
import { useEffect } from "react";
import { getProfile } from "../api/userApi";
import { login as loginRequest } from "../api/authApi";
import { AuthContext } from "./authContext";

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [initializing, setInitializing] = useState(() =>
    Boolean(localStorage.getItem(TOKEN_KEY)),
  );

  // On app start: if a token was saved, ask the backend who it belongs to
  useEffect(() => {
    if (!localStorage.getItem(TOKEN_KEY)) return;

    getProfile()
      .then((profile) => setUser(profile))
      .catch(() => localStorage.removeItem(TOKEN_KEY))
      .finally(() => setInitializing(false));
  }, []);

  const login = async (email, password) => {
    const data = await loginRequest(email, password);
    localStorage.setItem(TOKEN_KEY, data.jwt);
    setUser(data.user);
    return data.user;
  };

  const logout = () => {
    localStorage.removeItem(TOKEN_KEY);
    setUser(null);
  };

  const value = {
    user,
    isAuthenticated: Boolean(user),
    initializing,
    login,
    logout,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};
