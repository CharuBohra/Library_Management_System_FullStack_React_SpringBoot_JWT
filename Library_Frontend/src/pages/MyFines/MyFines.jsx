import { ErrorOutline, ReceiptLong } from "@mui/icons-material";
import PageContainer from "../components/PageContainer";
import PageHeader from "../components/PageHeader";
import { fines } from "../../data/fines";
import { formatMoney } from "../../utils/formatMoney";
import { Button } from "@mui/material";

const MyFines = () => {
  const totalOutstanding = fines
    .filter((f) => f.status !== "WAIVED")
    .reduce((sum, f) => sum + f.amountOutstanding, 0);

  const totalPaid = fines.reduce((sum, f) => sum + f.amountPaid, 0);

  const totalWaived = fines
    .filter((f) => f.status === "WAIVED")
    .reduce((sum, f) => sum + (f.amount - f.amountPaid), 0);

  const unpaidCount = fines.filter(
    (f) => f.amountOutstanding > 0 && f.status != "WAIVED",
  ).length;

  const hasOutstanding = totalOutstanding > 0;

  return (
    <div className="min-h-screen bg-linear-to-br from-indigo-50 via-white to-purple-50 py-8">
      <PageContainer>
        <PageHeader
          title="My Fines"
          subtitle="Overdue, damage and loss charges on your loans"
          icon={<ReceiptLong sx={{ fontSize: 36, color: "#4F46E5" }} />}
        />
        {hasOutstanding && (
          <div
            role="alert"
            className="mb-6 p-4 rounded-xl flex flex-wrap items-center gap-4 bg-red-50 border border-red-200"
          >
            <ErrorOutline sx={{ fontSize: 24, color: "#B91C1C" }} />
            <div className="flex flex-col flex-1 gap-1">
              <span className="text-xl font-bold text-red-700">
                You have {formatMoney(totalOutstanding)} outstanding
              </span>
              <span className="text-sm text-red-600">
                New borrowing is paused until unpaid fines are cleared. Pay each
                fine below.
              </span>
            </div>
            <div className="ml-auto">
              <Button
                variant="outlined"
                sx={{
                  background: "white",
                  color: "#B91C1C",
                  p: 1,
                  borderRadius: 2,
                  borderColor: "#B91C1C",
                  textTransform: "none",
                }}
              >
                Show unpaid fines
              </Button>
            </div>
          </div>
        )}
      </PageContainer>
    </div>
  );
};

export default MyFines;
