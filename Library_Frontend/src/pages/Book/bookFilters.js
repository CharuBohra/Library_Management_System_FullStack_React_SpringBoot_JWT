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

  // ASC keeps localeCompare's order (A to Z); DESC flips it (Z to A).
  // sortBy is "title", "author" or "createdAt", so a[sortBy] reads that field.
  return [...filtered].sort((a, b) => {
    const result = String(a[sortBy]).localeCompare(String(b[sortBy])); //sortBy here is title , author or such ------ sortBy is a variable holding "title", "author" or "createdAt". Square brackets read the property with that name. So when sortBy = "title", a[sortBy] is the same as a.title. One line works for all three sort options.
    if (sortDirection === "ASC") {
      return result; // keep A to Z
    } else {
      return -result; // flip it to Z to A
    }
  });
};
