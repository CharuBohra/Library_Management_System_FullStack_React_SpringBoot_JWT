import { MenuBook } from "@mui/icons-material";
import { useState } from "react";

const BookCover = ({ src, alt, className = "" }) => {
  const [imageFailed, setImageFailed] = useState(false);
  const showImage = src && !imageFailed;
  return (
    <div
      className={`relative aspect-2/3 bg-linear-to-br from-indigo-100 to-purple-100 overflow-hidden ${className} `}
    >
      {showImage ? (
        <img
          src={src}
          alt={alt}
          onError={() => {
            setImageFailed(true);
          }}
          className="w-full h-full object-cover"
        />
      ) : (
        <div className="w-full h-full flex items-center justify-center">
          <MenuBook sx={{ fontSize: 64, color: "#A5B4FC" }} />
        </div>
      )}
    </div>
  );
};

export default BookCover;
