import { useState } from "react";
import { TOKEN_KEY } from "../api/client";

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [initializing, setInitializing] = useState(() =>
    Boolean(localStorage.getItem(TOKEN_KEY)),
  );
};
