import {
  AssignmentReturn,
  CalendarToday,
  MenuBook,
  Numbers,
  Person,
} from "@mui/icons-material";
import {
  Box,
  Button,
  Card,
  CardContent,
  Divider,
  Typography,
} from "@mui/material";
import React from "react";
import { formatDate } from "../../utils/formatDate";

const LoanCard = ({ loan }) => {
  return (
    <Card>
      <CardContent sx={{ p: 3 }}>
        <Box
          sx={{
            display: "flex",
            flexDirection: { xs: "column", md: "row" },
            gap: 3,
          }}
        >
          {/* Book COver */}
          <Box
            sx={{
              width: 80,
              height: 120,
              borderRadius: 2,
              background: "linear-gradient(135deg , #667eea 0% , #764ba2 100%)",
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
              <Person sx={{ fontSize: 16, color: "text.secondary" }} />
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
                    {formatDate(loan.checkoutDate)}
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
                    {formatDate(loan.dueDate)}
                  </Typography>
                </Box>
              </Box>
              {loan.returnDate && (
                <Box>
                  <Typography
                    variant="caption"
                    sx={{ color: "text.secondary", display: "block", mb: 0.5 }}
                  >
                    Return Date
                  </Typography>
                  <Box sx={{ display: "flex", alignItems: "center", gap: 0.5 }}>
                    <AssignmentReturn sx={{ fontSize: 14, color: "#667eea" }} />
                    <Typography variant="body2" sx={{ fontWeight: 600 }}>
                      {formatDate(loan.returnDate)}
                    </Typography>
                  </Box>
                </Box>
              )}
            </Box>
          </Box>
        </Box>
        {/* Note Section */}
        {loan.notes && (
          <Box
            sx={{
              mt: 3,
              p: 2,
              borderRadius: 2,
              background: "rgba(59,130,246,0.05)",
              border: "1px solid rgba(59,130,246,0.2)",
            }}
          >
            <Typography
              variant="body2"
              sx={{ color: "text.secondary", fontStyle: "italic" }}
            >
              <strong>Note : </strong>
              {loan.notes}
            </Typography>
          </Box>
        )}
        <Divider sx={{ my: 2 }} />
        {/* Action Buttons */}
        <Box
          sx={{
            display: "flex",
            gap: 1.5,
            flexWrap: "wrap",
            justifyContent: "flex-end",
          }}
        >
          <Button
            variant="outlined"
            sx={{
              borderColor: "#667eea",
              color: "#667eea",
              textTransform: "none",
              fontWeight: 600,
              "&:hover": {
                borderColor: "#764ba2",
                bgcolor: "rgba(102,126,234,0.05)",
              },
            }}
          >
            View Book Details
          </Button>
          {["CHECKED_OUT", "OVERDUE"].includes(loan.status) &&
            !loan.returnDate && (
              <Button
                variant="outlined"
                sx={{
                  textTransform: "none",
                  fontWeight: 600,
                  borderColor: "#667eea",
                  color: "#667eea",
                  transition: "all 0.3s",
                  "&:hover": {
                    transform: "translateY(-2px)",
                    borderColor: "#764ba2",
                  },
                }}
              >
                Return Book
              </Button>
            )}
        </Box>
      </CardContent>
    </Card>
  );
};

export default LoanCard;
