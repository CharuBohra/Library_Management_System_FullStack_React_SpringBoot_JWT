import { useNavigate, useSearchParams } from "react-router";
import PageContainer from "../components/PageContainer";
import { Check, ErrorOutline, HourglassTop } from "@mui/icons-material";
import { Button } from "@mui/material";
import { payments } from "../../data/payments";
import { formatMoney } from "../../utils/formatMoney";

const RESULT_CONFIG = {
  SUCCESS: {
    label: "Successful",
    icon: <Check sx={{ fontSize: 32, color: "#10B981" }} />,
    bgColor: "bg-green-100",
    textColor: "text-green-600",
    heading: "Payment successful",
    message:
      "Thanks! Your payment has been confirmed and your account is up to date.",
    actionLabel: "View my fines",
    actionTo: "/my-fines",
  },
  FAILURE: {
    label: "Failed",
    icon: <ErrorOutline sx={{ fontSize: 36 }} />,
    bgColor: "bg-red-100",
    textColor: "text-red-600",
    heading: "Payment failed",
    message: "The payment wasn’t completed and you haven’t been charged.",
    actionLabel: "Try again",
    actionTo: "/my-fines",
  },
  PENDING: {
    label: "Pending",
    icon: <HourglassTop sx={{ fontSize: 36 }} />,
    bgColor: "bg-amber-100",
    textColor: "text-amber-600",
    heading: "Payment processing",
    message: "We’re waiting for confirmation from Razorpay.",
    actionLabel: "Refresh Status",
  },
  PROCESSING: {
    label: "Processing",
    icon: <HourglassTop sx={{ fontSize: 36 }} />,
    bgColor: "bg-amber-100",
    textColor: "text-amber-600",
    heading: "Payment processing",
    message: "We’re waiting for confirmation from Razorpay.",
    actionLabel: "Refresh Status",
  },
};

const PaymentResult = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const status = searchParams.get("status");
  const paymentId = searchParams.get("paymentId");

  const view = RESULT_CONFIG[status] ?? RESULT_CONFIG.FAILURE;
  const payment = payments.find((p) => p.id === Number(paymentId));
  const isMembership = payment?.paymentType === "MEMBERSHIP";
  const actionLabel =
    status === "SUCCESS" && isMembership
      ? "View subscription"
      : view.actionLabel;
  const actionTo =
    status === "SUCCESS" && isMembership ? "/subscriptions" : view.actionTo;

  return (
    <div className="min-h-screen bg-linear-to-br from-indigo-100 via-white to-purple-100 py-8">
      <PageContainer>
        <div className="min-h-[70vh] flex items-center justify-center py-12 px-6 ">
          <div className="w-full max-w-[460px] bg-white border border-gray-200 rounded-2xl shadow-lg py-10 px-8 flex flex-col items-center gap-6 text-center">
            <div
              className={`p-6 rounded-full ${view.bgColor} ${view.textColor}`}
            >
              {view.icon}
            </div>
            <h1 className="text-3xl font-extrabold text-gray-900">
              {view.heading}
            </h1>
            <span className="text-gray-500 text-lg">{view.message}</span>
            {payment?.failureReason && (
              <p className="text-sm text-red-600">{payment.failureReason}</p>
            )}
            <div className="grid grid-cols-2 gap-6 p-4 bg-gray-100 rounded-xl text-left w-full">
              <div className="flex flex-col gap-1 ">
                <span className="text-gray-500 text-xs font-semibold uppercase tracking-wide">
                  Payment id
                </span>
                <span className="text-gray-900 font-semibold text-base">
                  #{paymentId}
                </span>
              </div>
              <div className="flex flex-col gap-1">
                <span className="text-gray-500 text-xs font-semibold uppercase tracking-wide">
                  Status
                </span>
                <span className={`${view.textColor} font-semibold text-base`}>
                  {view.label}
                </span>
              </div>
              {payment && (
                <>
                  <div className="flex flex-col gap-1">
                    <span className="text-gray-500 text-xs font-semibold uppercase tracking-wide">
                      Amount
                    </span>
                    <span className="text-gray-900 font-semibold text-base">
                      {formatMoney(payment.amount)}
                    </span>
                  </div>
                  <div className="flex flex-col gap-1">
                    <span className="text-gray-500 text-xs font-semibold uppercase tracking-wide">
                      For
                    </span>
                    <span className="text-gray-900 font-semibold text-base">
                      {payment.description}
                    </span>
                  </div>
                </>
              )}
            </div>
            <div className="flex flex-wrap gap-3">
              <Button
                variant="contained"
                onClick={() => (actionTo ? navigate(actionTo) : navigate(0))}
                sx={{
                  bgcolor: "#4F46E5",
                  borderRadius: 2,
                  py: 1.5,
                  color: "white",
                  textTransform: "none",
                  fontWeight: 600,
                  "&:hover": {
                    bgcolor: "#4338CA",
                    //   bgcolor: "rgba(102,126,234,0.05)",
                  },
                }}
              >
                {actionLabel}
              </Button>
              <Button
                variant="outlined"
                onClick={() => navigate("/")}
                sx={{
                  bgcolor: "white",
                  borderRadius: 2,
                  py: 1.5,
                  color: "#4F46E5",
                  textTransform: "none",
                  fontWeight: 600,
                  "&:hover": {
                    color: "#4338CA",
                    bgcolor: "whitesmoke",
                    //   bgcolor: "rgba(102,126,234,0.05)",
                  },
                }}
              >
                Go to Dashboard
              </Button>
            </div>
          </div>
        </div>
      </PageContainer>
    </div>
  );
};

export default PaymentResult;
