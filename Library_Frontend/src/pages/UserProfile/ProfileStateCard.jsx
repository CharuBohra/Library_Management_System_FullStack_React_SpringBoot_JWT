import { ChevronRight } from "@mui/icons-material";
import { Link } from "react-router";

const ProfileStateCard = ({ profileTab }) => {
  return (
    <div
      className="bg-white  rounded-xl shadow-md hover:shadow-xl transition-all duration-300 hover:-translate-y-1
                    overflow-hidden flex flex-col flex-1 h-full p-5 gap-3"
    >
      <span className="text-gray-500 text-sm font-semibold uppercase tracking-wide">
        {profileTab.label}
      </span>
      <p className={`text-4xl font-extrabold ${profileTab.classes}`}>
        {profileTab.value}
      </p>
      <span className="text-xs">
        <Link
          to={profileTab.to}
          className={`inline-flex items-center gap-1 font-semibold ${profileTab.classes} hover:underline`}
        >
          {profileTab.linkText}
          <ChevronRight sx={{ fontSize: 16 }} />
        </Link>
      </span>
    </div>
  );
};

export default ProfileStateCard;
