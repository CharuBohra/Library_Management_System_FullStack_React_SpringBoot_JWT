import { ArrowBack } from "@mui/icons-material";
import { Button } from "@mui/material";
import { useState } from "react";
import { Link } from "react-router";
import AuthSidePanel from "./AuthSidePanel";

const ForgotPassword = () => {
  const [email, setEmail] = useState("");

  const handleChange = (e) => {
    setEmail(e.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
  };
  return (
    <div className="min-h-screen flex flex-wrap">
      <AuthSidePanel />
      <div className="min-w-0 basis-80 flex flex-1 items-center justify-center p-5  bg-white">
        <div className="w-full max-w-sm flex flex-col gap-4">
          <h1 className="text-3xl text-gray-900 font-bold">
            Forgot your password?
          </h1>
          <p className="text-gray-700 text-lg mb-2 ">
            Enter your email and we'll send you a link to set a new one.
          </p>
          <form
            action=""
            onSubmit={handleSubmit}
            className="flex flex-col gap-5 w-full max-w-sm"
          >
            <div className="flex flex-col gap-1.5">
              <label htmlFor="email" className="font-semibold">
                Email
              </label>
              <input
                id="email"
                type="email"
                name="email"
                value={email}
                onChange={handleChange}
                autoComplete="email"
                placeholder="you@example.com"
                className="border border-gray-200 rounded-lg shadow-sm px-3.5 py-2.5 w-full focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
              />
            </div>
            <Button
              variant="contained"
              fullWidth
              type="submit"
              sx={{
                bgcolor: "#4F46E5",
                fontSize: 16,
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
              Send Reset Link
            </Button>
            <p className="text-base text-center text-gray-600 mt-2">
              <Link
                to="/login"
                className="inline-flex self-center items-center justify-center gap-1.5 text-indigo-600 hover:underline"
              >
                <ArrowBack sx={{ fontSize: 18 }} />
                Back to sign in
              </Link>
            </p>
          </form>
        </div>
      </div>
    </div>
  );
};

export default ForgotPassword;
