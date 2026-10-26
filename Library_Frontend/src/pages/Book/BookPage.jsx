import React from "react";
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

// Mirrors GenreDTO from the Spring Boot backend.
// LocalDateTime serialises as an ISO-8601 string (Spring Boot default).
export const genres = [
  {
    id: 1,
    code: "FICTION",
    name: "Fiction",
    description: "Imaginative narratives including novels and short stories.",
    displayOrder: 1,
    active: true,
    parentGenreId: null,
    parentGenreName: null,
    bookCount: 120,
    createdAt: "2026-01-10T09:15:00",
    updatedAt: "2026-09-02T14:30:00",
    subGenres: [
      {
        id: 11,
        code: "FICTION_FANTASY",
        name: "Fantasy",
        description: "Magic, mythical creatures and imaginary worlds.",
        displayOrder: 1,
        active: true,
        parentGenreId: 1,
        parentGenreName: "Fiction",
        bookCount: 45,
        createdAt: "2026-01-12T10:00:00",
        updatedAt: "2026-08-20T11:45:00",
        subGenres: [],
      },
      {
        id: 12,
        code: "FICTION_MYSTERY",
        name: "Mystery & Thriller",
        description: "Crime, suspense and detective stories.",
        displayOrder: 2,
        active: true,
        parentGenreId: 1,
        parentGenreName: "Fiction",
        bookCount: 38,
        createdAt: "2026-01-12T10:05:00",
        updatedAt: "2026-07-14T16:20:00",
        subGenres: [],
      },
      {
        id: 13,
        code: "FICTION_ROMANCE",
        name: "Romance",
        description: "Stories centred on relationships and love.",
        displayOrder: 3,
        active: true,
        parentGenreId: 1,
        parentGenreName: "Fiction",
        bookCount: 37,
        createdAt: "2026-01-12T10:10:00",
        updatedAt: "2026-06-30T09:00:00",
        subGenres: [],
      },
    ],
  },
  {
    id: 2,
    code: "NON_FICTION",
    name: "Non-Fiction",
    description: "Factual writing: biographies, history, self-help and more.",
    displayOrder: 2,
    active: true,
    parentGenreId: null,
    parentGenreName: null,
    bookCount: 95,
    createdAt: "2026-01-10T09:20:00",
    updatedAt: "2026-09-05T12:10:00",
    subGenres: [
      {
        id: 21,
        code: "NON_FICTION_BIOGRAPHY",
        name: "Biography",
        description: "Life stories of notable people.",
        displayOrder: 1,
        active: true,
        parentGenreId: 2,
        parentGenreName: "Non-Fiction",
        bookCount: 30,
        createdAt: "2026-01-15T08:30:00",
        updatedAt: "2026-08-01T10:00:00",
        subGenres: [],
      },
      {
        id: 22,
        code: "NON_FICTION_SELF_HELP",
        name: "Self-Help",
        description: "Personal growth, productivity and wellbeing.",
        displayOrder: 2,
        active: true,
        parentGenreId: 2,
        parentGenreName: "Non-Fiction",
        bookCount: 40,
        createdAt: "2026-01-15T08:35:00",
        updatedAt: "2026-09-10T15:25:00",
        subGenres: [],
      },
    ],
  },
  {
    id: 3,
    code: "SCIENCE_TECH",
    name: "Science & Technology",
    description: "Physics, computing, engineering and popular science.",
    displayOrder: 3,
    active: true,
    parentGenreId: null,
    parentGenreName: null,
    bookCount: 64,
    createdAt: "2026-01-11T11:00:00",
    updatedAt: "2026-08-28T17:40:00",
    subGenres: [
      {
        id: 31,
        code: "SCIENCE_PROGRAMMING",
        name: "Programming",
        description: "Software engineering, languages and frameworks.",
        displayOrder: 1,
        active: true,
        parentGenreId: 3,
        parentGenreName: "Science & Technology",
        bookCount: 28,
        createdAt: "2026-01-18T13:00:00",
        updatedAt: "2026-09-12T08:15:00",
        subGenres: [],
      },
    ],
  },
  {
    id: 4,
    code: "HISTORY",
    name: "History",
    description: "Events, civilisations and people of the past.",
    displayOrder: 4,
    active: true,
    parentGenreId: null,
    parentGenreName: null,
    bookCount: 33,
    createdAt: "2026-01-11T11:30:00",
    updatedAt: "2026-07-22T10:50:00",
    subGenres: [],
  },
  {
    id: 5,
    code: "CHILDREN",
    name: "Children's Books",
    description: "Picture books and early readers for young audiences.",
    displayOrder: 5,
    active: true,
    parentGenreId: null,
    parentGenreName: null,
    bookCount: 52,
    createdAt: "2026-02-01T09:00:00",
    updatedAt: "2026-09-18T13:35:00",
    subGenres: [],
  },
  {
    id: 6,
    code: "POETRY",
    name: "Poetry",
    description: "Collections of poems and verse.",
    displayOrder: 6,
    active: false,
    parentGenreId: null,
    parentGenreName: null,
    bookCount: 0,
    createdAt: "2026-03-05T14:00:00",
    updatedAt: "2026-10-01T09:30:00",
    subGenres: [],
  },
];

const cover = (isbn) => `https://covers.openlibrary.org/b/isbn/${isbn}-L.jpg`;

export const books = [
  {
    id: 1,
    isbn: "9780132350884",
    title: "Clean Code",
    author: "Robert C. Martin",
    genreId: 31,
    genreCode: "SCIENCE_PROGRAMMING",
    genreName: "Programming",
    publisher: "Prentice Hall",
    publicationDate: "2008-08-01",
    language: "English",
    pages: 464,
    description:
      "A handbook of agile software craftsmanship: writing code that is readable, maintainable and easy to change.",
    totalCopies: 5,
    availableCopies: 3,
    damagedCopies: 0,
    price: 32.99,
    coverImageUrl: cover("9780132350884"),
    active: true,
    createdAt: "2026-02-10T10:00:00",
    updatedAt: "2026-09-15T12:30:00",
  },
  {
    id: 2,
    isbn: "9780134685991",
    title: "Effective Java",
    author: "Joshua Bloch",
    genreId: 31,
    genreCode: "SCIENCE_PROGRAMMING",
    genreName: "Programming",
    publisher: "Addison-Wesley",
    publicationDate: "2018-01-06",
    language: "English",
    pages: 412,
    description:
      "Best practices for the Java platform, covering generics, lambdas, streams and concurrency.",
    totalCopies: 4,
    availableCopies: 1,
    damagedCopies: 1,
    price: 39.99,
    coverImageUrl: cover("9780134685991"),
    active: true,
    createdAt: "2026-02-12T09:30:00",
    updatedAt: "2026-10-01T08:45:00",
  },
  {
    id: 3,
    isbn: "9780135957059",
    title: "The Pragmatic Programmer",
    author: "David Thomas, Andrew Hunt",
    genreId: 31,
    genreCode: "SCIENCE_PROGRAMMING",
    genreName: "Programming",
    publisher: "Addison-Wesley",
    publicationDate: "2019-09-13",
    language: "English",
    pages: 352,
    description:
      "Your journey to mastery: practical advice on career, tools and writing flexible code.",
    totalCopies: 3,
    availableCopies: 3,
    damagedCopies: 0,
    price: 35.5,
    coverImageUrl: cover("9780135957059"),
    active: true,
    createdAt: "2026-03-01T11:15:00",
    updatedAt: "2026-08-20T16:00:00",
  },
  {
    id: 4,
    isbn: "9780547928227",
    title: "The Hobbit",
    author: "J.R.R. Tolkien",
    genreId: 11,
    genreCode: "FICTION_FANTASY",
    genreName: "Fantasy",
    publisher: "Houghton Mifflin Harcourt",
    publicationDate: "2012-09-18",
    language: "English",
    pages: 300,
    description:
      "Bilbo Baggins is swept into an epic quest to reclaim the lost Dwarf Kingdom of Erebor.",
    totalCopies: 6,
    availableCopies: 4,
    damagedCopies: 0,
    price: 10.99,
    coverImageUrl: cover("9780547928227"),
    active: true,
    createdAt: "2026-01-20T14:00:00",
    updatedAt: "2026-09-30T10:10:00",
  },
  {
    id: 5,
    isbn: "9780747532699",
    title: "Harry Potter and the Philosopher's Stone",
    author: "J.K. Rowling",
    genreId: 11,
    genreCode: "FICTION_FANTASY",
    genreName: "Fantasy",
    publisher: "Bloomsbury",
    publicationDate: "1997-06-26",
    language: "English",
    pages: 223,
    description:
      "An orphaned boy discovers he is a wizard and begins his first year at Hogwarts.",
    totalCopies: 4,
    availableCopies: 0,
    damagedCopies: 0, // all checked out
    price: 8.99,
    coverImageUrl: cover("9780747532699"),
    active: true,
    createdAt: "2026-01-25T09:00:00",
    updatedAt: "2026-10-20T18:20:00",
  },
  {
    id: 6,
    isbn: "9780307454546",
    title: "The Girl with the Dragon Tattoo",
    author: "Stieg Larsson",
    genreId: 12,
    genreCode: "FICTION_MYSTERY",
    genreName: "Mystery & Thriller",
    publisher: "Vintage",
    publicationDate: "2009-06-23",
    language: "English",
    pages: 644,
    description:
      "A journalist and a brilliant hacker investigate a decades-old disappearance.",
    totalCopies: 3,
    availableCopies: 2,
    damagedCopies: 1,
    price: 12.5,
    coverImageUrl: cover("9780307454546"),
    active: true,
    createdAt: "2026-04-05T13:45:00",
    updatedAt: "2026-09-02T09:05:00",
  },
  {
    id: 7,
    isbn: "9780062316097",
    title: "Sapiens: A Brief History of Humankind",
    author: "Yuval Noah Harari",
    genreId: 4,
    genreCode: "HISTORY",
    genreName: "History",
    publisher: "Harper",
    publicationDate: "2015-02-10",
    language: "English",
    pages: 443,
    description:
      "How Homo sapiens came to dominate the planet, from the Cognitive Revolution to today.",
    totalCopies: 2,
    availableCopies: 2,
    damagedCopies: 0,
    price: 18.99,
    coverImageUrl: cover("9780062316097"),
    active: false, // inactive, should be hidden
    createdAt: "2026-05-11T10:30:00",
    updatedAt: "2026-10-05T11:00:00",
  },
  {
    id: 8,
    isbn: "9780735211292",
    title: "Atomic Habits",
    author: "James Clear",
    genreId: 22,
    genreCode: "NON_FICTION_SELF_HELP",
    genreName: "Self-Help",
    publisher: "Avery",
    publicationDate: "2018-10-16",
    language: "English",
    pages: 320,
    description:
      "A proven framework for building good habits and breaking bad ones through small changes.",
    totalCopies: 5,
    availableCopies: 5,
    damagedCopies: 0,
    price: 16.99,
    coverImageUrl: cover("9780735211292"),
    active: true,
    createdAt: "2026-06-18T15:20:00",
    updatedAt: "2026-10-22T14:40:00",
  },
];

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
