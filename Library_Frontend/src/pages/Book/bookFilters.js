// Ids to match for a genre: the genre itself plus its sub-genres
const getGenreIds = (genres, genreId) => {
  const genre = genres.find((g) => g.id === genreId);
  const childIds = genre?.subGenres?.map((sub) => sub.id) ?? [];
  return [genreId, ...childIds];
};

// Mirrors GET /api/books (genreId, availableOnly, sortBy, sortDirection) plus text search
export const filterAndSortBooks = (
  books,
  genres,
  { genreId, availableOnly, searchTerm, sortBy, sortDirection },
) => {
  const genreIds = genreId ? getGenreIds(genres, genreId) : null;
  const term = searchTerm.trim().toLowerCase();

  const filtered = books.filter(
    (book) =>
      book.active &&
      (!genreIds || genreIds.includes(book.genreId)) &&
      (!availableOnly || book.availableCopies > 0) &&
      (!term ||
        book.title.toLowerCase().includes(term) ||
        book.author.toLowerCase().includes(term) ||
        book.genreName.toLowerCase().includes(term)),
  );

  const direction = sortDirection === "ASC" ? 1 : -1;
  return [...filtered].sort(
    (a, b) => String(a[sortBy]).localeCompare(String(b[sortBy])) * direction,
  );
};
