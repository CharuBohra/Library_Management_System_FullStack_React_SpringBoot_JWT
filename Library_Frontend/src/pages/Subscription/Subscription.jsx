import { CardMembership } from "@mui/icons-material";
import PageContainer from "../components/PageContainer";
import PageHeader from "../components/PageHeader";
import { activeSubscription } from "../../data/subscription";
import { formatDate } from "../../utils/formatDate";
import { loans } from "../../data/loans";
import { LinearProgress } from "@mui/material";
import { formatMoney } from "../../utils/formatMoney";
import { plans } from "../../data/plans";
import PlanCard from "./PlanCard";

const Subscription = () => {
  const getSubscriptionStatus = (sub) => {
    if (!sub) return null;
    if (sub.cancelledAt)
      return { label: "Cancelled", classes: "bg-gray-100 text-gray-700" };
    if (sub.expiredAt)
      return { label: "Expired", classes: "bg-red-100 text-red-700" };
    if (sub.isValid)
      return { label: "Active", classes: "bg-green-100 text-green-700" };
    return { label: "Inactive", classes: "bg-gray-100 text-gray-700" };
  };

  const status = getSubscriptionStatus(activeSubscription);

  const activeLoanCount = loans.filter((l) =>
    ["CHECKED_OUT", "OVERDUE"].includes(l.status),
  ).length;

  const maxBooks = activeSubscription?.maxBooksAllowed ?? 0;
  const usagePercent = maxBooks
    ? Math.min((activeLoanCount / maxBooks) * 100, 100)
    : 0;
  const atLimit = activeLoanCount >= maxBooks;

  const visiblePlans = plans.filter((p) => p.active !== false);
  return (
    <div className="min-h-screen bg-linear-to-br from-indigo-100 via-white to-purple-100 py-8">
      <PageContainer>
        <PageHeader
          title="Subscriptions"
          subtitle="Your plan decides how many books you can borrow and for how long"
          icon={<CardMembership sx={{ fontSize: 36, color: "#4F46E5" }} />}
        />
        <div className="bg-white rounded-xl shadow-md border border-gray-300 min-w-0 p-6 mb-8">
          <div className="flex flex-wrap items-start justify-between gap-8">
            <div className="flex flex-col flex-1 min-w-0 gap-4">
              <div className="flex items-center gap-3 ">
                <span className=" text-gray-500 text-xs font-semibold uppercase tracking-wide">
                  Current Plan
                </span>
                {status && (
                  <span
                    className={`inline-flex px-2.5 py-0.5 font-semibold items-center gap-1.5 rounded-full text-xs ${status.classes}`}
                  >
                    ● {status.label}
                  </span>
                )}
              </div>
              <div className="text-3xl font-extrabold text-gray-900">
                {activeSubscription.planName}
              </div>
              <div className="text-gray-500 text-base font-medium">
                <span>
                  {formatDate(activeSubscription.startDate)} -
                  {formatDate(activeSubscription.endDate)} ~{" "}
                </span>
                <span className="font-bold text-gray-900">
                  {activeSubscription.daysRemaining} days left
                </span>
              </div>
              <div className="flex flex-wrap items-center justify-between">
                <span className="text-xs text-gray-600 ">
                  Books on loan right now
                </span>
                <span className="font-semibold">
                  {activeLoanCount} of {maxBooks}
                </span>
              </div>
              <LinearProgress
                variant="determinate"
                value={usagePercent}
                sx={{
                  height: 10,
                  borderRadius: 5,
                  bgcolor: "#EEF2FF",
                  "& .MuiLinearProgress-bar": {
                    borderRadius: 5,
                    bgcolor: atLimit ? "#D97706" : "#4F46E5",
                  },
                }}
              />
              {atLimit && (
                <p className="text-amber-700 text-xs">
                  You've reached your plan limit. Return a book to borrow
                  another.
                </p>
              )}
            </div>
            <div className="ml-auto w-full sm:w-64 shrink-0 bg-gray-50 rounded-lg p-6">
              <div className="flex flex-col gap-3 space-y-1">
                <div className="flex justify-between gap-4">
                  <span className="text-gray-600 text-sm ">
                    Max books at once
                  </span>
                  <span className="font-bold text-sm text-gray-900">
                    {maxBooks}
                  </span>
                </div>
                <div className="flex justify-between gap-4">
                  <span className="text-gray-600 text-sm ">Days per book</span>
                  <span className="font-bold text-sm text-gray-900">
                    {activeSubscription.maxDaysPerBook}
                  </span>
                </div>
                <div className="flex justify-between gap-4">
                  <span className="text-gray-600 text-sm ">Auto-renew</span>
                  <span className="font-bold text-sm text-gray-900">
                    {activeSubscription.autoRenew ? "On" : "Off"}
                  </span>
                </div>
                <div className="flex justify-between gap-4">
                  <span className="text-gray-600 text-sm ">Paid</span>
                  <span className="font-bold text-sm text-gray-900">
                    {formatMoney(activeSubscription.price)}
                  </span>
                </div>
                <div className="flex justify-between gap-4">
                  <button
                    type="button"
                    className="self-start text-sm font-semibold text-red-600 hover:underline"
                  >
                    Cancel subscription…
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div className="text-3xl font-bold text-gray-900 my-3">
          Available Plans
        </div>
        <div className="grid grid-cols-[repeat(auto-fill,minmax(350px,1fr))] gap-5 mt-2">
          {visiblePlans.map((plan) => (
            <PlanCard
              key={plan.id}
              plan={plan}
              currentPlanId={activeSubscription?.planId}
            />
          ))}
        </div>
      </PageContainer>
    </div>
  );
};

export default Subscription;
