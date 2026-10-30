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
    icon: <AccessAlarm className="w-5 h-5" />,
  },
  {
    label: "Completed",
    value: "COMPLETED",
    icon: <CheckCircle className="w-5 h-5" />,
  },
];
