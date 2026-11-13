import { Link } from "react-router";
import PageContainer from "../components/PageContainer";
import { ArrowBack, ErrorOutline } from "@mui/icons-material";

const NotFound = () => {
  return (
    <div className="min-h-screen bg-linear-to-br from-indigo-50 via-white to-purple-50 py-8">
      <PageContainer>
        <div className="flex flex-col items-center justify-center gap-6">
          <div className="flex flex-wrap items-center gap-3">
            <ErrorOutline sx={{ fontSize: 24, color: "red" }} />
            <h1 className="text-4xl font-bold text-gray-900">Page Not Found</h1>
          </div>
          <div className="flex flex-wrap items-center gap-2 text-indigo-500">
            <Link to={"/"}>
              <ArrowBack sx={{ fontSize: 18 }} />
              Back to Home Page
            </Link>
          </div>
        </div>
      </PageContainer>
    </div>
  );
};

export default NotFound;
