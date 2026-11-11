import { Box, Drawer } from "@mui/material";
import SidebarDrawer from "./SidebarDrawer";
import { DRAWER_WIDTH } from "../../constants/layout";

const paperStyles = {
  boxSizing: "border-box",
  width: DRAWER_WIDTH,
  border: "none",
};
const UserSidebar = ({ mobileOpen, onClose }) => {
  return (
    <Box
      component={"nav"}
      aria-label="Main navigation"
      sx={{ width: { md: DRAWER_WIDTH }, flexShrink: { md: 0 } }}
    >
      {/* Mobile Drawer : Slides in over the page*/}
      <Drawer
        variant="temporary"
        open={mobileOpen}
        onClose={onClose}
        ModalProps={{ keepMounted: true }}
        sx={{
          display: {
            xs: "block",
            md: "none",
          },
          "& .MuiDrawer-paper": paperStyles,
        }}
      >
        <SidebarDrawer onNavigate={onClose} />
      </Drawer>
      {/* Desktop Drawer */}
      <Drawer
        variant="permanent"
        open
        sx={{
          display: { xs: "none", md: "block" },
          "& .MuiDrawer-paper": paperStyles,
        }}
      >
        <SidebarDrawer />
      </Drawer>
    </Box>
  );
};

export default UserSidebar;
