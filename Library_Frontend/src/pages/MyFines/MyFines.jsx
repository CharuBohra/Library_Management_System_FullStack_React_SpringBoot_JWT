import { ErrorOutline, ReceiptLong } from "@mui/icons-material";
import PageContainer from "../components/PageContainer";
import PageHeader from "../components/PageHeader";
import { fines } from "../../data/fines";
import { formatMoney } from "../../utils/formatMoney";
import { Button, FormControl, MenuItem, Select } from "@mui/material";
import FineRow from "./FineRow";
import { useState } from "react";
import { FINE_TYPES } from "./fineConfig";
import StatCard from "../components/StatCard";

const MyFines = () => {
  const [statusFilter, setStatusFilter] = useState(null);
  const [typeFilter, setTypeFilter] = useState(null);

  const totalOutstanding = fines
    .filter((f) => f.status !== "WAIVED")
    .reduce((sum, f) => sum + f.amountOutstanding, 0);

  const totalPaid = fines.reduce((sum, f) => sum + f.amountPaid, 0);

  const totalWaived = fines
    .filter((f) => f.status === "WAIVED")
    .reduce((sum, f) => sum + (f.amount - f.amountPaid), 0);

  const unpaidCount = fines.filter(
    (f) => f.amountOutstanding > 0 && f.status !== "WAIVED",
  ).length;

  const hasOutstanding = totalOutstanding > 0;

  const visibleFines = fines.filter(
    (f) =>
      (statusFilter === null || f.status === statusFilter) &&
      (typeFilter === null || f.type === typeFilter),
  );

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

  const statusChips = [
    { value: null, label: "All" },
    { value: "PENDING", label: "Pending" },
    { value: "PARTIALLY_PAID", label: "Partially Paid" },
    { value: "PAID", label: "Paid" },
    { value: "WAIVED", label: "Waived" },
  ];

  const countFor = (status) =>
    status === null
      ? fines.length
      : fines.filter((f) => f.status === status).length;

  const handlePay = (fine) => {
    console.log("POST /api/fines/" + fine.id + "/pay");
  };

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
                onClick={() => {
                  setStatusFilter("PENDING");
                  setTypeFilter(null);
                }}
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
            <StatCard
              key={card.id}
              label={card.label}
              value={card.value}
              hint={card.hint}
              valueClass={card.valueClass}
            />
          ))}
        </div>
        <div className="bg-white rounded-xl shadow-sm border border-gray-300 overflow-hidden">
          <div className="flex flex-wrap items-center justify-between gap-2 p-4 border-b border-gray-200">
            <div className="flex flex-wrap gap-2">
              {statusChips.map((chip) => {
                const isActive = statusFilter === chip.value;
                return (
                  <button
                    key={chip.label}
                    type="button"
                    onClick={() => setStatusFilter(chip.value)}
                    aria-pressed={isActive}
                    className={`px-3 py-1.5 rounded-full text-sm font-semibold border transition-colors ${
                      isActive
                        ? "bg-indigo-100 text-indigo-700 border-indigo-200"
                        : "bg-white text-gray-700 border-gray-200 hover:bg-gray-50"
                    }`}
                  >
                    {chip.label} ({countFor(chip.value)})
                  </button>
                );
              })}
            </div>
            <div className="flex items-center gap-2 md:w-64">
              <label
                id="fine-type-label"
                htmlFor="fine-type-select"
                className="text-base font-semibold text-gray-600"
              >
                Type
              </label>
              <FormControl fullWidth>
                <Select
                  labelId="fine-type-label"
                  id="fine-type-select"
                  value={typeFilter ?? ""}
                  displayEmpty
                  label="Type"
                  onChange={(e) => setTypeFilter(e.target.value || null)}
                  sx={{
                    minWidth: 160,
                    fontSize: "0.875rem", // 14px, same as text-sm
                    fontFamily: "inherit", // use the page font instead of Roboto
                    color: "#374151", // text-gray-700
                  }}
                  MenuProps={{
                    slotProps: {
                      paper: {
                        sx: {
                          "& .MuiMenuItem-root": {
                            fontSize: "0.875rem", // 14px, same as text-sm
                            fontFamily: "inherit",
                            color: "#374151",
                          },
                        },
                      },
                    },
                  }}
                >
                  <MenuItem value="">All Types</MenuItem>
                  {Object.entries(FINE_TYPES).map(([key, t]) => (
                    <MenuItem key={key} value={key}>
                      {t.label}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            </div>
          </div>
          <div className="overflow-x-auto">
            {visibleFines.length === 0 ? (
              <p className="p-8 text-center text-gray-500">
                No fines match these filters.
              </p>
            ) : (
              <table className=" w-full min-w-[860px] text-sm">
                <thead className="bg-gray-50 border-b border-gray-200">
                  <tr className="uppercase tracking-wide font-semibold text-xs text-left">
                    <th className="px-4 py-3">Book</th>
                    <th className="px-4 py-3">Type</th>
                    <th className="px-4 py-3 text-right">Amount</th>
                    <th className="px-4 py-3 text-right">Paid</th>
                    <th className="px-4 py-3 text-right">Outstanding</th>
                    <th className="px-4 py-3">Status</th>
                    <th className="px-4 py-3">Date</th>
                    <th className="px-4 py-3 w-40">
                      <span className="sr-only">Action</span>
                    </th>
                  </tr>
                </thead>
                <tbody>
                  {visibleFines.map((fine) => (
                    <FineRow key={fine.id} fine={fine} onPay={handlePay} />
                  ))}
                </tbody>
              </table>
            )}
            ;
          </div>
        </div>
        <div className="text-xs text-gray-500 py-4">
          Payments are processed by Razorpay . You'll return here once the
          payment is confirmed.
        </div>
      </PageContainer>
    </div>
  );
};

export default MyFines;
