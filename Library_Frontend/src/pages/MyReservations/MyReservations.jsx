import { AccessAlarm, Book, CalendarToday } from "@mui/icons-material";
import { Box, Button } from "@mui/material";
import { useState } from "react";
import { tabs } from "./tabs";
import MyReservationCard from "./MyReservationCard";
import PageContainer from "../components/PageContainer";
import PageHeader from "../components/PageHeader";
import { useEffect } from "react";
import { getMyReservations } from "../../api/reservationApi";
import { getErrorMessage } from "../../utils/getErrorMessage";

const MyReservations = () => {
  const [activeTab, setActiveTab] = useState("ALL");
  const [reloadCount, setReloadCount] = useState(0);
  const [result, setResult] = useState({
    key: null,
    reservations: [],
    totalReservations: 0,
    error: null,
  });
  const requestKey = `${reloadCount}`;

  useEffect(() => {
    let ignore = false;
    const load = async () => {
      try {
        const data = await getMyReservations({ page: 0, size: 50 });

        if (!ignore) {
          setResult({
            key: requestKey,
            reservations: data.content,
            totalReservations: data.totalElements,
            error: null,
          });
        }
      } catch (error) {
        if (!ignore) {
          setResult({
            key: requestKey,
            reservations: [],
            totalReservations: 0,
            error: getErrorMessage(error),
          });
        }
      }
    };
    load();
    return () => {
      ignore = true;
    };
  }, [requestKey]);

  const loading = result.key !== requestKey;
  const currentTab = tabs.find((t) => t.value === activeTab);
  const visibleReservations = result.reservations.filter(
    (r) => !currentTab?.statuses || currentTab.statuses.includes(r.status),
  );
  const all = result.reservations;

  //   const stats = { total: 8, active: 2, available: 1 };
  const stats = {
    total: all.length,
    active: all.filter((r) => ["PENDING", "AVAILABLE"].includes(r.status))
      .length,
    ready: all.filter((r) => r.status === "AVAILABLE" && !r.isExpired).length,
  };

  const renderReservations = () => {
    // 1. Loading
    if (loading) {
      return (
        <div className="p-8 text-gray-600 text-center">
          Loading your reservations…
        </div>
      );
    }
    // 2. Error
    if (result.error) {
      return (
        <div
          role="alert"
          className="p-6 rounded-xl bg-red-50 text-red-700 text-center border border-red-700 flex flex-col items-center gap-3"
        >
          <p className="text-red-700 font-semibold">{result.error}</p>
          <button
            type="button"
            onClick={() => setReloadCount((prev) => prev + 1)}
            className="px-4 py-2 text-white rounded-lg font-semibold bg-indigo-600 hover:bg-indigo-700 "
          >
            Try again
          </button>
        </div>
      );
    }

    // 3.no data
    if (visibleReservations.length === 0) {
      return (
        <p className="p-8 text-center text-gray-500">
          No reservations in this category.
        </p>
      );
    }
    return (
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {visibleReservations.map((reservation) => (
          <MyReservationCard key={reservation.id} reservation={reservation} />
        ))}
      </div>
    );
  };
  return (
    <div className="min-h-screen bg-linear-to-br from-indigo-50 via-white to-purple-50 py-8">
      <PageContainer>
        {/* Header */}
        <PageHeader
          title="My Reservations"
          subtitle="Manage and track your book reservations"
          icon={<AccessAlarm sx={{ fontSize: 36, color: "#4F46E5" }} />}
        />

        {/* Stats */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-6">
          {/* Total Reservation */}
          <div className="bg-white rounded-xl p-6 shadow-lg hover:shadow-xl transition-shadow duration-300 bg-gradient-to-br from-indigo-100 to-purple-200">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm font-semibold text-gray-500 uppercase tracking-wide">
                  Total Reservations
                </p>
                <p className="text-4xl font-extrabold text-gray-900 mt-1">
                  {stats.total}
                </p>
              </div>
              <div className="p-4 bg-linear-to-br from-indigo-500 to-purple-600 rounded-xl shadow-lg">
                <Book className="w-8 h-8 text-white" />
              </div>
            </div>
          </div>
          {/* Active Reservations */}
          <div className="bg-white rounded-xl p-6 shadow-lg hover:shadow-xl transition-shadow duration-300 bg-gradient-to-br from-yellow-50 to-yellow-100">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm font-semibold text-gray-500 uppercase tracking-wide">
                  Active Reservations
                </p>
                <p className="text-4xl font-extrabold text-gray-900 mt-1">
                  {stats.active}
                </p>
              </div>
              <div className="p-4 bg-linear-to-br from-yellow-500 to-amber-600 rounded-xl shadow-lg">
                <AccessAlarm className="w-8 h-8 text-white" />
              </div>
            </div>
          </div>
          {/* Ready to Pickup */}
          <div className="bg-white rounded-xl p-6 shadow-lg hover:shadow-xl transition-shadow duration-300 bg-gradient-to-br from-green-100 to-emerald-200">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm font-semibold text-gray-500 uppercase tracking-wide">
                  Ready to Pickup
                </p>
                <p className="text-4xl font-extrabold text-gray-900 mt-1">
                  {stats.ready}
                </p>
              </div>
              <div className="p-4 bg-linear-to-br from-green-500 to-emerald-600 rounded-xl shadow-lg">
                <CalendarToday className="w-8 h-8 text-white" />
              </div>
            </div>
          </div>
        </div>

        {/* Tabs */}
        <div className="bg-white rounded-xl shadow-lg mb-6 overflow-hidden">
          <div className="flex border-b border-gray-200">
            {tabs.map((tab) => {
              const isActive = activeTab === tab.value;
              return (
                <Button
                  key={tab.value}
                  onClick={() => setActiveTab(tab.value)}
                  sx={{
                    flex: 1,
                    minWidth: 0,
                    px: { xs: 1, sm: 4 },
                    py: { xs: 1.5, sm: 2 },
                    fontWeight: 600,
                    fontSize: { xs: "0.875rem", sm: "1rem" },
                    gap: { xs: 0, sm: 2 },
                    textTransform: "none",
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "center",
                    transition: "all 0.3s",
                    borderRadius: 0, // tabs have square corners
                    borderBottom: "4px solid",
                    borderColor: isActive ? "#4F46E5" : "transparent",
                    color: isActive ? "#4F46E5" : "#4B5563", // indigo-600 / gray-600
                    bgcolor: isActive ? "#EEF2FF" : "transparent",
                    "&:hover": {
                      bgcolor: isActive ? "#EEF2FF" : "#F9FAFB", // keep active colour, gray-50 otherwise
                    },
                  }}
                >
                  <Box
                    component="span"
                    sx={{ display: { xs: "none", sm: "inline-flex" } }}
                  >
                    {tab.icon}
                  </Box>
                  {tab.label}
                </Button>
              );
            })}
          </div>
        </div>

        {/* content Reservation List */}
        {renderReservations()}
      </PageContainer>
    </div>
  );
};

export default MyReservations;
