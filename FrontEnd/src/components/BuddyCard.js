import {
  Card,
  CardActions,
  CardContent,
  CardMedia,
  Divider,
  Typography,
  Button,
} from "@mui/material";
import noPetPhoto from "../assets/noPetPhoto.jpg";
import { Link, useNavigate } from "react-router-dom";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import { useState } from "react";

export default function BuddyCard({ buddy, isLoggedIn }) {
  const [showNotLoggedIn, setShowNotLoggedIn] = useState(false);

  const miniDescription =
    buddy.description.length > 200
      ? buddy.description.substring(0, 200) + "..."
      : buddy.description;

  const navigate = useNavigate();
  const handleSeeDetails = () => {
    if (isLoggedIn) navigate(`/buddies/${buddy.id}`);
    else handleNotLoggedInModal();
  };

  const handleNotLoggedInModal = () => {
    setShowNotLoggedIn(true);
  };

  const handleCloseNotLoggedInModal = () => {
    setShowNotLoggedIn(false);
  };

  return (
    <>
      <Card elevation={5}>
        <CardMedia
          image={buddy.photoUrl ? buddy.photoUrl : noPetPhoto}
          sx={{ height: 180 }}
        />
        <CardContent>
          <Typography variant="h5" component="div">
            {buddy.name}
          </Typography>
          <Typography gutterBottom variant="caption">
            {buddy.breed}
          </Typography>
          <Divider />
          <Typography variant="body2">{miniDescription}</Typography>
        </CardContent>
        <CardActions>
          <Button size="small" onClick={handleSeeDetails}>
            Learn more
          </Button>
        </CardActions>
      </Card>
      <Dialog
        open={showNotLoggedIn}
        onClose={handleCloseNotLoggedInModal}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">{"Not logged in"}</DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            You are not logged in. Only registered users can view details. Click
            login in the menu bar, or{" "}
            <Link to="/profile/create">create an account</Link> to get started.
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseNotLoggedInModal} autoFocus>
            OK
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
}
