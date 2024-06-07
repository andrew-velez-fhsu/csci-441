import { Navigate } from "react-router-dom";
import { UserAuth } from "../context/AuthContext";

const ProtectedRoute = ({ children }) => {
  const { isLoggedIn } = UserAuth();
  if (!isLoggedIn) {
    return <Navigate to="/" />;
  }
  return children;
};

export default ProtectedRoute;
