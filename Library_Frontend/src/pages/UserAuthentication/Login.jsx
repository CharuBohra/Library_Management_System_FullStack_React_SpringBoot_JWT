import { Button } from "@mui/material";
import { useState } from "react";
import { Link, Navigate, useLocation, useNavigate } from "react-router";
import AuthSidePanel from "./AuthSidePanel";
import { getErrorMessage } from "../../utils/getErrorMessage";
import { useAuth } from "../../hooks/useAuth";
import { signup } from "../../api/authApi";

const Login = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const from = location.state?.from ?? "/";
  const { login, initializing, isAuthenticated } = useAuth();
  const [form, setForm] = useState({
    fullName: "",
    email: "",
    password: "",
    phone: "",
  });

  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [showPassword, setShowPassword] = useState(false);
  const [mode, setMode] = useState("login");

  const isSignUp = mode === "signup";

  const toggleMode = () => {
    setMode((prev) => (prev === "login" ? "signup" : "login"));
    setError(null);
    setSuccess(null);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
    setError(null);
    setSuccess(null);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (isSignUp) {
      const { fullName, email, password, phone } = form;
      if (!fullName.trim()) {
        setError("Please enter your full name.");
        return;
      }
      if (password.length < 8) {
        setError("Password must be at least 8 characters.");
        return;
      }
      try {
        setError(null);
        setIsSubmitting(true);
        await signup(fullName, email, password, phone);
        setMode("login");
        setForm((prev) => ({ ...prev, password: "" }));
        setSuccess("Account created. Please Sign in");
      } catch (error) {
        setError(getErrorMessage(error));
      } finally {
        setIsSubmitting(false);
      }
    } else {
      const { email, password } = form;
      try {
        setError(null);
        setIsSubmitting(true);
        await login(email, password);
        navigate(from, { replace: true });
      } catch (error) {
        setError(getErrorMessage(error));
      } finally {
        setIsSubmitting(false);
      }
    }
  };

  const MODE_TEXT = {
    login: {
      heading: "Welcome back",
      subtitle: "Sign in to manage your loans and reservations",
      cta: "Sign in",
      pending: "Signing in...",
    },
    signup: {
      heading: "Create your account",
      subtitle: "Join the library in under a minute",
      cta: "Create account",
      pending: "Creating account...",
    },
  };

  const text = MODE_TEXT[mode];

  if (!initializing && isAuthenticated) {
    return <Navigate to={from} replace />;
  }

  return (
    <div className="min-h-screen flex flex-wrap">
      <AuthSidePanel />
      <div className="min-w-0 basis-80 flex flex-1 items-center justify-center p-6  bg-white">
        <div className="w-full max-w-sm flex flex-col gap-4">
          <h1 className="text-4xl text-gray-900 font-bold">{text.heading}</h1>
          <p className="text-gray-700 text-lg mb-2 ">{text.subtitle}</p>
          <form
            action=""
            onSubmit={handleSubmit}
            className="flex flex-col gap-5 w-full max-w-sm"
          >
            {isSignUp && (
              <div className="flex flex-col gap-1.5">
                <label htmlFor="fullName" className="font-semibold">
                  Full Name
                </label>
                <input
                  id="fullName"
                  type="text"
                  name="fullName"
                  value={form.fullName}
                  onChange={handleChange}
                  autoComplete="name"
                  placeholder="John Doe"
                  className="border border-gray-200 rounded-lg shadow-sm px-3.5 py-2.5 w-full focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                />
              </div>
            )}
            <div className="flex flex-col gap-1.5">
              <label htmlFor="email" className="font-semibold">
                Email
              </label>
              <input
                id="email"
                type="email"
                name="email"
                value={form.email}
                onChange={handleChange}
                autoComplete="email"
                placeholder="you@example.com"
                className="border border-gray-200 rounded-lg shadow-sm px-3.5 py-2.5 w-full focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
              />
            </div>
            <div className="flex flex-col gap-1.5">
              <div className="flex flex-wrap items-center justify-between gap-3">
                <label htmlFor="password" className="font-semibold">
                  Password
                </label>
                {!isSignUp && (
                  <Link
                    to={"/forgot-password"}
                    className="text-sm font-semibold text-indigo-600 hover:underline"
                  >
                    Forgot password?
                  </Link>
                )}
              </div>
              <div className="relative">
                <input
                  id="password"
                  name="password"
                  type={showPassword ? "text" : "password"}
                  value={form.password}
                  autoComplete={isSignUp ? "new-password" : "current-password"}
                  placeholder="Enter your password"
                  onChange={handleChange}
                  className="border border-gray-200 rounded-lg shadow-sm px-3.5 py-2.5 pr-16 w-full focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                />
                <button
                  type="button"
                  onClick={() => setShowPassword((prev) => !prev)}
                  aria-label={showPassword ? "Hide password" : "Show password"}
                  className="absolute right-3 top-1/2 -translate-y-1/2 text-sm font-semibold text-indigo-600"
                >
                  {showPassword ? "Hide" : "Show"}
                </button>
              </div>
            </div>
            {isSignUp && (
              <div className="flex flex-col gap-1.5">
                <label htmlFor="phone" className="font-semibold">
                  Phone (optional)
                </label>
                <input
                  id="phone"
                  type="tel"
                  name="phone"
                  value={form.phone}
                  onChange={handleChange}
                  autoComplete="tel"
                  placeholder="+91 9876543210"
                  className="border border-gray-200 rounded-lg shadow-sm px-3.5 py-2.5 w-full focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                />
              </div>
            )}
            {error && (
              <div
                role="alert"
                className="text-sm font-semibold rounded-lg border border-red-700 bg-red-100 px-3.5 py-2.5 text-red-700"
              >
                {error}
              </div>
            )}
            {success && (
              <div
                role="status"
                className="text-sm font-semibold rounded-lg border border-green-700 bg-green-100 px-3.5 py-2.5 text-green-700"
              >
                {success}
              </div>
            )}
            <Button
              variant="contained"
              fullWidth
              disabled={isSubmitting}
              type="submit"
              sx={{
                bgcolor: "#4F46E5",
                borderRadius: 2,
                py: 1.5,
                color: "white",
                textTransform: "none",
                fontWeight: 600,
                "&:hover": {
                  bgcolor: "#4338CA",
                },
                "&.Mui-disabled": { bgcolor: "#A5B4FC", color: "white" },
              }}
            >
              {isSubmitting ? text.pending : text.cta}
            </Button>
            <p className="text-sm text-center text-gray-600 mt-4">
              {isSignUp ? "Already have an account?" : "New to ZoshBook?"}{" "}
              <button
                type="button"
                onClick={toggleMode}
                className="font-semibold text-indigo-600 hover:underline"
              >
                {isSignUp ? "Sign In" : "Create an account"}
              </button>
            </p>
          </form>
        </div>
      </div>
    </div>
  );
};

export default Login;
