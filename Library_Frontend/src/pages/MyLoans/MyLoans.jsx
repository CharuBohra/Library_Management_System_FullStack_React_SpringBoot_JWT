import { Box, Card, Tab, Tabs } from "@mui/material";
import { tabs } from "./tabs";
import { useState } from "react";
import { loans } from "../../data/loans";
import LoanCard from "./LoanCard";
import PageContainer from "../components/PageContainer";
import { RequestQuote } from "@mui/icons-material";
import PageHeader from "../components/PageHeader";
import { useEffect } from "react";
import { getMyLoans } from "../../api/loanApi";
import { getErrorMessage } from "../../utils/getErrorMessage";

const MyLoans = () => {
  const [activeTab, setActiveTab] = useState("ALL");
  const [result, setResult] = useState({
    key: null,
    loans: [],
    total: 0,
    error: null,
  });
  const [reloadCount, setReloadCount] = useState(0);
  const requestKey = `${activeTab} | ${reloadCount}`;

  const currentTab = tabs.find((t) => t.value === activeTab);
  const status = currentTab?.statuses?.[0];

  useEffect(() => {
    let ignore = false;
    const load = async () => {
      try {
        const data = await getMyLoans({
          status,
          page: 0,
          size: 20,
        });
        if (!ignore) {
          setResult({
            key: requestKey,
            loans: data.content,
            total: data.totalElements,
            error: null,
          });
        }
      } catch (error) {
        if (!ignore) {
          setResult({
            key: requestKey,
            loans: [],
            total: 0,
            error: getErrorMessage(error),
          });
        }
      }
    };
    load();
    return () => {
      ignore = true;
    };
  }, [requestKey, status]);

  const loading = result.key !== requestKey;
  const visibleLoans = result.loans;

  const renderLoans = () => {
    // 1. Loading
    if (loading) {
      return (
        <div className="p-8 text-gray-600 text-center">Loading your loans…</div>
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
    if (visibleLoans.length === 0) {
      return (
        <p className="p-8 text-center text-gray-500">
          No loans in this category.
        </p>
      );
    }

    return (
      <div className=" grid grid-cols-1 xl:grid-cols-2 gap-8">
        {visibleLoans.map((loan) => (
          <LoanCard key={loan.id} loan={loan} />
        ))}
      </div>
    );
  };
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
                variant="scrollable"
                scrollButtons="auto"
                allowScrollButtonsMobile
                onChange={(e, newValue) => setActiveTab(newValue)}
                aria-label="loans-tab"
              >
                {tabs.map((tab) => (
                  <Tab
                    key={tab.label}
                    label={tab.label}
                    value={tab.value}
                    sx={{ fontWeight: 600, textTransform: "none" }}
                  />
                ))}
              </Tabs>
            </Box>
          </Card>
          {/* Loan List  */}
          {renderLoans()}
        </PageContainer>
      </div>
    </div>
  );
};

export default MyLoans;
