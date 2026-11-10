import { ArrowForward } from "@mui/icons-material";
import { Link } from "react-router";

const StatCard = ({
  label,
  value,
  hint,
  valueClass = "text-gray-900",
  to,
  linkText,
}) => {
  return (
    <div
      className={`flex flex-col gap-2 h-full p-5 bg-white border border-gray-200 rounded-xl shadow-sm ${
        to
          ? "transition-all duration-300 hover:shadow-lg hover:-translate-y-1"
          : ""
      }`}
    >
      <span className="text-sm font-semibold text-gray-500 uppercase tracking-wide">
        {label}
      </span>

      <p className={`text-4xl font-extrabold ${valueClass}`}>{value}</p>

      {hint && <span className="text-sm text-gray-500">{hint}</span>}

      {to && (
        <Link
          to={to}
          className={`mt-auto pt-2 self-start inline-flex items-center gap-1 text-sm font-semibold hover:underline ${valueClass}`}
        >
          {linkText}
          <ArrowForward sx={{ fontSize: 16 }} />
        </Link>
      )}
    </div>
  );
};

export default StatCard;
