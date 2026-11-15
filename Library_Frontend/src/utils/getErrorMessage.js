export const getErrorMessage = (
  error,
  fallback = "Something went wrong. Try again later",
) => {
  if (error.response) {
    return error.response.data?.message ?? fallback; // backend replied with 4xx/5xx
  }
  if (error.request) {
    return "Couldn't reach the server. Is the backend running?"; //no reply at all
  }
  return fallback;
};
