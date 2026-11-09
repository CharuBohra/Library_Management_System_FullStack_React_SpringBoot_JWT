import { MenuBook } from "@mui/icons-material";
import { Avatar, Box, Button, Typography } from "@mui/material";
import { useState } from "react";
import { Link } from "react-router";

const Login = () => {
  const [form, setForm] = useState({
    email: "",
    password: "",
  });

  const [showPassword, setShowPassword] = useState(false);
  const [mode, setMode] = useState("login");

  const isSignUp = mode === "signup";

  const toggleMode = () => {
    setMode((prev) => (prev === "login" ? "signup" : "login"));
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
  };

  return (
    <div className="min-h-screen flex flex-wrap">
      <div className="min-w-0 basis-110 bg-linear-to-br from-slate-800 to-slate-900 flex flex-col flex-1 items-start justify-between p-12">
        <Box
          sx={{
            display: "flex",
            alignItems: "center",
            gap: 2,
          }}
        >
          <Avatar
            sx={{
              width: 64,
              height: 64,
              background: "linear-gradient(135deg , #667eea 0% , #764ba2 100%)",
              fontWeight: "bold",
              fontSize: "1.3rem",
              boxShadow: "0 4px 16px rgba(102,126,234,0.4)",
            }}
          >
            <MenuBook sx={{ fontSize: 36 }} />
          </Avatar>
          <Box>
            <Typography
              variant="h5"
              sx={{
                fontWeight: 900,
                letterSpacing: 0.5,
                background:
                  "linear-gradient(135deg , #ffffff 0% , #e0e7ff 100%)",
                backgroundClip: "text",
                WebkitBackgroundClip: "text",
                WebkitTextFillColor: "transparent",
              }}
            >
              ZoshBook
            </Typography>
            <Typography
              variant="body1"
              sx={{
                opacity: 0.5,
                fontWeight: 700,
                letterSpacing: 1,
                textTransform: "uppercase",
                color: "white",
                mt: 1,
              }}
            >
              Library Hub
            </Typography>
          </Box>
        </Box>
        <Box>
          <Typography
            variant="h4"
            sx={{
              fontWeight: 900,
              letterSpacing: 0.5,
              backgroundClip: "text",
              WebkitBackgroundClip: "text",
              maxWidth: 560,
              color: "white",
              fontSize: 48,
            }}
          >
            Borrow, reserve and track every book in one place.
          </Typography>
          <Typography
            variant="body1"
            sx={{
              opacity: 0.8,
              fontWeight: 200,
              letterSpacing: 1,
              textTransform: "none",
              color: "whitesmoke",
              mt: 2,
              maxWidth: 600,
              fontSize: 18,
            }}
          >
            See due dates, renew loans, join waiting lists and pay fines without
            visiting the desk.
          </Typography>
        </Box>
        <div className="text-gray-300 text-xl ">
          <p>© 2026 ZoshBook ,All Rights Reserved</p>
        </div>
      </div>
      <div className="min-w-0 w-full max-w-160 basis-100 flex flex-col flex-1 justify-center p-24 m-5 bg-white gap-4">
        <h1 className="text-4xl text-gray-900 font-bold">Welcome Back</h1>
        <p className="text-gray-700 text-lg mb-2 ">
          Sign in to manage your loans and reservations
        </p>
        <form
          action=""
          onSubmit={handleSubmit}
          className="flex flex-col gap-5 w-full max-w-sm"
        >
          <div className="gap-1">
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
          <div className="gap-1">
            <div className="flex flex-wrap items-center justify-between gap-3">
              <label htmlFor="password" className="font-semibold">
                Password
              </label>
              <Link
                to={"/forgot-password"}
                className="text-sm font-semibold text-indigo-600 hover:underline"
              >
                Forgot password?
              </Link>
            </div>
            <div className="relative">
              <input
                id="password"
                name="password"
                type={showPassword ? "text" : "password"}
                value={form.password}
                autoComplete="current-password"
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
          <Button
            variant="contained"
            fullWidth
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
                //   bgcolor: "rgba(102,126,234,0.05)",
              },
            }}
          >
            {isSignUp ? "Sign Up" : "Sign In"}
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
  );
};

export default Login;
