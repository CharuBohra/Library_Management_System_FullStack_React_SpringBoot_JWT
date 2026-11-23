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
import PageContainer from "../components/PageContainer";
import { getGenres } from "../../api/genreApi";
import { useEffect } from "react";
import { getBooks } from "../../api/bookApi";
import { getErrorMessage } from "../../utils/getErrorMessage";

const BookPage = () => {
  const [selectedGenreId, setSelectedGenreId] = useState(null);
  const [availableOnly, setAvailableOnly] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");
  const [sortBy, setSortBy] = useState("createdAt");
  const [sortDirection, setSortDirection] = useState("DESC");
  const [reloadCount, setReloadCount] = useState(0);

  const requestKey = `${selectedGenreId} | ${availableOnly} | ${sortBy} | ${sortDirection} | ${reloadCount}`;
  const [result, setResult] = useState({
    key: null,
    books: [],
    total: 0,
    error: null,
  });

  const [genres, setGenres] = useState([]);

  useEffect(() => {
    let ignore = false;
    const load = async () => {
      try {
        const data = await getGenres();
        if (!ignore) setGenres(data);
      } catch {
        if (!ignore) setGenres([]);
      }
    };
    load();
    return () => {
      ignore = true;
    };
  }, []);

  useEffect(() => {
    let ignore = false;
    const load = async () => {
      try {
        const data = await getBooks({
          genreId: selectedGenreId,
          availableOnly,
          sortBy,
          sortDirection,
          page: 0,
          pageSize: 20,
        });
        if (!ignore) {
          setResult({
            key: requestKey,
            books: data.content,
            total: data.totalElements,
            error: null,
          });
        }
      } catch (error) {
        if (!ignore) {
          setResult({
            key: requestKey,
            books: [],
            total: 0,
            error: getErrorMessage(error),
          });
        }
      }
    };
    load();
    return () => {
      ignore = true;
    };
  }, [requestKey, selectedGenreId, availableOnly, sortBy, sortDirection]);

  const activeGenres = genres.filter(
    (g) => g.active && g.parentGenreId === null,
  );

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

  const loading = result.key !== requestKey;
  const visibleBooks = result.books;

  const renderBooks = () => {
    // 1. Loading
    if (loading) {
      return (
        <div className="p-8 text-gray-600 text-center">Loading books…</div>
      );
    }
    // 2. Error
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

    // 3.no data
    if (visibleBooks.length === 0) {
      return (
        <p className="p-8 text-center text-gray-500">
          No books match these filters.
        </p>
      );
    }

    return (
      <div className="space-y-4">
        <p className="text-sm text-gray-600">
          Showing {visibleBooks.length} of {result.total}{" "}
          {result.total === 1 ? "book" : "books"}
        </p>
        <div className="grid gap-6 grid-cols-[repeat(auto-fill,minmax(min(100%,300px),1fr))]">
          {visibleBooks.map((book) => (
            <BookCard key={book.id} book={book} />
          ))}
        </div>
      </div>
    );
  };
  return (
    <div className="min-h-screen bg-linear-to-br from-indigo-50 via-white to-purple-50">
      <PageContainer>
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
                {/* availableOnly Filter  */}
                <div className="bg-white rounded-xl shadow-md p-4 border border-gray-100">
                  <h3 className="text-lg font-bold text-gray-900 mb-4 pb-3 border-b border-gray-200">
                    Availability
                  </h3>
                  <FormControl fullWidth>
                    <InputLabel id="availability-label">
                      Availability
                    </InputLabel>
                    <Select
                      labelId="availability-label"
                      id="availability-select"
                      value={availableOnly ? "AVAILABLE" : "ALL"}
                      label="availability"
                      inputProps={{ "aria-label": "availability" }}
                      onChange={(e) =>
                        setAvailableOnly(e.target.value === "AVAILABLE")
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
                              "& .MuiMenuItem-root": {
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
                    <InputLabel id="sort-label">Sort By</InputLabel>
                    <Select
                      labelId="sort-label"
                      id="sort-select"
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
                      <MenuItem value={"createdAt-desc"}>Newest First</MenuItem>
                      <MenuItem value={"createdAt-asc"}>Oldest First</MenuItem>
                    </Select>
                  </FormControl>
                </div>
              </div>
              {/* Books Grid  */}
              {renderBooks()}
            </main>
          </div>
        </div>
      </PageContainer>
    </div>
  );
};

export default BookPage;
