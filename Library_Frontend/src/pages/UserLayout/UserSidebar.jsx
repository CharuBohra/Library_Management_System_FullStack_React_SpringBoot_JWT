import { Box, Drawer } from "@mui/material";
import SidebarDrawer from "./SidebarDrawer";
import { DRAWER_WIDTH } from "../../constants/layout";

const drawerWidth = DRAWER_WIDTH;
const UserSidebar = () => {
  return (
    <Box
      component={"nav"}
      sx={{ width: { md: drawerWidth }, flexShrink: { md: 0 } }}
    >
      {/* Desktop Drawer */}
      <Drawer
        variant="permanent"
        sx={{
          display: { xs: "none", md: "block" },
          "& .MuiDrawer-paper": {
            boxSizing: "border-box",
            width: drawerWidth,
            border: "none",
          },
        }}
        open
      >
        <SidebarDrawer />
      </Drawer>
    </Box>
  );
};

export default UserSidebar;
