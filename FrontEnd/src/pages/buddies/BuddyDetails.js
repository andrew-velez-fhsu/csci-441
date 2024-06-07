import { useParams } from "react-router-dom";
import Masterpage from "../../components/Masterpage";
import { Pets } from "../../context/PetsContext";
import { useEffect, useState } from "react";
import logo from "../../assets/logo.jpg";
import noPetPhoto from "../../assets/noPetPhoto.jpg";
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Grid,
  Paper,
  Stack,
  TextField,
  Typography,
} from "@mui/material";
import {
  Cake,
  ChildCare,
  Email,
  Pets as PetsIcon,
  Place,
} from "@mui/icons-material";
import { IconBone } from "@tabler/icons-react";

export default function BuddyDetails() {
  const { id } = useParams();
  const [buddy, setBuddy] = useState(null);
  const [showContactModal, setShowContactModal] = useState(false);
  const { getPet } = Pets();

  useEffect(() => {
    getPet(id).then((data) => setBuddy(data));
  }, [getPet, id]);

  const handleCloseContactModal = () => {
    setShowContactModal(false);
  };

  return (
    <Masterpage title={`About ${buddy?.name}`}>
      <Grid container spacing={5}>
        <Grid item xs={12} md={4}>
          <img src={logo} alt="Zoom Buddy logo" />
        </Grid>

        <Grid item xs={12} md={8}>
          <Paper
            elevation={5}
            sx={{
              padding: "5px",
              borderRadius: "15px",
              marginBottom: "1rem",
              backgroundColor: "goldenrod",
            }}
          >
            <img
              className="pet-profile"
              src={buddy?.photoUrl ? buddy.photoUrl : noPetPhoto}
              alt={buddy?.name}
            />
          </Paper>
          <Button
            variant="outlined"
            fullWidth
            startIcon={<Email />}
            onClick={() => setShowContactModal(true)}
          >
            Have your people call my people!
          </Button>
          <Dialog
            open={showContactModal}
            onClose={handleCloseContactModal}
            PaperProps={{
              component: "form",
              onSubmit: (e) => {
                e.preventDefault();
                handleCloseContactModal();
              },
            }}
          >
            <DialogTitle>Make a connection</DialogTitle>
            <DialogContent>
              <DialogContentText>
                Send a message to {buddy?.name}. Your contact information will
                be shared with their owner so you can have some great playtime!
              </DialogContentText>
              <TextField
                autoFocus
                required
                margin="dense"
                variant="outlined"
                multiline
                fullWidth
                minRows={2}
              />
            </DialogContent>
            <DialogActions>
              <Button onClick={handleCloseContactModal}>Cancel</Button>
              <Button type="submit" startIcon={<Email />}>
                Connect!
              </Button>
            </DialogActions>
          </Dialog>
          <Grid container spacing={2} sx={{ marginTop: "1rem" }}>
            <Grid item xs={12} md={6}>
              <Typography gutterBottom variant="h6">
                {buddy?.name}
              </Typography>
              <Typography variant="body2">{buddy?.description}</Typography>
            </Grid>
            <Grid item xs={12} md={6}>
              <Stack direction="column" gap={1}>
                <Stack alignItems="center" direction="row" gap={1}>
                  <PetsIcon color="primary" fontSize="small" />
                  <Typography>Breed: {buddy?.breed}</Typography>
                </Stack>
                <Stack alignItems="center" direction="row" gap={1}>
                  <Cake color="primary" fontSize="small" />
                  <Typography>
                    Birthday:{" "}
                    {buddy?.birthday
                      ? new Date(buddy.birthday).toLocaleDateString("en-us", {
                          year: "numeric",
                          month: "short",
                          day: "2-digit",
                        })
                      : "Not provided"}
                  </Typography>
                </Stack>
                <Stack alignItems="center" direction="row" gap={1}>
                  <ChildCare color="primary" fontSize="small" />
                  <Typography>
                    Good with children?{" "}
                    {buddy?.isGoodWithChildren !== undefined
                      ? buddy.isGoodWithChildren
                        ? "Yes"
                        : "No"
                      : "Not provided"}
                  </Typography>
                </Stack>
                <Stack alignItems="center" direction="row" gap={1}>
                  <IconBone className="details-icon" fontSize="small" />
                  <Typography>
                    Resource protective?{" "}
                    {buddy?.isResourceProtective !== undefined
                      ? buddy.isResourceProtective
                        ? "Yes"
                        : "No"
                      : "Not provided"}
                  </Typography>
                </Stack>
                <Stack alignItems="center" direction="row" gap={1}>
                  <Place className="details-icon" fontSize="small" />
                  <Typography>
                    Location:{" "}
                    {buddy?.location ? buddy.location : "Not provided"}
                  </Typography>
                </Stack>
              </Stack>
            </Grid>
          </Grid>
        </Grid>
      </Grid>
    </Masterpage>
  );
}
