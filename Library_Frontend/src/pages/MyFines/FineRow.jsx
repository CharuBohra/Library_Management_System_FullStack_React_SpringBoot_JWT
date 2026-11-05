import { Button } from "@mui/material";
import { formatDate } from "../../utils/formatDate";
import { formatMoney } from "../../utils/formatMoney";
import { FINE_STATUSES, FINE_TYPES } from "./fineConfig";

const FineRow = ({ fine, onPay }) => {
  const type = FINE_TYPES[fine.type];
  const status = FINE_STATUSES[fine.status];
  const owes = fine.amountOutstanding > 0 && fine.status !== "WAIVED";
  return (
    <tr className="border-b border-gray-200 last:border-0 hover:bg-gray-50">
      <td className="px-4 py-3">
        <div className="font-semibold text-gray-900">{fine.bookTitle}</div>
        <div className="text-sm text-gray-500">{fine.reason}</div>
      </td>
      <td className="px-4 py-3">
        <span
          className={`inline-flex self-start gap-1.5 px-2 py-1 rounded-full text-xs ${type.classes} mt-2 min-w-0`}
        >
          {type.label}
        </span>
      </td>
      <td className="text-right px-4 py-3 text-gray-600">
        {formatMoney(fine.amount)}
      </td>
      <td className="text-right px-4 py-3 text-gray-600">
        {formatMoney(fine.amountPaid)}
      </td>
      <td
        className={`text-right px-4 py-3 ${owes ? "text-red-600" : "text-gray-600"}`}
      >
        {formatMoney(fine.amountOutstanding)}
      </td>
      <td className="px-4 py-3">
        <span
          className={`inline-flex self-start items-start gap-1.5 px-2 py-1 rounded-full text-xs ${status.classes} mt-2 min-w-0`}
        >
          {status.label}
        </span>
      </td>
      <td className="px-4 py-3 whitespace-nowrap text-gray-600">
        {formatDate(fine.createdAt)}
      </td>
      <td className="px-4 py-3 text-right">
        {owes ? (
          <Button
            variant="contained"
            fullWidth
            size="small"
            onClick={() => onPay(fine)}
            sx={{
              minWidth: 128,
              height: "40px",
              whiteSpace: "nowrap",
              bgcolor: "#4F46E5",
              textTransform: "none",
              fontWeight: 600,
              borderRadius: 2,
              "&:hover": {
                bgcolor: "#4338CA",
                //   bgcolor: "rgba(102,126,234,0.05)",
              },
            }}
          >
            Pay {formatMoney(fine.amountOutstanding)}
          </Button>
        ) : fine.status === "WAIVED" ? (
          <div className="flex flex-col flex-1 gap-0.5">
            <span className="text-gray-500 text-xs">Waived</span>
            <span className="text-gray-500 text-xs">{fine.waiverReason}</span>
          </div>
        ) : (
          <div className="flex flex-col flex-1 gap-0.5">
            <span className="text-gray-500 text-xs">
              Paid {formatDate(fine.paidAt)}
            </span>
            <span className="text-gray-500 text-xs">
              Txn {fine.transactionId}
            </span>
          </div>
        )}
      </td>
    </tr>
  );
};

export default FineRow;
