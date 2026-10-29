import GenreFilter from "./GenreFilter";
import { useState } from "react";
import {
  FormControl,
  InputAdornment,
  InputLabel,
  MenuItem,
  Select,
  TextField,
} from "@mui/material";
import { Search, Sort } from "@mui/icons-material";
import BookCard from "./BookCard";
import { books } from "./books";
import { genres } from "./genres";

const BookPage = () => {
  const [selectedGenreId, setSelectedGenreId] = useState(null);
  const [availability, setAvailability] = useState("ALL");
  const [searchTerm, setSearchTerm] = useState("");
  const [sortBy, setSortBy] = useState("createdAt");
  const [sortDirection, setSortDirection] = useState("DESC");

  const activeBooks = books.filter((b) => b.active);
  const activeGenres = genres.filter((g) => g.active);

  const handleGenreChange = (genreId) => {
    setSelectedGenreId(genreId);
  };

  const handleSortChange = (value) => {
    const [field, direction] = value.split("-");
    setSortBy(field);
    setSortDirection(direction.toUpperCase());
  };

  const getCurrentSortValue = () => {
    return `${sortBy}-${sortDirection.toLowerCase()}`;
  };
  return (
    <div className="min-h-screen bg-linear-to-br from-indigo-50 via-white to-purple-50">
      {/* header */}
      <div className="bg-white border-b border-gray-200 text-center">
        <div className="px-4 sm:px-6 lg:px-8 py-8">
          <h1 className="text-4xl md:text-5xl font-bold mb-4 ">
            Browse Our{" "}
            <span className="bg-linear-to-br from-indigo-600 to-purple-600 bg-clip-text text-transparent">
              Collection
            </span>
          </h1>
          <p className="text-lg text-gray-600">
            Discover thousands of books across all genres
          </p>
        </div>
      </div>
      {/* Main Content */}
      <div className="px-4 sm:px-6 lg:px-8 py-8">
        <div className="flex flex-col lg:flex-row gap-8">
          {/* Sidebar Filter */}
          <aside className="lg:w-72 space-y-6">
            {/* Filter Container */}
            <div className="space-y-6">
              <GenreFilter
                genres={activeGenres}
                onGenreSelect={handleGenreChange}
                selectedGenreId={selectedGenreId}
              />
              {/* Availability Filter  */}
              <div className="bg-white rounded-xl shadow-md p-4 border border-gray-100">
                <h3 className="text-lg font-bold text-gray-900 mb-4 pb-3 border-b border-gray-200">
                  Availability
                </h3>
                <FormControl fullWidth>
                  <InputLabel id="demo-simple-select-label">
                    Availability
                  </InputLabel>
                  <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    value={availability}
                    label="Availability"
                    inputProps={{ "aria-label": "Availability" }}
                    onChange={(e) => setAvailability(e.target.value)}
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
                    <MenuItem value={"ALL"}>All Books</MenuItem>
                    <MenuItem value={"AVAILABLE"}>Available Books</MenuItem>
                    <MenuItem value={"CHECKED_OUT"}>Checked Out</MenuItem>
                  </Select>
                </FormControl>
              </div>
            </div>
          </aside>

          {/* Main Content Area */}
          <main className="flex-1 space-y-6">
            {/* Search and sort  */}
            <div className="flex flex-col md:flex-row gap-4">
              {/* Search Bar */}
              <div className="flex-1">
                <TextField
                  fullWidth
                  placeholder="Search by Title , Author or Category"
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                  slotProps={{
                    input: {
                      startAdornment: (
                        <InputAdornment position="start">
                          <Search className="text-gray-400" />
                        </InputAdornment>
                      ),
                    },
                  }}
                  sx={{
                    "& .MuiOutlinedInput-root": {
                      "&:hover fieldset": {
                        borderColor: "#4F46E5",
                      },
                    },
                    "& .Mui-focused fieldset": {
                      borderColor: "#4F46E5",
                    },
                  }}
                />
              </div>
              {/* Sort Dropdown */}
              <div className="md:w-64">
                <FormControl fullWidth>
                  <InputLabel id="demo-simple-select-label">Sort By</InputLabel>
                  <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    value={getCurrentSortValue()}
                    label="Sort By"
                    onChange={(e) => handleSortChange(e.target.value)}
                    startAdornment={
                      <InputAdornment position="start">
                        <Sort className="text-gray-400" />
                      </InputAdornment>
                    }
                    sx={{
                      "& .MuiOutlinedInput-notchedOutline": {
                        borderColor: "#E5E7EB",
                      },
                      "&:hover .MuiOutlinedInput-notchedOutline": {
                        borderColor: "#4F46E5",
                      },
                      "& .Mui-focused .MuiOutlinedInput-notchedOutline": {
                        borderColor: "#4F46E5",
                      },
                    }}
                  >
                    <MenuItem value={"title-asc"}>Title(A-Z)</MenuItem>
                    <MenuItem value={"title-desc"}>Title(Z-A)</MenuItem>
                    <MenuItem value={"author-asc"}>Author(A-Z)</MenuItem>
                    <MenuItem value={"author-desc"}>Author(Z-A)</MenuItem>
                    <MenuItem value={"createdAt-asc"}>Newest First</MenuItem>
                    <MenuItem value={"createdAt-desc"}>Oldest First</MenuItem>
                  </Select>
                </FormControl>
              </div>
            </div>

            {/* Books Grid  */}
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
              {activeBooks.map((book) => (
                <BookCard key={book.id} book={book} />
              ))}
            </div>
          </main>
        </div>
      </div>
    </div>
  );
};

export default BookPage;
