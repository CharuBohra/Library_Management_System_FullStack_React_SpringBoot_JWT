import { AutoAwesome } from "@mui/icons-material";
import { statsConfig } from "./StateConfig";
import { LinearProgress, Box, Tabs, Tab } from "@mui/material";
import { useState } from "react";
import CurrentLoans from "./CurrentLoans";
import Reservations from "./Reservations";
import ReadingHistory from "./ReadingHistory";
import PageContainer from "../components/PageContainer";
import DashboardStatCard from "./DashboardStatCard";
import { loans } from "../../data/loans";
import { mockReservations } from "../../data/reservations";
import { fines } from "../../data/fines";

const READING_GOAL = 30;
const Dashboard = () => {
  const [activeTab, setActiveTab] = useState(0);

  const handleTabChange = (event, newValue) => {
    setActiveTab(newValue);
  };

  const currentLoans = loans.filter((l) =>
    ["CHECKED_OUT", "OVERDUE"].includes(l.status),
  );
  const activeReservations = mockReservations.filter(
    (r) => r.status === "PENDING" || (r.status === "AVAILABLE" && !r.isExpired),
  );
  const returnedLoans = loans.filter((l) => ["RETURNED"].includes(l.status));
  const unpaidFines = fines.filter(
    (f) => f.status !== "WAIVED" && f.amountOutstanding > 0,
  );
  const outstandingFines = unpaidFines.reduce(
    (sum, f) => sum + f.amountOutstanding,
    0,
  );

  const booksRead = returnedLoans.length;

  const stateData = statsConfig({
    currentLoans: currentLoans.length,
    activeReservations: activeReservations.length,
    booksRead,
    outstandingFines,
    unpaidFinesCount: unpaidFines.length,
  });

  const percent = Math.min(100, Math.round((booksRead / READING_GOAL) * 100));
  return (
    <div className="min-h-screen bg-linear-to-br from-indigo-200 via-white to-purple-100 py-8">
      <PageContainer>
        <div className="mb-8 animate-fade-in-up">
          <h1 className="text-4xl font-bold text-indigo-400 mb-2">
            My{" "}
            <span
              className="bg-linear-to-br from-indigo-600 to-purple-600 
                    bg-clip-text text-transparent"
            >
              Dashboard
            </span>
          </h1>
          <p className="text-lg text-gray-700">
            Track your reading journey and manage your Library
          </p>
        </div>

        {/* {state card} */}

        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
          {stateData.map((item) => (
            <DashboardStatCard
              bgColor={item.bgColor}
              textColor={item.textColor}
              icon={item.icon}
              value={item.value}
              title={item.title}
              subtitle={item.subtitle}
              key={item.id}
            />
          ))}
        </div>

        {/* Progress card */}
        <div className="bg-white rounded-2xl shadow-2xl p-6 mb-8">
          <div className="flex items-center justify-between mb-4">
            <div>
              <h3 className="text-xl font-bold text-gray-900 mb-1">
                2026 Reading Goals
              </h3>
              <p className="text-gray-600">
                {booksRead} of {READING_GOAL} books read.
              </p>
            </div>
            <div className="p-3 bg-linear-to-br from-indigo-100 to-purple-100 rounded-full">
              <AutoAwesome
                sx={{
                  fontSize: 32,
                  color: "#4F46E5",
                }}
              />
            </div>
          </div>

          <LinearProgress
            variant="determinate"
            value={percent}
            sx={{
              height: 12,
              borderRadius: 6,
              backgroundColor: "#E0E7FF",
              "& .MuiLinearProgress-bar": {
                background:
                  "linear-gradient(90deg ,#4F46E5 0% , #9333EA 100% )",
                borderRadius: 6,
              },
            }}
          />

          <p className="text-sm text-gray-600 mt-2">{percent}%</p>
        </div>

        {/* Tab Section */}
        <div className="bg-white rounded-2xl shadow-2xl mb-8 overflow-hidden">
          <Box sx={{ borderBottom: 1, borderColor: "divider" }}>
            <Tabs
              value={activeTab}
              onChange={handleTabChange}
              aria-label="Basic Tabs Example"
              sx={{
                "& .MuiTab-root": {
                  fontSize: "1rem",
                  textTransform: "none",
                  fontWeight: 600,
                },
                "& .Mui-selected": {
                  color: "#4F46E5",
                },
                "& .MuiTabs-indicator": {
                  backgroundColor: "#4F46E5",
                },
              }}
            >
              <Tab label="Current Loans" />
              <Tab label="Reservations" />
              <Tab label="Reading History" />
            </Tabs>
          </Box>
        </div>

        {/* Current Loans Tab */}
        {activeTab === 0 && <CurrentLoans loans={currentLoans} />}

        {/* Reservation Tab */}
        {activeTab === 1 && <Reservations reservations={activeReservations} />}

        {/* Reading History Tab */}
        {activeTab === 2 && <ReadingHistory loans={returnedLoans} />}
      </PageContainer>
    </div>
  );
};

export default Dashboard;
