import "./App.css";
import Dashboard from "./pages/Dashboard/Dashboard";
import { Route, Routes } from "react-router";
import UserLayout from "./pages/UserLayout/UserLayout";
import BookPage from "./pages/Book/BookPage";
import MyLoans from "./pages/MyLoans/MyLoans";
import MyReservations from "./pages/MyReservations/MyReservations";

function App() {
  return (
    <>
      <Routes>
        {/* User routes */}
        <Route element={<UserLayout />}>
          <Route path="/" element={<Dashboard />} />
          <Route path="/books" element={<BookPage />} />
          <Route path="/my-loans" element={<MyLoans />} />
          <Route path="/my-reservations" element={<MyReservations />} />
          <Route path="/my-fines" element={<Dashboard />} />
          <Route path="/subscriptions" element={<Dashboard />} />
          <Route path="/wishlist" element={<Dashboard />} />
          <Route path="/profile" element={<Dashboard />} />
          <Route path="/settings" element={<Dashboard />} />
        </Route>
      </Routes>
    </>
  );
}

export default App;
