import {
  Paper,
  Typography,
  Grid,
  Stack,
  Button,
  TextField,
  FormGroup,
  FormControlLabel,
  Checkbox,
  Snackbar,
} from "@mui/material";
import { IconDog } from "@tabler/icons-react";
import { styled } from "@mui/material/styles";
import { AddAPhoto, Delete } from "@mui/icons-material";
import { Storage } from "../context/StorageContext";
import { useEffect, useState } from "react";
import { DatePicker } from "@mui/x-date-pickers";
import { Pets } from "../context/PetsContext";
import { UserAuth } from "../context/AuthContext";
import dayjs from "dayjs";

export default function EditBuddy({ buddy }) {
  const [userProfile, setUserProfile] = useState(null);
  const [buddyProfile, setBuddyProfile] = useState(buddy);
  const [name, setName] = useState(buddyProfile.name ? buddyProfile.name : "");
  const [description, setDescription] = useState(
    buddyProfile.description ? buddyProfile.description : ""
  );
  const [breed, setBreed] = useState(
    buddyProfile.breed ? buddyProfile.breed : ""
  );
  const [birthday, setBirthday] = useState(
    buddyProfile.birthday ? buddyProfile.birthday : null
  );

  const [isGoodWithChildren, setIsGoodWithChildren] = useState(
    buddyProfile.isGoodWithChildren ? buddyProfile.isGoodWithChildren : false
  );
  const [isResourceProtective, setIsResourceProtective] = useState(
    buddyProfile.isResourceProtective
      ? buddyProfile.isResourceProtective
      : false
  );
  const [photoUrl, setPhotoUrl] = useState(
    buddyProfile.photoUrl ? buddyProfile.photoUrl : null
  );
  const [errName, setErrName] = useState(false);
  const [errDescription, setErrDescription] = useState(false);
  const [errBreed, setErrBreed] = useState(false);
  const [isSaved, setIsSaved] = useState(false);

  const { uploadFile } = Storage();
  const { updatePet } = Pets();
  const { getProfile } = UserAuth();

  useEffect(() => {
    getProfile().then((profile) => setUserProfile(profile));
  }, []);

  const VisuallyHiddenInput = styled("input")({
    clip: "rect(0 0 0 0)",
    clipPath: "inset(50%)",
    height: 1,
    overflow: "hidden",
    position: "absolute",
    bottom: 0,
    left: 0,
    whiteSpace: "nowrap",
    width: 1,
  });
  const getProfilePicture = () => {
    if (photoUrl) {
      return <img width="100%" src={photoUrl} alt="Profile" />;
    } else {
      return <IconDog color="gray" stroke={1} width="100%" height="100%" />;
    }
  };

  const handlePetPicUpload = async (event) => {
    const file = event.target.files[0];
    try {
      const profileUrl = await uploadFile(file, "pets");

      setPhotoUrl(profileUrl);
      //update pet
      await updatePet({ ...buddyProfile, photoUrl: profileUrl }, userProfile);
    } catch (err) {
      console.warn(err);
    }
  };

  const savePet = async () => {
    const updatedPet = {
      ...buddyProfile,
      name,
      description,
      breed,
      birthday,
      photoUrl,
      isGoodWithChildren,
      isResourceProtective,
    };
    await updatePet(updatedPet, userProfile);
    setBuddyProfile(updatedPet);
    setIsSaved(true);
  };

  const handleSavePet = async (e) => {
    e.preventDefault();
    await savePet();
  };

  return (
    <Paper key={buddy.id} sx={{ marginTop: "1rem", padding: "0.5rem" }}>
      <Typography variant="h5">
        {name ? name : "Tell us about your new friend!"}
      </Typography>

      <Grid container spacing={2} direction="row" alignItems="stretch">
        <Grid item xs={12} md={4}>
          <Stack alignItems="center" spacing={2}>
            {getProfilePicture()}
            <Button
              component="label"
              variant="contained"
              startIcon={<AddAPhoto />}
            >
              Select new profile picture
              <VisuallyHiddenInput
                type="file"
                accept=".jpg, .jpeg, .png, .gif"
                onChange={handlePetPicUpload}
              />
            </Button>
          </Stack>
        </Grid>
        <Grid item xs={12} md={8}>
          <Stack spacing={2}>
            <form noValidate>
              <TextField
                label="Name"
                variant="outlined"
                value={name}
                fullWidth
                required
                onChange={(e) => setName(e.target.value)}
                error={errName}
                sx={{ marginBottom: "1rem" }}
              />
              <TextField
                label="About Me"
                variant="outlined"
                value={description}
                fullWidth
                required
                multiline
                minRows={3}
                onChange={(e) => setDescription(e.target.value)}
                error={errDescription}
                sx={{ marginBottom: "1rem" }}
              />
              <TextField
                label="Breed"
                variant="outlined"
                value={breed}
                fullWidth
                required
                onChange={(e) => setBreed(e.target.value)}
                error={errName}
                sx={{ marginBottom: "1rem" }}
              />
              <DatePicker
                label="Birthday"
                value={birthday ? dayjs(birthday) : null}
                onChange={(newValue) => setBirthday(newValue)}
                sx={{ marginBottom: "1rem" }}
              />
              <FormGroup>
                <FormControlLabel
                  sx={{ marginBottom: "1rem" }}
                  control={
                    <Checkbox
                      checked={isGoodWithChildren}
                      onChange={(e) => setIsGoodWithChildren(e.target.checked)}
                    />
                  }
                  label="Good with children"
                />
                <FormControlLabel
                  sx={{ marginBottom: "1rem" }}
                  control={
                    <Checkbox
                      checked={isResourceProtective}
                      onChange={(e) =>
                        setIsResourceProtective(e.target.checked)
                      }
                    />
                  }
                  label="Resource protective"
                />
              </FormGroup>

              <Button type="submit" variant="contained" onClick={handleSavePet}>
                Save
              </Button>
              <Snackbar
                open={isSaved}
                autoHideDuration={5000}
                onClose={() => setIsSaved(false)}
                message={`${name} profile saved!`}
                anchorOrigin={{ vertical: "bottom", horizontal: "right" }}
              />
            </form>
            <Button variant="secondary" startIcon={<Delete />}>
              Delete
            </Button>
          </Stack>
        </Grid>
      </Grid>
    </Paper>
  );
}
