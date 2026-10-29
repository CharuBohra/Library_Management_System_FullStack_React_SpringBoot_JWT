import { AccessAlarm, Book, CalendarToday } from "@mui/icons-material";

const MyReservations = () => {
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
          <div className="bg-white rounded-xl p-6 shadow-lg hover:shadow-xl transition-shadow duration-300 ">
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
          <div className="bg-white rounded-xl p-6 shadow-lg hover:shadow-xl transition-shadow duration-300">
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
          <div className="bg-white rounded-xl p-6 shadow-lg hover:shadow-xl transition-shadow duration-300">
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
      </div>
    </div>
  );
};

export default MyReservations;
