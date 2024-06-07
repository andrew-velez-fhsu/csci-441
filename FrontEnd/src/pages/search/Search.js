import {
  Box,
  Button,
  Grid,
  Paper,
  Stack,
  TextField,
  Typography,
} from "@mui/material";
import Masterpage from "../../components/Masterpage";
import { useEffect, useState } from "react";
import logo from "../../assets/logo.jpg";
import { Search as SearchIcon } from "@mui/icons-material";

import { Pets } from "../../context/PetsContext";
import BuddyCard from "../../components/BuddyCard";
import { useLocation } from "react-router-dom";
import { UserAuth } from "../../context/AuthContext";

export default function Search() {
  const [search, setSearch] = useState("");
  const [pets, setPets] = useState([]);
  const { getPets } = Pets();
  const location = useLocation();
  const { isLoggedIn } = UserAuth();

  const handleSearch = async (e) => {
    e.preventDefault();
    const searchParams = {
      search: search,
    };

    const foundPets = await getPets(searchParams);

    setPets(foundPets);
  };

  useEffect(() => {
    const queryParameters = new URLSearchParams(location.search);

    const searchParam = queryParameters.get("search");

    if (searchParam) {
      setSearch(searchParam);
      const searchParams = {
        search: searchParam,
      };
      getPets(searchParams).then((foundPets) => setPets(foundPets));
    }
  }, [location.search, getPets]);

  return (
    <Masterpage title="Search">
      <Grid container spacing={2} direction="row" alignItems="stretch">
        <Grid item xs={12} md={4}>
          <Paper sx={{ minHeight: "100%", padding: "20px" }}>
            <Stack direction="column">
              <img src={logo} alt="logo" />
              <form noValidate onSubmit={handleSearch}>
                <Box sx={{ display: "flex" }}>
                  <TextField
                    variant="outlined"
                    value={search}
                    sx={{ flexGrow: 1 }}
                    onChange={(e) => setSearch(e.target.value)}
                  />
                  <Button
                    autoFocus
                    variant="outlined"
                    aria-label="search"
                    color="secondary"
                    type="submit"
                  >
                    <SearchIcon />
                  </Button>
                </Box>
              </form>
            </Stack>
          </Paper>
        </Grid>
        <Grid item xs={12} md={8}>
          <Paper
            sx={{
              minHeight: "100%",
              padding: "20px",
              backgroundColor: "#f7f7f7",
            }}
          >
            <Grid container spacing={2}>
              {(!pets || pets.length === 0) && (
                <Typography variant="subtitle1">
                  No results. Update your search.
                </Typography>
              )}
              {pets.map((pet) => (
                <Grid key={pet.id} item xs={12} md={6}>
                  <BuddyCard buddy={pet} isLoggedIn={isLoggedIn} />
                </Grid>
              ))}
            </Grid>
          </Paper>
        </Grid>
      </Grid>
    </Masterpage>
  );
}
