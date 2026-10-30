import { AccessAlarm, Book, CalendarToday } from "@mui/icons-material";
import { Button } from "@mui/material";
import { useState } from "react";
import { tabs } from "./tabs";

const MyReservations = () => {
  const [activeTab, setActiveTab] = useState("ALL");
  const state = { total: 8, active: 2, available: 1 };
  return (
    <div className="min-h-screen bg-linear-to-br from-indigo-50 via-white to-purple-50 py-8">
      <div className="px-4 sm:px-6 lg:px-8">
        {/* Header */}
        <div className="mb-8">
          <h1 className="text-4xl font-bold text-gray-900 mb-2 flex items-center space-x-4">
            <span>
              <img
                src="https://cdn-icons-png.flaticon.com/128/5832/5832416.png"
                alt="Book Icon"
                className="w-8 h-8 gap-2"
              />
            </span>
            <span className="bg-linear-to-br from-indigo-600 to-purple-600 bg-clip-text text-transparent mb-1">
              My Reservations
            </span>
          </h1>
          <p className="text-lg text-gray-600">
            Manage and track your book reservations
          </p>
        </div>

        {/* State */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-6">
          {/* Total Reservation */}
          <div className="bg-white rounded-xl p-6 shadow-lg hover:shadow-xl transition-shadow duration-300 bg-gradient-to-br from-indigo-100 to-purple-200">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm font-semibold text-gray-500 uppercase tracking-wide">
                  Total Reservations
                </p>
                <p className="text-4xl font-extrabold text-gray-900 mt-1">
                  {state.total}
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
                  {state.active}
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
                  {state.available}
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
                    px: 4,
                    py: 2,
                    fontWeight: 600,
                    fontSize: "1rem",
                    gap: 2,
                    textTransform: "uppercase",
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
                  {tab.icon}
                  {tab.label}
                </Button>
              );
            })}
          </div>
        </div>
      </div>
    </div>
  );
};

export default MyReservations;
