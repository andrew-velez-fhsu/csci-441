import { Typography } from "@mui/material";
import Masterpage from "../../components/Masterpage";

import { useEffect, useState } from "react";
import Button from "@mui/material/Button";
import { UserAuth } from "../../context/AuthContext";
import { Pets } from "../../context/PetsContext";
import { Pets as PetsIcon } from "@mui/icons-material";
import EditBuddy from "../../components/EditBuddy";

export default function Buddies() {
  const { getPetsByUser, addNewPet } = Pets();

  const { userId } = UserAuth();
  const [buddies, setBuddies] = useState([]);

  const getPets = async () => {
    const myPets = await getPetsByUser(userId);
    setBuddies(myPets);
  };

  useEffect(() => {
    getPets();
  }, []);

  const handleAddBuddy = async () => {
    const newPet = await addNewPet(userId);
    setBuddies([newPet, ...buddies]);
  };

  return (
    <Masterpage title="My Buddies">
      <Typography variant="h5">
        Who's a good girl?! Who's a good boy?!
      </Typography>
      <Typography>
        Your buddies are looking for other buddies. Let's let everyone know how
        awesome they are!
      </Typography>
      {(!buddies || buddies.length === 0) && (
        <Typography>Looks like you haven't added any buddies yet</Typography>
      )}
      <Button
        fullWidth
        variant="contained"
        startIcon={<PetsIcon />}
        onClick={handleAddBuddy}
      >
        Add a buddy!
      </Button>
      {buddies &&
        buddies.map((buddy) => <EditBuddy key={buddy.id} buddy={buddy} />)}
    </Masterpage>
  );
}
