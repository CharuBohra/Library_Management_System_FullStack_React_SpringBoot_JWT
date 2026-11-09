import { Check } from "@mui/icons-material";
import { formatMoney } from "../../utils/formatMoney";
import { Button } from "@mui/material";

const PlanCard = ({ plan, currentPlanId }) => {
  const isCurrent = plan.id === currentPlanId;
  return (
    <div
      className={`bg-white rounded-xl shadow-md hover:shadow-xl transition-all duration-300 hover:-translate-y-1
                    overflow-hidden border ${plan.isFeatured ? "border-indigo-500" : "border-gray-200"} flex flex-col h-full`}
    >
      <div className="flex flex-col flex-1 gap-2 p-5">
        <div className="h-6">
          {plan.badgeText && (
            <span className="self-start px-2.5 py-0.5 rounded-full text-xs font-semibold bg-indigo-600 text-white">
              {plan.badgeText}
            </span>
          )}
        </div>
        <h2 className="font-bold text-gray-900 text-xl">{plan.name}</h2>
        <p className="text-sm text-gray-500">{plan.description}</p>
        <div>
          <span className="text-4xl font-extrabold text-gray-900 ">
            {formatMoney(plan.price)}
          </span>
          <span className="text-base text-gray-700">
            {" "}
            / {plan.durationDays} days
          </span>
        </div>
        <div className="flex items-center gap-2 text-sm text-gray-700">
          <Check sx={{ fontSize: 18, color: "#047857" }} /> Borrow up to{" "}
          {plan.maxBooksAllowed} books at once.
        </div>
        <div className="flex items-center gap-2 text-sm text-gray-700">
          <Check sx={{ fontSize: 18, color: "#047857" }} /> Borrow up to Keep
          each book {plan.maxDaysPerBook} days.
        </div>
        <div className="flex items-center gap-2 text-sm text-gray-700">
          <Check sx={{ fontSize: 18, color: "#047857" }} /> Borrow up to Reserve
          books and join queues.
        </div>

        <div className="mt-auto p-3">
          <Button
            variant={plan.isCurrent ? "outlined" : "contained"}
            fullWidth
            disabled={isCurrent}
            sx={{
              textTransform: "none",
              height: "50px",
              fontSize: "16px",
            }}
          >
            {isCurrent ? "Current Plan" : `Choose ${plan.name}`}
          </Button>
        </div>
      </div>
    </div>
  );
};

export default PlanCard;
