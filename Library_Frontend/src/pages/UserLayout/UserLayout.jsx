import { Box, Toolbar } from "@mui/material";
import UserSidebar from "./UserSidebar";
import { Outlet } from "react-router";
import Navbar from "./Navbar";
import { DRAWER_WIDTH } from "../../constants/layout";

const drawerWidth = DRAWER_WIDTH;
const UserLayout = () => {
  return (
    <Box sx={{ display: "flex", minHeight: "100vh", bgcolor: "white" }}>
      {/* App bar */}
      <Navbar />

      {/* Profile Menu  */}

      {/* User sidebar */}
      <UserSidebar />

      {/* Main content */}
      <Box
        component={"main"}
        sx={{
          flexGrow: 1,
          width: { md: `calc(100% - ${drawerWidth}px)` },
          minHeight: "100vh",
          padding: 2,
        }}
      >
        <Toolbar />
        <Box>
          <Outlet />
        </Box>
      </Box>
    </Box>
  );
};

export default UserLayout;
