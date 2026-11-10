import { Button } from "@mui/material";
import { fines } from "../../data/fines";
import { loans } from "../../data/loans";
import { mockReservations } from "../../data/reservations";
import { activeSubscription } from "../../data/subscription";
import { currentUser } from "../../data/user";
import { wishlist } from "../../data/wishlist";
import { formatDate, formatDateTime } from "../../utils/formatDate";
import { getInitials } from "../../utils/getInitials";
import PageContainer from "../components/PageContainer";
import { formatMoney } from "../../utils/formatMoney";
import StatCard from "../components/StatCard";

const UserProfile = () => {
  const activeLoanCount = loans.filter((l) =>
    ["CHECKED_OUT", "OVERDUE"].includes(l.status),
  ).length;
  const activeReservationCount = mockReservations.filter((r) =>
    ["PENDING", "AVAILABLE"].includes(r.status),
  ).length;
  const wishlistCount = wishlist.length;
  const finesDue = fines
    .filter((f) => f.status !== "WAIVED")
    .reduce((sum, f) => sum + f.amountOutstanding, 0);

  const ROLE_LABELS = {
    CUSTOMER: "Customer",
    ADMIN: "Admin",
  };

  const profileStats = [
    {
      id: "loans",
      label: "Current Loans",
      value: activeLoanCount,
      classes: "text-indigo-600",
      to: "/my-loans",
      linkText: "My Loans",
    },
    {
      id: "reservations",
      label: "Reservations",
      value: activeReservationCount,
      classes: "text-amber-700",
      to: "/my-reservations",
      linkText: "My Reservations",
    },
    {
      id: "wishlist",
      label: "Wishlist",
      value: wishlistCount,
      classes: "text-pink-700",
      to: "/wishlist",
      linkText: "Wishlist",
    },
    {
      id: "fines",
      label: "Fines Due",
      value: formatMoney(finesDue),
      classes: "text-red-700",
      to: "/my-fines",
      linkText: "My Fines",
    },
  ];

  const accountDetails = [
    { label: "Full name", value: currentUser.fullName },
    { label: "Email", value: currentUser.email },
    { label: "Phone", value: currentUser.phone },
    {
      label: "Current plan",
      value: activeSubscription
        ? `${activeSubscription.planName} · until ${formatDate(activeSubscription.endDate)}`
        : "No active plan",
    },
  ];
  return (
    <div className="min-h-screen bg-linear-to-br from-indigo-100 via-white to-purple-100 py-8">
      <PageContainer>
        <div className="bg-white rounded-lg shadow-md p-6">
          <div className="flex flex-wrap items-start gap-6">
            <div className="text-4xl flex items-center justify-center font-extrabold rounded-full w-26 h-26 bg-indigo-700 text-white">
              {getInitials(currentUser.fullName)}
            </div>
            <div className="flex flex-col flex-1 gap-2">
              <div className="flex flex-wrap items-center min-w-0 gap-3">
                <h1 className="text-4xl font-extrabold text-gray-900 ">
                  {currentUser.fullName}
                </h1>
                <span className="inline-flex px-2.5 py-1 font-semibold items-center gap-1.5 rounded-full text-xs bg-indigo-100 text-indigo-700">
                  {ROLE_LABELS[currentUser.role] ?? currentUser.role}
                </span>
              </div>
              <span className="text-gray-700 text-lg">{currentUser.email}</span>
              <span className="text-gray-600 text-sm">
                Last login {formatDateTime(currentUser.lastLogin)}
              </span>
            </div>
          </div>
        </div>
        <div className="grid grid-cols-[repeat(auto-fill,minmax(240px,1fr))] gap-5 mt-8">
          {profileStats.map((profileTab) => (
            <StatCard
              key={profileTab.id}
              label={profileTab.label}
              value={profileTab.value}
              valueClass={profileTab.classes}
              to={profileTab.to}
              linkText={profileTab.linkText}
            />
          ))}
        </div>
        <div className="flex flex-wrap mt-8 gap-6 w-full ">
          <div className="flex flex-col flex-1 min-w-0 basis-96 bg-white rounded-lg shadow-md p-8 gap-5">
            <h2 className="text-2xl font-bold text-gray-900">
              Account Details
            </h2>
            <div className="grid grid-cols-[repeat(auto-fill,minmax(200px,1fr))] gap-x-10 gap-y-5">
              {accountDetails.map((detail) => (
                <div key={detail.label} className="flex flex-col gap-1 ">
                  <span className=" text-xs font-semibold uppercase tracking-wide text-gray-500 ">
                    {detail.label}
                  </span>
                  <span className="font-semibold text-base text-gray-900">
                    {detail.value}
                  </span>
                </div>
              ))}
            </div>
          </div>
          <div className="flex flex-col w-full sm:w-80 shrink-0 bg-white rounded-lg shadow-md p-8 gap-3">
            <h2 className="text-2xl font-bold text-gray-900">Security</h2>
            <p className="text-sm text-gray-500">
              We'll email a reset link to {currentUser.email}
            </p>
            <Button
              variant="outlined"
              fullWidth
              sx={{
                py: 1,
                bgcolor: "white",
                textTransform: "none",
                fontWeight: 600,
                borderRadius: 2,
                "&:hover": {
                  bgcolor: "#EEF2FF",
                },
              }}
            >
              Send password reset link
            </Button>
            <Button
              variant="outlined"
              fullWidth
              color="error"
              sx={{
                py: 1,
                bgcolor: "white",
                textTransform: "none",
                fontWeight: 600,
                borderRadius: 2,
                "&:hover": {
                  bgcolor: "FEF2F2",
                  border: "1px solid rgba(239,68,68,0.9)",
                },
              }}
            >
              Log out
            </Button>
          </div>
        </div>
      </PageContainer>
    </div>
  );
};

export default UserProfile;
