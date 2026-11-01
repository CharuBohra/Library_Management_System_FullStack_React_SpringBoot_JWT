import { useParams } from "react-router";
import { books } from "./books";

const BookDetails = () => {
  const { id } = useParams();
  const book = books.find((b) => b.id === Number(id));
  if (!book) return <p>Book Not Found</p>;
  return <div>BookDetails</div>;
};

export default BookDetails;
