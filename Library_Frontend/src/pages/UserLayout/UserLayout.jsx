import { Box, Toolbar } from "@mui/material";
import UserSidebar from "./UserSidebar";
import { Outlet } from "react-router";
import Navbar from "./Navbar";
import { DRAWER_WIDTH } from "../../constants/layout";
import { useState } from "react";

const UserLayout = () => {
  const [mobileOpen, setMobileOpen] = useState(false);

  const handleDrawerToggle = () => setMobileOpen((prev) => !prev);
  const handleDrawerClose = () => setMobileOpen(false);
  return (
    <Box sx={{ display: "flex", minHeight: "100vh", bgcolor: "white" }}>
      {/* App bar */}
      <Navbar handleDrawerToggle={handleDrawerToggle} />

      {/* Profile Menu  */}

      {/* User sidebar */}
      <UserSidebar mobileOpen={mobileOpen} onClose={handleDrawerClose} />

      {/* Main content */}
      <Box
        component={"main"}
        sx={{
          flexGrow: 1,
          minWidth: 0,
          width: { md: `calc(100% - ${DRAWER_WIDTH}px)` },
          minHeight: "100vh",
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
