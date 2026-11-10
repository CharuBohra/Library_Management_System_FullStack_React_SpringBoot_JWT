import "./App.css";
import Dashboard from "./pages/Dashboard/Dashboard";
import { Route, Routes } from "react-router";
import UserLayout from "./pages/UserLayout/UserLayout";
import BookPage from "./pages/Book/BookPage";
import MyLoans from "./pages/MyLoans/MyLoans";
import MyReservations from "./pages/MyReservations/MyReservations";
import BookDetails from "./pages/BookDetails/BookDetails";
import Wishlist from "./pages/Wishlist/Wishlist";
import MyFines from "./pages/MyFines/MyFines";
import Subscription from "./pages/Subscription/Subscription";
import UserProfile from "./pages/UserProfile/UserProfile";
import Login from "./pages/UserAuthentication/Login";
import ForgotPassword from "./pages/UserAuthentication/ForgotPassword";
import ResetPassword from "./pages/UserAuthentication/ResetPassword";
import PaymentResult from "./pages/Payments/PaymentResult";

function App() {
  return (
    <>
      <Routes>
        {/* User routes */}
        <Route element={<UserLayout />}>
          <Route path="/" element={<Dashboard />} />
          <Route path="/books" element={<BookPage />} />
          <Route path="/books/:id" element={<BookDetails />} />
          <Route path="/my-loans" element={<MyLoans />} />
          <Route path="/my-reservations" element={<MyReservations />} />
          <Route path="/my-fines" element={<MyFines />} />
          <Route path="/subscriptions" element={<Subscription />} />
          <Route path="/wishlist" element={<Wishlist />} />
          <Route path="/profile" element={<UserProfile />} />
          <Route path="/settings" element={<Dashboard />} />
          <Route path="/payment/result" element={<PaymentResult />} />
        </Route>
        <Route path="/login" element={<Login />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />
        <Route path="/reset-password" element={<ResetPassword />} />
      </Routes>
    </>
  );
}

export default App;
