import {
  EventAvailable,
  History,
  LibraryBooks,
  ReceiptLong,
} from "@mui/icons-material";
import { formatMoney } from "../../utils/formatMoney";

export const statsConfig = ({
  currentLoans,
  activeReservations,
  booksRead,
  outstandingFines,
  unpaidFinesCount,
}) => [
  {
    id: "loans",
    title: "Current Loans",
    subtitle: "Books you are reading",
    value: currentLoans,
    icon: <LibraryBooks sx={{ fontSize: 32, color: "#4F46E5" }} />,
    bgColor: "bg-indigo-100",
    textColor: "text-indigo-600",
  },
  {
    id: "reservations",
    title: "Reservations",
    subtitle: "Books on hold",
    value: activeReservations,
    icon: <EventAvailable sx={{ fontSize: 32, color: "#9333EA" }} />,
    bgColor: "bg-purple-100",
    textColor: "text-purple-600",
  },
  {
    id: "read",
    title: "Books Read",
    subtitle: "Returned books",
    value: booksRead,
    icon: <History sx={{ fontSize: 32, color: "#10B981" }} />,
    bgColor: "bg-green-100",
    textColor: "text-green-600",
  },
  {
    id: "fines",
    title: "Outstanding Fines",
    subtitle: `${unpaidFinesCount} unpaid ${unpaidFinesCount === 1 ? "fine" : "fines"}`,
    value: formatMoney(outstandingFines),
    icon: <ReceiptLong sx={{ fontSize: 32, color: "#DC2626" }} />,
    bgColor: "bg-red-100",
    textColor: "text-red-600",
  },
];
