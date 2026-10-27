import { CalendarToday, MenuBook, Numbers, Person } from "@mui/icons-material";
import { Box, Card, CardContent, Divider, Typography } from "@mui/material";
import React from "react";

const LoanCard = ({ loan }) => {
  return (
    <Card>
      <CardContent sx={{ p: 3 }}>
        <Box
          sx={{
            display: "flex",
            flexDirection: { sm: "column", md: "row" },
            gap: 3,
          }}
        >
          {/* Book COver */}
          <Box
            sx={{
              width: 80,
              height: 120,
              borderRadius: 2,
              background: "linear-gradient(135deg , #6c7eea 0% , #764bc2 100%)",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
              flexShrink: 0,
              cursor: "pointer",
              transition: "transform 0.3s",
              "&:hover": {
                transform: "scale(1.05)",
              },
            }}
          >
            <MenuBook sx={{ fontSize: 40, color: "white", opacity: 0.9 }} />
          </Box>
          {/* Book Details */}
          <Box sx={{ flex: 1 }}>
            <Typography variant="h6">{loan.bookTitle}</Typography>
            <Box sx={{ display: "flex", alignItems: "center", gap: 1, mb: 1 }}>
              <Person sx={{ fontSize: 16 }} />
              <Typography variant="body2" sx={{ color: "text.secondary" }}>
                {loan.bookAuthor}
              </Typography>
            </Box>
            <Box sx={{ display: "flex", alignItems: "center", gap: 1, mb: 1 }}>
              <Numbers sx={{ fontSize: 16, color: "text.secondary" }} />
              <Typography variant="caption" sx={{ color: "text.secondary" }}>
                ISBN : {loan.bookIsbn}
              </Typography>
            </Box>
          </Box>
          <Divider
            orientation="vertical"
            flexItem
            sx={{ display: { xs: "none", md: "block" } }}
          />
          {/* Loan Details */}
          <Box sx={{ flex: 1 }}>
            <Box
              sx={{
                display: "grid",
                gridTemplateColumns: "repeat(2,1fr)",
                gap: 2,
                mb: 2,
              }}
            >
              <Box>
                <Typography
                  variant="caption"
                  sx={{ color: "text.secondary", display: "block", mb: 0.5 }}
                >
                  Checkout Date
                </Typography>
                <Box sx={{ display: "flex", alignItems: "center", gap: 0.5 }}>
                  <CalendarToday sx={{ fontSize: 14, color: "#667eea" }} />
                  <Typography variant="body2" sx={{ fontWeight: 600 }}>
                    {loan.checkoutDate}
                  </Typography>
                </Box>
              </Box>
              <Box>
                <Typography
                  variant="caption"
                  sx={{ color: "text.secondary", display: "block", mb: 0.5 }}
                >
                  Due Date
                </Typography>
                <Box sx={{ display: "flex", alignItems: "center", gap: 0.5 }}>
                  <CalendarToday sx={{ fontSize: 14, color: "#667eea" }} />
                  <Typography variant="body2" sx={{ fontWeight: 600 }}>
                    {loan.dueDate}
                  </Typography>
                </Box>
              </Box>
            </Box>
          </Box>
        </Box>
      </CardContent>
    </Card>
  );
};

export default LoanCard;
