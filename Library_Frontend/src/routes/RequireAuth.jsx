import { Navigate, Outlet, useLocation } from "react-router";
import { useAuth } from "../hooks/useAuth";

const RequireAuth = () => {
  const { isAuthenticated, initializing } = useAuth();
  const location = useLocation();
  if (initializing) {
    return (
      <div className="min-h-screen flex items-center justify-center text-center text-3xl font-semibold text-gray-900">
        Loading....
      </div>
    );
  }
  if (!isAuthenticated) {
    return (
      <Navigate to={"/login"} replace state={{ from: location.pathname }} />
    );
  }
  return <Outlet />;
};

export default RequireAuth;
