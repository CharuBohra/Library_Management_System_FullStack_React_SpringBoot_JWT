import React from "react";

const MyLoans = () => {
  return (
    <div>
      <div className="min-h-screen bg-linear-to-br from-indigo-50 via-white to-purple-50 py-8">
        <div className="px-4 sm:px-6 lg:px-8">
          {/* Header */}
          <div className="mb-8">
            <h1 className="text-4xl font-bold text-gray-900 mb-2 flex items-center space-x-3.5">
              <span>
                <img
                  src="https://cdn-icons-png.flaticon.com/128/5832/5832416.png"
                  alt="Book Icon"
                  className="w-8 h-8 gap-1"
                />
              </span>
              <span className="bg-linear-to-br from-indigo-600 to-purple-600 bg-clip-text text-transparent">
                My Borrowed Books
              </span>
            </h1>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MyLoans;
