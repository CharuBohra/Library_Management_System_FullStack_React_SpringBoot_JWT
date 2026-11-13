import {
  AppBar,
  IconButton,
  Toolbar,
  Tooltip,
  Typography,
  Box,
  Avatar,
} from "@mui/material";
import { Menu as MenuIcon } from "@mui/icons-material";
import { navigationItems, secondaryItems } from "./NavigationItems";
import { isActive } from "./utils";
import { Contrast, Notifications, Search } from "@mui/icons-material";
import { useLocation } from "react-router";
import { DRAWER_WIDTH } from "../../constants/layout";
import { getInitials } from "../../utils/getInitials";
import { currentUser } from "../../data/user";

const Navbar = ({ handleDrawerToggle }) => {
  const location = useLocation();
  return (
    <AppBar
      position="fixed"
      sx={{
        width: { md: `calc(100% - ${DRAWER_WIDTH}px)` },
        ml: { md: `${DRAWER_WIDTH}px` },
        bgcolor: "white",
        color: "text.primary",
        boxShadow: "0 1px 3px rgba(0,0,0,0.08)",
      }}
    >
      <Toolbar>
        <IconButton
          color="inherit"
          edge="start"
          aria-label="Open menu"
          onClick={handleDrawerToggle}
          sx={{
            mr: 2,
            display: { md: "none" },
          }}
        >
          <MenuIcon />
        </IconButton>

        <Typography
          variant="h6"
          noWrap
          component="div"
          sx={{
            flexGrow: 1,
            fontWeight: 600,
          }}
        >
          {[...navigationItems, ...secondaryItems].find((item) =>
            isActive(item.path, location),
          )?.title || "Dashboard"}
        </Typography>

        <Tooltip title="Search">
          <Search sx={{ display: { xs: "none", sm: "inline-block" } }} />
        </Tooltip>

        <Notifications sx={{ ml: 2 }} />

        <Box sx={{ ml: 2, display: { xs: "none", sm: "inline-block" } }}>
          <Contrast />
        </Box>

        <Tooltip title="Account">
          <IconButton
            //  onClick={handleProfileMenuDropdown}
            sx={{ ml: 1 }}
          >
            <Avatar
              sx={{
                width: 36,
                height: 36,
                bgcolor: "#4F46E5",
                fontSize: "0.9rem",
                fontWeight: 600,
              }}
            >
              {getInitials(currentUser.fullName)}
            </Avatar>
          </IconButton>
        </Tooltip>
      </Toolbar>
    </AppBar>
  );
};

export default Navbar;
