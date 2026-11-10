import { MenuBook } from "@mui/icons-material";
import { Avatar, Box, Typography } from "@mui/material";

const AuthSidePanel = () => {
  return (
    <div className="min-w-0 basis-150 bg-linear-to-br from-slate-800 to-slate-900 flex flex-col flex-1 items-start justify-between p-12">
      <Box
        sx={{
          display: "flex",
          alignItems: "center",
          gap: 2,
        }}
      >
        <Avatar
          sx={{
            width: 64,
            height: 64,
            background: "linear-gradient(135deg , #667eea 0% , #764ba2 100%)",
            fontWeight: "bold",
            fontSize: "1.3rem",
            boxShadow: "0 4px 16px rgba(102,126,234,0.4)",
          }}
        >
          <MenuBook sx={{ fontSize: 36 }} />
        </Avatar>
        <Box>
          <Typography
            variant="h5"
            sx={{
              fontWeight: 900,
              letterSpacing: 0.5,
              background: "linear-gradient(135deg , #ffffff 0% , #e0e7ff 100%)",
              backgroundClip: "text",
              WebkitBackgroundClip: "text",
              WebkitTextFillColor: "transparent",
            }}
          >
            ZoshBook
          </Typography>
          <Typography
            variant="body1"
            sx={{
              opacity: 0.5,
              fontWeight: 700,
              letterSpacing: 1,
              textTransform: "uppercase",
              color: "white",
              mt: 1,
            }}
          >
            Library Hub
          </Typography>
        </Box>
      </Box>
      <Box>
        <Typography
          variant="h4"
          sx={{
            fontWeight: 900,
            letterSpacing: 0.5,
            backgroundClip: "text",
            WebkitBackgroundClip: "text",
            maxWidth: 560,
            color: "white",
            fontSize: 48,
          }}
        >
          Borrow, reserve and track every book in one place.
        </Typography>
        <Typography
          variant="body1"
          sx={{
            opacity: 0.8,
            fontWeight: 200,
            letterSpacing: 1,
            textTransform: "none",
            color: "whitesmoke",
            mt: 2,
            maxWidth: 600,
            fontSize: 18,
          }}
        >
          See due dates, renew loans, join waiting lists and pay fines without
          visiting the desk.
        </Typography>
      </Box>
      <div className="text-gray-300 text-xl ">
        <p>© {new Date().getFullYear()} ZoshBook. All rights reserved.</p>
      </div>
    </div>
  );
};

export default AuthSidePanel;
