import { useParams } from "react-router";
import { books } from "../../data/books";
import PageContainer from "../components/PageContainer";
import { ArrowLeft } from "@mui/icons-material";
import { Box, Card, CardContent, Rating, Typography } from "@mui/material";
import { reviews } from "../../data/reviews";
import { formatDate } from "../../utils/formatDate";

const BookDetails = () => {
  const { id } = useParams();
  const book = books.find((b) => b.id === Number(id));
  const review = reviews.filter((r) => r.bookId === Number(id));

  const reviewCount = review.length;
  const avgRating =
    reviewCount === 0
      ? 0
      : review.reduce((sum, r) => sum + r.rating, 0) / reviewCount;
  if (!book) return <p>Book Not Found</p>;
  return (
    <div className="min-h-screen bg-linear-to-br from-indigo-50 via-white to-purple-50 py-8">
      <PageContainer>
        <div className="mb-8">
          <div>
            <ArrowLeft sx={{ fontSize: 24 }} />
            <span>Browse Books / {book.title}</span>
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
                  <h1 className="font-bold">{book.title}</h1>
                  <div className="text-sm font-semibold">by {book.author}</div>
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
                    <p className="text-sm">{book.description}</p>
                  </div>
                  <div className="grid grid-cols-[repeat(auto-fill,minmax(110px,1fr))] gap-x-5 gap-y-4 bg-gray-50 rounded-xl p-4">
                    <div className="flex flex-col gap-1">
                      <div className="text-xs font-semibold uppercase tracking-wide">
                        Publisher
                      </div>
                      <div className="text-sm font-semibold">
                        {book.publisher}
                      </div>
                    </div>
                    <div className="flex flex-col gap-1">
                      <div className="text-xs font-semibold uppercase tracking-wide">
                        Published
                      </div>
                      <div className="text-sm font-semibold">
                        {formatDate(book.publicationDate)}
                      </div>
                    </div>
                    <div className="flex flex-col gap-1">
                      <div className="text-xs font-semibold uppercase tracking-wide">
                        Language
                      </div>
                      <div className="text-sm font-semibold">
                        {book.language}
                      </div>
                    </div>
                    <div className="flex flex-col gap-1">
                      <div className="text-xs font-semibold uppercase tracking-wide">
                        Pages
                      </div>
                      <div className="text-sm font-semibold">{book.pages}</div>
                    </div>
                    <div className="flex flex-col gap-1">
                      <div className="text-xs font-semibold uppercase tracking-wide">
                        ISBN
                      </div>
                      <div className="text-sm font-semibold">{book.isbn}</div>
                    </div>
                  </div>
                </div>
              </Box>
            </CardContent>
          </Card>
        </div>
      </PageContainer>
    </div>
  );
};

export default BookDetails;
