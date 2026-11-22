import { ArrowBack } from "@mui/icons-material";
import { Button } from "@mui/material";
import { useState } from "react";
import { Link } from "react-router";
import AuthSidePanel from "./AuthSidePanel";
import { forgotPassword } from "../../api/authApi";
import { getErrorMessage } from "../../utils/getErrorMessage";

const ForgotPassword = () => {
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [error, setError] = useState(null);
  const [sent, setSent] = useState(false);
  const [email, setEmail] = useState("");

  const handleChange = (e) => {
    setEmail(e.target.value);
    setError(null);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    setIsSubmitting(true);
    try {
      await forgotPassword(email);
      setSent(true);
    } catch (error) {
      setError(getErrorMessage(error));
    } finally {
      setIsSubmitting(false);
    }
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
          {sent ? (
            <div
              role="status"
              className="text-sm font-semibold rounded-lg border border-green-700 bg-green-100 px-3.5 py-2.5 text-green-700"
            >
              If an account exists for that email, a reset link is on its way.
              Check your inbox.
            </div>
          ) : (
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
              {error && (
                <div
                  role="alert"
                  className="text-sm font-semibold rounded-lg border border-red-700 bg-red-100 px-3.5 py-2.5 text-red-700"
                >
                  {error}
                </div>
              )}
              <Button
                variant="contained"
                fullWidth
                type="submit"
                disabled={isSubmitting}
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
                {isSubmitting ? "Sending..." : "Send Reset Link"}
              </Button>
            </form>
          )}
          <p className="text-sm text-center text-gray-600">
            Link expired?{" "}
            <Link
              to="/forgot-password"
              className="font-semibold text-indigo-600 hover:underline"
            >
              Request a new one
            </Link>
          </p>
          <p className="text-base text-center text-gray-600 mt-2">
            <Link
              to="/login"
              className="inline-flex self-center items-center justify-center gap-1.5 text-indigo-600 hover:underline"
            >
              <ArrowBack sx={{ fontSize: 18 }} />
              Back to sign in
            </Link>
          </p>
        </div>
      </div>
    </div>
  );
};

export default ForgotPassword;
