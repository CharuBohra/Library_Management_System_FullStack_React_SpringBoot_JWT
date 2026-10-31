import {
  AccessAlarm,
  Book,
  CalendarToday,
  CheckCircle,
  Close,
  HourglassBottom,
  MenuBook,
  Notifications,
  Replay,
} from "@mui/icons-material";
import { getStatusColor } from "./getStatusColor";
import { Button, Divider } from "@mui/material";
import { useNavigate } from "react-router";

const MyReservationCard = ({ reservation }) => {
  const navigate = useNavigate();

  const statusColors = getStatusColor(reservation.status);
  const isFulfilled = reservation.status === "FULFILLED";
  const canReserveAgain = ["CANCELLED", "EXPIRED"].includes(reservation.status);

  const getStatusIcon = (status) => {
    const iconClass = "w-5 h-5";
    const icons = {
      PENDING: <HourglassBottom className={iconClass} />,
      AVAILABLE: <CalendarToday className={iconClass} />,
      FULFILLED: <CheckCircle className={iconClass} />,
      CANCELLED: <Close className={iconClass} />,
      EXPIRED: <AccessAlarm className={iconClass} />,
    };

    return icons[status] || <AccessAlarm className={iconClass} />;
  };

  const handleReserveAgain = () => {
    console.log("Reserve Again - BookId ", reservation.bookId);
  };
  return (
    <div
      className="bg-white rounded-xl shadow-md hover:shadow-xl transition-all duration-300 hover:-translate-y-1
                    overflow-hidden border border-gray-200"
    >
      {/* Status Banner */}
      <div
        className={`bg-gradient-to-br px-4 py-3 flex items-center justify-between ${statusColors.gradient}`}
      >
        <div className="flex items-center gap-2">
          <span className={`${statusColors.text}`}>
            {getStatusIcon(reservation.status)}
          </span>
          <span
            className={`font-bold text-sm uppercase tracking-wider ${statusColors.text}`}
          >
            {reservation.status}
          </span>
        </div>
      </div>
      {/* Book details */}
      <div className="p-4">
        {/* book header */}
        <div className="mb-4">
          <div className="flex items-center gap-3 mb-2">
            <div className="p-3 rounded-lg bg-gradient-to-br from-indigo-500 to-purple-600 shadow-lg">
              <Book className="w-6 h-6 text-white" />
            </div>
            <div className="min-w-0">
              <p>Book Id </p>
              <h3># {reservation.bookId}</h3>
            </div>
          </div>
          <p>{reservation.bookTitle}</p>
        </div>
        <Divider />
        {/* Timeline */}
        <div className="space-y-3 m-3">
          <div className="flex items-start gap-2">
            <AccessAlarm className="w-4 h-4 text-gray-400 mt-0.5" />
            <div>
              <p className="text-xs font-semibold text-gray-500 uppercase">
                Reserved
              </p>
              <p className="text-sm font-semibold text-gray-700">
                {reservation.reservedAt}
              </p>
            </div>
          </div>
          {reservation.availableAt && (
            <div className="flex items-start gap-2">
              <CalendarToday className="w-4 h-4 text-green-400 mt-0.5" />
              <div>
                <p className="text-xs font-semibold text-green-500 uppercase">
                  Available At
                </p>
                <p className="text-sm font-semibold text-green-700">
                  {reservation.availableAt}
                </p>
              </div>
            </div>
          )}
          {reservation.expiredAt && (
            <div className="flex items-start gap-2">
              <Notifications className="w-4 h-4 text-red-400 mt-0.5" />
              <div>
                <p className="text-xs font-semibold text-red-500 uppercase">
                  Expired
                </p>
                <p className="text-sm font-semibold text-red-700">
                  {reservation.expiredAt}
                </p>
              </div>
            </div>
          )}
          {reservation.fulfilledAt && (
            <div className="flex items-start gap-2">
              <CheckCircle className="w-4 h-4 text-blue-400 mt-0.5" />
              <div>
                <p className="text-xs font-semibold text-blue-500 uppercase">
                  Fulfilled
                </p>
                <p className="text-sm font-semibold text-blue-700">
                  {reservation.fulfilledAt}
                </p>
              </div>
            </div>
          )}
        </div>
        <Divider />
        {/* Action Buttons */}
        <div className="space-y-3 mt-3">
          {reservation.canBeCancelled && (
            <Button
              variant="outlined"
              fullWidth
              color="error"
              startIcon={<Close />}
              sx={{
                height: "50px",
                // borderColor: "#667eea",
                // color: "#667eea",
                textTransform: "none",
                fontWeight: 600,
                // "&:hover": {
                //   borderColor: "#764ba2",
                //   bgcolor: "rgba(102,126,234,0.05)",
                // },
              }}
            >
              Cancel Reservation
            </Button>
          )}
          {isFulfilled && (
            <Button
              variant="outlined"
              fullWidth
              startIcon={<MenuBook />}
              onClick={() => navigate("/my-loans")}
              sx={{
                height: "50px",
                borderColor: "#4F46E5",
                color: "#4F46E5",
                textTransform: "none",
                fontWeight: 600,
                "&:hover": {
                  borderColor: "#4338CA",
                  bgcolor: "rgba(102,126,234,0.05)",
                },
              }}
            >
              View Loan
            </Button>
          )}
          {canReserveAgain && (
            <Button
              variant="contained"
              fullWidth
              startIcon={<Replay />}
              onClick={() => handleReserveAgain()}
              sx={{
                height: "50px",
                bgcolor: "#4F46E5",
                textTransform: "none",
                fontWeight: 600,
                "&:hover": {
                  bgcolor: "#4338CA",
                  //   bgcolor: "rgba(102,126,234,0.05)",
                },
              }}
            >
              Reserve Again
            </Button>
          )}
        </div>
      </div>
    </div>
  );
};

export default MyReservationCard;
