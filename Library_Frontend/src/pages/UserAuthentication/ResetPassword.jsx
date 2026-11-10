import { ArrowBack } from "@mui/icons-material";
import { Button } from "@mui/material";
import { useState } from "react";
import { Link, useSearchParams } from "react-router";
import AuthSidePanel from "./AuthSidePanel";

const ResetPassword = () => {
  const [form, setForm] = useState({
    newPassword: "",
    confirmPassword: "",
  });

  const [showPassword, setShowPassword] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState(false);
  const [searchParams] = useSearchParams();
  const token = searchParams.get("token");

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
    setError("");
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (form.newPassword.length < 8) {
      setError("Password must be at least 8 characters");
      return;
    }
    if (form.newPassword !== form.confirmPassword) {
      setError("Passwords don't match");
      return;
    }
    setError("");
    console.log("POST /auth/reset-password", {
      token,
      newPassword: form.newPassword,
    });

    setSuccess(true);
  };
  return (
    <div className="min-h-screen flex flex-wrap">
      <AuthSidePanel />
      <div className="min-w-0 basis-80 flex flex-1 items-center justify-center p-6  bg-white">
        {token ? (
          success ? (
            <div className="flex flex-col gap-4">
              <h1 className="text-3xl font-bold text-gray-900">
                Password updated
              </h1>
              <p className="text-gray-600">
                You can now sign in with your new password.
              </p>
              <Link
                to="/login"
                className="inline-flex self-center items-center justify-center gap-1.5 text-indigo-600 hover:underline"
              >
                <ArrowBack sx={{ fontSize: 18 }} />
                Go to sign in
              </Link>
            </div>
          ) : (
            <div className="w-full max-w-sm flex flex-col gap-4">
              <h1 className="text-4xl text-gray-900 font-bold">
                Set a new password
              </h1>
              <p className="text-gray-700 text-lg mb-2 ">
                Choose a new password for your account, the link from your email
                is valid for a limited time,
              </p>
              <form
                action=""
                onSubmit={handleSubmit}
                className="flex flex-col gap-5 w-full max-w-sm"
              >
                <div className="flex flex-col gap-1.5">
                  <div className="flex flex-wrap items-center justify-between gap-3">
                    <label htmlFor="newPassword" className="font-semibold">
                      New password
                    </label>
                  </div>
                  <div className="relative">
                    <input
                      id="newPassword"
                      name="newPassword"
                      type={showPassword ? "text" : "password"}
                      value={form.newPassword}
                      autoComplete={"new-password"}
                      placeholder="Enter your password"
                      onChange={handleChange}
                      className="border border-gray-200 rounded-lg shadow-sm px-3.5 py-2.5 pr-16 w-full focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                    />
                    <button
                      type="button"
                      onClick={() => setShowPassword((prev) => !prev)}
                      aria-label={
                        showPassword ? "Hide password" : "Show password"
                      }
                      className="absolute right-3 top-1/2 -translate-y-1/2 text-sm font-semibold text-indigo-600"
                    >
                      {showPassword ? "Hide" : "Show"}
                    </button>
                  </div>
                </div>
                <div className="flex flex-col gap-1.5">
                  <div className="flex flex-wrap items-center justify-between gap-3">
                    <label htmlFor="confirmPassword" className="font-semibold">
                      Confirm new password
                    </label>
                  </div>
                  <input
                    id="confirmPassword"
                    name="confirmPassword"
                    type={showPassword ? "text" : "password"}
                    value={form.confirmPassword}
                    autoComplete={"new-password"}
                    placeholder="Enter your password"
                    onChange={handleChange}
                    className={`border rounded-lg shadow-sm px-3.5 py-2.5 w-full  ${error ? "border-red-400 focus:ring-red-500" : "border-gray-200 focus:ring-indigo-500"} focus:outline-none focus:ring-2`}
                  />
                  {error && (
                    <span className="text-red-500 text-sm mt-1 font-semibold">
                      {error}
                    </span>
                  )}
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
                  Update Password
                </Button>
                <p className="text-sm text-center text-gray-600 ">
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
          )
        ) : (
          <div className="flex flex-col gap-4">
            <h1 className="text-3xl font-bold text-gray-900">Link not valid</h1>
            <p className="text-gray-600">
              This reset link is invalid or incomplete.Please request a new one.
            </p>
            <Link
              to="/forgot-password"
              className="text-center py-2.5 rounded-lg bg-indigo-600 text-white font-semibold hover:bg-indigo-700"
            >
              Request a new link
            </Link>
            <Link
              to="/login"
              className="inline-flex self-center items-center justify-center gap-1.5 text-indigo-600 hover:underline"
            >
              <ArrowBack sx={{ fontSize: 18 }} />
              Back to sign in
            </Link>
          </div>
        )}
      </div>
    </div>
  );
};

export default ResetPassword;
