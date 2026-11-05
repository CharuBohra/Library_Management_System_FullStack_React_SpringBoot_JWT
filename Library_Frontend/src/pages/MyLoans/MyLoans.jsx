import { Box, Card, Tab, Tabs } from "@mui/material";
import { tabs } from "./tabs";
import { useState } from "react";
import { loans } from "../../data/loans";
import LoanCard from "./LoanCard";
import PageContainer from "../components/PageContainer";
import { RequestQuote } from "@mui/icons-material";
import PageHeader from "../components/PageHeader";

const MyLoans = () => {
  const [activeTab, setActiveTab] = useState("");
  return (
    <div>
      <div className="min-h-screen bg-linear-to-br from-indigo-50 via-white to-purple-50 py-8">
        <PageContainer>
          {/* Header */}
          <PageHeader
            title="My Borrowed Books"
            subtitle="Manage your book loans , track due dates and renew books"
            icon={<RequestQuote sx={{ fontSize: 36, color: "#4F46E5" }} />}
          />
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
