import { Route, BrowserRouter, Routes } from "react-router-dom";
import "./App.css";
import Home from "./pages/home/Home";
import Search from "./pages/search/Search";
import Details from "./pages/details/Details";
import Profile from "./pages/profile/Profile";
import CreateAccount from "./pages/profile/create/CreateAccount";
import { AuthContextProvider } from "./context/AuthContext";

import ProtectedRoute from "./utils/ProtectedRoute";
import { StorageContextProvider } from "./context/StorageContext";
import Buddies from "./pages/buddies/Buddies";
import { PetsContextProvider } from "./context/PetsContext";
import { LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import NotFound from "./pages/notFound/NotFound";
import BuddyDetails from "./pages/buddies/BuddyDetails";

function App() {
  return (
    <>
      <AuthContextProvider>
        <StorageContextProvider>
          <PetsContextProvider>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
              <BrowserRouter>
                <Routes>
                  <Route path="/" element={<Home />} />
                  <Route path="/search" element={<Search />} />
                  <Route path="/details" element={<Details />} />
                  <Route
                    path="/profile"
                    element={
                      <ProtectedRoute>
                        <Profile />
                      </ProtectedRoute>
                    }
                  />
                  <Route
                    path="/buddies"
                    element={
                      <ProtectedRoute>
                        <Buddies />
                      </ProtectedRoute>
                    }
                  />
                  <Route
                    path="/buddies/:id"
                    element={
                      <ProtectedRoute>
                        <BuddyDetails />
                      </ProtectedRoute>
                    }
                  />
                  <Route path="/profile/create" element={<CreateAccount />} />
                  <Route path="*" element={<NotFound />} />
                </Routes>
              </BrowserRouter>
            </LocalizationProvider>
          </PetsContextProvider>
        </StorageContextProvider>
      </AuthContextProvider>
    </>
  );
}

export default App;
