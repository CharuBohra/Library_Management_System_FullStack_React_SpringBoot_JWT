import { AccessAlarm, Book, CheckCircle } from "@mui/icons-material";

export const tabs = [
  {
    label: "All Reservations",
    value: "ALL",
    icon: <Book className="w-5 h-5" />,
  },
  {
    label: "Active",
    value: "ACTIVE",
    statuses: ["PENDING", "AVAILABLE"],
    icon: <AccessAlarm className="w-5 h-5" />,
  },
  {
    label: "Completed",
    value: "COMPLETED",
    statuses: ["CANCELLED", "FULFILLED", "EXPIRED"],
    icon: <CheckCircle className="w-5 h-5" />,
  },
];
