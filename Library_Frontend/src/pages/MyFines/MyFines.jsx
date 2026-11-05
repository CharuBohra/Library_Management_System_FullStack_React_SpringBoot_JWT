import { ErrorOutline, ReceiptLong } from "@mui/icons-material";
import PageContainer from "../components/PageContainer";
import PageHeader from "../components/PageHeader";
import { fines } from "../../data/fines";
import { formatMoney } from "../../utils/formatMoney";
import { Button } from "@mui/material";
import FineStateCard from "./FineStateCard";

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

  const stateCards = [
    {
      id: "outstanding",
      label: "Outstanding",
      value: formatMoney(totalOutstanding),
      hint: `${unpaidCount} ${unpaidCount === 1 ? "fine" : "fines"}`,
      valueClass: "text-red-700",
    },
    {
      id: "paid",
      label: "Paid",
      value: formatMoney(totalPaid),
      hint: "including partial payments",
      valueClass: "text-green-700",
    },
    {
      id: "waived",
      label: "Waived",
      value: formatMoney(totalWaived),
      hint: "by library staff",
    },
  ];

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
            <div className="flex flex-col flex-1 gap-2">
              <span className="text-xl font-bold text-red-700">
                You have {formatMoney(totalOutstanding)} outstanding
              </span>
              <span className="text-sm text-red-700">
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
        <div className="grid grid-cols-1 sm:grid-cols-3 gap-4 mb-6">
          {stateCards.map((card) => (
            <FineStateCard
              key={card.id}
              label={card.label}
              value={card.value}
              hint={card.hint}
              valueClass={card.valueClass}
            />
          ))}
        </div>
      </PageContainer>
    </div>
  );
};

export default MyFines;
