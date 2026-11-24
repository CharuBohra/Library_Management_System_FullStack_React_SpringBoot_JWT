import { Link, useParams } from "react-router";
import PageContainer from "../components/PageContainer";
import { ArrowBack, FavoriteBorderOutlined } from "@mui/icons-material";
import {
  Box,
  Button,
  Card,
  CardContent,
  FormControl,
  InputLabel,
  MenuItem,
  Rating,
  Select,
  Typography,
} from "@mui/material";
import { formatDate } from "../../utils/formatDate";
import { useState } from "react";
import { useEffect } from "react";
import { getBookById } from "../../api/bookApi";
import { getErrorMessage } from "../../utils/getErrorMessage";
import { getBookReviews } from "../../api/bookReviewApi";
import { getActiveSubscription } from "../../api/subscriptionApi";

const BookDetails = () => {
  const { id } = useParams();
  const [reloadCount, setReloadCount] = useState(0);
  const [reviews, setReviews] = useState([]);
  const [subscription, setSubscription] = useState(null);
  const [pickedDays, setPickedDays] = useState(null);
  const [result, setResult] = useState({
    key: null,
    book: null,
    notFound: false,
    error: null,
  });
  const requestKey = `${id} | ${reloadCount}`;

  const review = reviews;

  const reviewCount = review.length;
  const avgRating =
    reviewCount === 0
      ? 0
      : review.reduce((sum, r) => sum + r.rating, 0) / reviewCount;

  useEffect(() => {
    let ignore = false;
    const load = async () => {
      try {
        const data = await getBookById(id);
        if (!ignore) {
          setResult({
            key: requestKey,
            book: data,
            notFound: false,
            error: null,
          });
        }
      } catch (error) {
        if (!ignore) {
          if (error.response?.status === 404) {
            setResult({
              key: requestKey,
              book: null,
              notFound: true,
              error: null,
            });
          } else {
            setResult({
              key: requestKey,
              book: null,
              notFound: false,
              error: getErrorMessage(error),
            });
          }
        }
      }
    };
    load();
    return () => {
      ignore = true;
    };
  }, [requestKey, id]);

  useEffect(() => {
    let ignore = false;
    const load = async () => {
      try {
        const data = await getBookReviews(id);
        if (!ignore) {
          setReviews(data);
        }
      } catch (error) {
        if (!ignore) {
          setReviews([]);
        }
      }
    };
    load();
    return () => {
      ignore = true;
    };
  }, [id]);

  useEffect(() => {
    let ignore = false;
    const load = async () => {
      try {
        const data = await getActiveSubscription();
        if (!ignore) {
          setSubscription(data);
        }
      } catch (error) {
        if (!ignore) {
          setSubscription(null);
        }
      }
    };
    load();
    return () => {
      ignore = true;
    };
  }, []);

  const maxDays = subscription?.maxDaysPerBook ?? 14;

  const checkoutDays = pickedDays ?? maxDays;

  const dayOptions = [...new Set([7, 14, maxDays])]
    .filter((d) => d <= maxDays)
    .sort((a, b) => a - b);

  const countFor = (stars) => review.filter((r) => r.rating === stars).length;

  const percentFor = (stars) =>
    reviewCount === 0 ? 0 : (countFor(stars) / reviewCount) * 100;

  const loading = result.key !== requestKey;
  const book = result.book;

  if (loading) {
    return (
      <div className="min-h-screen bg-linear-to-br from-indigo-50 via-white to-purple-50 py-8">
        <PageContainer>
          <div className="p-8 text-gray-600 text-center">Loading book…</div>
        </PageContainer>
      </div>
    );
  }
  if (result.notFound) {
    return (
      <div className="min-h-screen bg-linear-to-br from-indigo-50 via-white to-purple-50 py-8">
        <PageContainer>
          <p className="p-8 text-center text-gray-500">Book Not Found</p>
        </PageContainer>
      </div>
    );
  }
  if (result.error) {
    return (
      <div
        role="alert"
        className="p-6 rounded-xl bg-red-50 text-red-700 text-center border border-red-700 flex flex-col items-center gap-3"
      >
        <p className="text-red-700 font-semibold">{result.error}</p>
        <button
          type="button"
          onClick={() => setReloadCount((prev) => prev + 1)}
          className="px-4 py-2 text-white rounded-lg font-semibold bg-indigo-600 hover:bg-indigo-700 "
        >
          Try again
        </button>
      </div>
    );
  }
  return (
    <div className="min-h-screen bg-linear-to-br from-indigo-50 via-white to-purple-50 py-8">
      <PageContainer>
        <div className="mb-8">
          <div>
            <nav aria-label="Breadcrumb" className="mb-6">
              <ol className="flex items-center gap-2 text-sm min-w-0">
                <li>
                  <Link
                    to="/books"
                    className="inline-flex items-center gap-1 font-semibold text-indigo-600 hover:text-indigo-800 hover:underline"
                  >
                    <ArrowBack sx={{ fontSize: 20 }} />
                    Browse Books
                  </Link>
                </li>
                <li aria-hidden="true" className="text-gray-400">
                  /
                </li>
                <li
                  aria-current="page"
                  className="text-gray-700 truncate min-w-0"
                >
                  {book.title}
                </li>
              </ol>
            </nav>
          </div>
          <Card sx={{ m: 2 }}>
            <CardContent>
              <Box
                sx={{
                  display: "flex",
                  flexDirection: { xs: "column", md: "row" },
                  gap: 3,
                  p: 2,
                }}
              >
                {/* Book COver */}
                <Box
                  sx={{
                    width: 200,
                    height: 350,
                    borderRadius: 2,
                    background:
                      "linear-gradient(135deg , #667eea 0% , #764ba2 100%)",
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "center",
                    flexShrink: 0,
                    cursor: "pointer",
                    transition: "transform 0.3s",
                    "&:hover": {
                      transform: "scale(1.05)",
                    },
                  }}
                >
                  <Box
                    sx={{
                      display: "flex",
                      flexDirection: "column",
                      gap: 0.2,
                      p: 2,
                      mt: "auto",
                      mr: "auto",
                    }}
                  >
                    <Typography
                      variant="body1"
                      sx={{ fontWeight: 700, color: "white" }}
                    >
                      {book.title}
                    </Typography>
                    <Typography
                      variant="body2"
                      sx={{ color: "white", fontSize: 12 }}
                    >
                      {book.author}
                    </Typography>
                  </Box>
                </Box>

                {/* Book Details  */}
                <div className="flex flex-col gap-3">
                  <div className="flex flex-wrap gap-2">
                    <span
                      className="inline-flex items-center gap-1.5 px-3 py-1 rounded-full text-xs font-semibold
                               bg-indigo-50 text-indigo-700 whitespace-nowrap"
                    >
                      {book.genreName}
                    </span>
                    <span className="inline-flex items-center gap-1.5 px-3 py-1 rounded-full text-xs text-green-700 whitespace-nowrap bg-green-50">
                      {book.availableCopies} of {book.totalCopies} available
                    </span>
                  </div>
                  <h1 className="font-bold text-4xl">{book.title}</h1>
                  <div className="text-base font-semibold text-gray-600">
                    by {book.author}
                  </div>
                  <div className="flex items-center gap-2">
                    <Rating
                      value={avgRating}
                      precision={0.5}
                      readOnly
                      size="small"
                    />
                    <span className="font-semibold">
                      {avgRating.toFixed(1)}
                    </span>
                    {reviewCount > 1 ? (
                      <span className="text-sm text-purple-700">
                        {reviewCount} reviews
                      </span>
                    ) : (
                      <span className="text-sm text-gray-900">
                        No reviews yet
                      </span>
                    )}
                  </div>
                  <div>
                    <p className="text-sm leading-relaxed text-gray-700">
                      {book.description}
                    </p>
                  </div>
                  <div className="grid grid-cols-[repeat(auto-fill,minmax(110px,1fr))] gap-x-5 gap-y-4 bg-gray-50 rounded-xl p-4">
                    <div className="flex flex-col gap-1">
                      <div className="text-xs font-semibold uppercase tracking-wide">
                        Publisher
                      </div>
                      <div className="text-sm font-semibold text-gray-500">
                        {book.publisher}
                      </div>
                    </div>
                    <div className="flex flex-col gap-1">
                      <div className="text-xs font-semibold uppercase tracking-wide">
                        Published
                      </div>
                      <div className="text-sm font-semibold text-gray-500">
                        {formatDate(book.publicationDate)}
                      </div>
                    </div>
                    <div className="flex flex-col gap-1">
                      <div className="text-xs font-semibold uppercase tracking-wide">
                        Language
                      </div>
                      <div className="text-sm font-semibold text-gray-500">
                        {book.language}
                      </div>
                    </div>
                    <div className="flex flex-col gap-1">
                      <div className="text-xs font-semibold uppercase tracking-wide">
                        Pages
                      </div>
                      <div className="text-sm font-semibold text-gray-500">
                        {book.pages}
                      </div>
                    </div>
                    <div className="flex flex-col gap-1">
                      <div className="text-xs font-semibold uppercase tracking-wide">
                        ISBN
                      </div>
                      <div className="text-sm font-semibold text-gray-500">
                        {book.isbn}
                      </div>
                    </div>
                  </div>
                  <div className="flex flex-row gap-4 text-sm text-gray-900 mt-3 w-full">
                    <div className="flex items-center gap-2 md:w-64">
                      <InputLabel
                        sx={{ overflow: "visible", fontSize: "0.85rem" }}
                      >
                        Borrow for
                      </InputLabel>
                      <FormControl fullWidth>
                        <InputLabel id="checkout-days-label">Select</InputLabel>
                        <Select
                          labelId="checkout-days-label"
                          id="checkout-days-select"
                          value={checkoutDays}
                          label="Borrow for"
                          inputProps={{ "aria-label": "Checkout Days" }}
                          onChange={(e) =>
                            setPickedDays(Number(e.target.value))
                          }
                          sx={{
                            fontSize: "0.875rem", // 14px, same as text-sm
                            fontFamily: "inherit", // use the page font instead of Roboto
                            color: "#374151", // text-gray-700
                          }}
                          MenuProps={{
                            slotProps: {
                              paper: {
                                sx: {
                                  "&. MuiMenuItem-root": {
                                    fontSize: "0.875rem", // 14px, same as text-sm
                                    fontFamily: "inherit",
                                    color: "#374151",
                                  },
                                },
                              },
                            },
                          }}
                        >
                          {dayOptions.map((d) => (
                            <MenuItem key={d} value={d}>
                              {d} days {d === maxDays ? "(plan max)" : ""}
                            </MenuItem>
                          ))}
                        </Select>
                      </FormControl>
                    </div>
                    <div className="md:w-48">
                      <Button
                        variant="contained"
                        fullWidth
                        // onClick={() => handleBorrowBook()}
                        sx={{
                          height: "50px",
                          bgcolor: "#4F46E5",
                          textTransform: "none",
                          fontWeight: 600,
                          "&:hover": {
                            bgcolor: "#4338CA",
                            //   bgcolor: "rgba(102,126,234,0.05)",
                          },
                        }}
                      >
                        Borrow this book
                      </Button>
                    </div>
                    <div className="md:w-48">
                      <Button
                        variant="outlined"
                        fullWidth
                        // onClick={() => handleBorrowBook()}
                        startIcon={<FavoriteBorderOutlined />}
                        sx={{
                          height: "50px",
                          bgcolor: "white",
                          textTransform: "none",
                          fontWeight: 600,
                          "&:hover": {
                            bgcolor: "whitesmoke",
                            //   bgcolor: "rgba(102,126,234,0.05)",
                          },
                        }}
                      >
                        Add to Wishlist
                      </Button>
                    </div>
                  </div>
                </div>
              </Box>
            </CardContent>
          </Card>
          <Card sx={{ m: 2 }}>
            <CardContent sx={{ p: 3 }}>
              <div className="flex flex-1 flex-wrap items-center justify-between gap-3 mb-4">
                <h1 className="text-3xl font-bold text-gray-900">Reviews</h1>
                <div>
                  <Button
                    variant="outlined"
                    fullWidth
                    // onClick={() => handleBorrowBook()}
                    sx={{
                      height: "50px",
                      bgcolor: "white",
                      textTransform: "none",
                      fontWeight: 600,
                      px: 4,
                      py: 2,
                      "&:hover": {
                        bgcolor: "whitesmoke",
                        //   bgcolor: "rgba(102,126,234,0.05)",
                      },
                    }}
                  >
                    Write a Review
                  </Button>
                </div>
              </div>
              {reviewCount === 0 ? (
                <p className="text-gray-500">
                  No reviews yet. Be the first to review this book.
                </p>
              ) : (
                <div className="flex flex-wrap gap-10">
                  <div className="flex flex-col gap-6 w-60">
                    <div className="flex flex-col gap-1 shrink-0 mb-2">
                      <div className="flex items-baseline gap-1 mb-2">
                        <span className="text-4xl font-extrabold text-gray-900">
                          {avgRating.toFixed(1)}
                        </span>
                        <span className="text-gray-600 space-x-1">/5</span>
                      </div>
                      <Rating
                        value={avgRating}
                        precision={0.5}
                        readOnly
                        size="small"
                      />
                      <span className="text-sm text-gray-500">
                        {reviewCount} {reviewCount === 1 ? "review" : "reviews"}
                      </span>
                    </div>
                    <div className="flex flex-col gap-2 w-full max-w-sm">
                      {[5, 4, 3, 2, 1].map((stars) => (
                        <div
                          key={stars}
                          className="flex items-center gap-2 text-sm text-gray-700"
                        >
                          <span className="w-8 shrink-0">{stars}★</span>
                          {/* track light */}
                          <div className="flex-1 h-2 bg-indigo-50 rounded-full overflow-hidden">
                            {/* Fill inside */}
                            <div
                              className="h-full bg-indigo-600 rounded-full"
                              style={{ width: `${percentFor(stars)}%` }}
                            />
                          </div>
                          <span className="w-6 text-right shrink-0">
                            {countFor(stars)}
                          </span>
                        </div>
                      ))}
                    </div>
                  </div>
                  <div className="flex flex-col flex-1 min-w-0 basis-80">
                    {review.map((bookReview) => (
                      <article
                        key={bookReview.id}
                        className="flex flex-col gap-1.5 p-5 border border-gray-100 first:pt-0"
                      >
                        <div className="flex flex-wrap items-center gap-2">
                          <span className="font-bold text-gray-900 text-xl">
                            {bookReview.userName}
                          </span>
                          {bookReview.isVerifiedReader && (
                            <span className="inline-flex items-center gap-1.5 px-3 py-1 rounded-full text-xs text-green-700 whitespace-nowrap bg-green-50">
                              Verified Reader
                            </span>
                          )}
                          <span className="ml-auto text-gray-500 text-xs">
                            {formatDate(bookReview.createdAt)}
                          </span>
                        </div>
                        <Rating
                          value={bookReview.rating}
                          readOnly
                          size="small"
                        />
                        <div className="space-y-1">
                          {bookReview.title && (
                            <span className="text-xl font-bold text-gray-900 my-1">
                              {bookReview.title}
                            </span>
                          )}
                          <p className="text-sm text-gray-700 my-1">
                            {bookReview.reviewText}
                          </p>
                          <span className="text-xs text-gray-500">
                            {bookReview.helpfulCount} found this helpful
                          </span>
                        </div>
                      </article>
                    ))}
                  </div>
                </div>
              )}
            </CardContent>
          </Card>
        </div>
      </PageContainer>
    </div>
  );
};

export default BookDetails;
