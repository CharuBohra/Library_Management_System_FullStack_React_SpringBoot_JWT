import { Chip } from "@mui/material";
const GetStatusChip = ({ status }) => {
  const configs = {
    CHECKED_OUT: { label: "On Loan", color: "primary" },
    OVERDUE: { label: "Overdue", color: "error" },
    PENDING: { label: "In queue", color: "warning" },
    AVAILABLE: { label: "Ready for pickup", color: "success" },
  };

  const config = configs[status] || { label: status, color: "default" };
  return <Chip label={config.label} color={config.color} size="small" />;
};

export default GetStatusChip;
