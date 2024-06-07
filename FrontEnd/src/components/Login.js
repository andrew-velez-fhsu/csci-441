import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  TextField,
  Typography,
} from "@mui/material";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import Popover from "@mui/material/Popover";
import { UserAuth } from "../context/AuthContext";

export default function Login() {
  const [anchorEl, setAnchorEl] = useState(null);
  const [errorEmail, setErrorEmail] = useState(false);
  const [errorPassword, setErrorPassword] = useState(false);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [isLoginFailed, setIsLoginFailed] = useState(false);
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const { signIn, getUserId } = UserAuth();

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleLoginWarningClose = () => {
    setIsLoginFailed(false);
  };

  const open = Boolean(anchorEl);
  const id = open ? "login-popover" : undefined;

  const handleLogin = async (e) => {
    e.preventDefault();

    //reset valication
    setErrorEmail(false);
    setErrorPassword(false);

    if (email === "") setErrorEmail(true);
    if (password === "") setErrorPassword(true);

    if (email && password) {
      try {
        await signIn(email, password);
        setAnchorEl(null);
      } catch (err) {
        console.error(err);
        setIsLoginFailed(true);
      }
    }
  };

  useEffect(() => {
    const userIsLoggedIn = getUserId();
    console.log("user logged in", userIsLoggedIn);
    setIsLoggedIn(getUserId());
  }, [getUserId]);

  const authenticationButton = () => {
    if (isLoggedIn) {
      return (
        <Typography variant="caption" sx={{ marginRight: "0.5rem" }}>
          Welcome back!
        </Typography>
      );
    } else {
      return (
        <>
          <Button
            aria-describedby={id}
            variant="contained"
            sx={{ marginRight: "0.5rem" }}
            onClick={handleClick}
          >
            Log in
          </Button>
          <Popover
            id={id}
            open={open}
            anchorEl={anchorEl}
            onClose={handleClose}
            anchorOrigin={{
              vertical: "bottom",
              horizontal: "right",
            }}
          >
            <div className="modal">
              <Typography>Login to ZoomBuddy.com</Typography>
              <form onSubmit={handleLogin} noValidate>
                <TextField
                  label="Email address"
                  variant="outlined"
                  required
                  onChange={(e) => setEmail(e.target.value)}
                  fullWidth
                  error={errorEmail}
                  sx={{ marginBottom: 2 }}
                />
                <TextField
                  label="Password"
                  variant="outlined"
                  type="password"
                  required
                  fullWidth
                  onChange={(e) => setPassword(e.target.value)}
                  error={errorPassword}
                  sx={{ marginBottom: 2 }}
                />

                <Button type="submit" variant="contained">
                  Login
                </Button>
                <Dialog
                  open={isLoginFailed}
                  onClose={handleLoginWarningClose}
                  aria-describedby="login-failed-text"
                >
                  <DialogContent>
                    <DialogContentText id="login-failed-text">
                      Unable to log in. Check your user name and passsword, and
                      try again
                    </DialogContentText>
                  </DialogContent>
                  <DialogActions>
                    <Button onClick={handleLoginWarningClose}>OK</Button>
                  </DialogActions>
                </Dialog>
              </form>
              <Typography>
                Don't have an account?{" "}
                <Link to="/profile/create">Create a new account</Link>
              </Typography>
            </div>
          </Popover>
        </>
      );
    }
  };

  return <div>{authenticationButton()}</div>;
}
