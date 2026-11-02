import { Box, Card, Tab, Tabs } from "@mui/material";
import { tabs } from "./tabs";
import { useState } from "react";
import { loans } from "../../data/loans";
import LoanCard from "./LoanCard";
import PageContainer from "../components/PageContainer";

const MyLoans = () => {
  const [activeTab, setActiveTab] = useState("");
  return (
    <div>
      <div className="min-h-screen bg-linear-to-br from-indigo-50 via-white to-purple-50 py-8">
        <PageContainer>
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
                My Borrowed Books
              </span>
            </h1>
            <p className="text-lg text-gray-600">
              Manage your book loans , track due dates and renew books
            </p>
          </div>
          {/* Card Area */}
          <Card className="mb-6">
            <Box sx={{ borderBottom: 1, borderColor: "divider" }}>
              <Tabs
                value={activeTab}
                onChange={(e, newValue) => setActiveTab(newValue)}
                aria-label="loans-tab"
              >
                {tabs.map((tab) => (
                  <Tab
                    key={tab.label}
                    label={tab.label}
                    sx={{ fontWeight: 600, textTransform: "none" }}
                  />
                ))}
              </Tabs>
            </Box>
          </Card>
          {/* Loan List  */}
          <div className="space-y-4 grid grid-cols-1 xl:grid-cols-2 gap-8">
            {loans.map((loan) => (
              <LoanCard key={loan.id} loan={loan} />
            ))}
          </div>
        </PageContainer>
      </div>
    </div>
  );
};

export default MyLoans;
