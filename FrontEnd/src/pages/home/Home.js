import { Box, Button, Grid, TextField, Typography } from "@mui/material";

import logo from "../../assets/logo.jpg";
import Masterpage from "../../components/Masterpage";
import { Search } from "@mui/icons-material";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function Home() {
  const [search, setSearch] = useState("");
  const navigate = useNavigate();
  const handleSearch = (e) => {
    e.preventDefault();
    navigate(`/search?search=${encodeURI(search)}`);
  };

  return (
    <>
      <Masterpage title="Home">
        <Grid container>
          <Grid item xs={12} md={4}>
            <img src={logo} alt="logo" />
          </Grid>
          <Grid item xs={12} md={8} alignContent="center">
            <form noValidate onSubmit={handleSearch}>
              <Box sx={{ display: "flex" }}>
                <TextField
                  placeholder="i.e. Ossining, poodle, or Fido"
                  color="secondary"
                  onChange={(e) => {
                    setSearch(e.target.value);
                  }}
                  sx={{ flexGrow: 1 }}
                ></TextField>
                <Button
                  autoFocus
                  variant="contained"
                  color="secondary"
                  endIcon={<Search />}
                  type="submit"
                >
                  Search
                </Button>
              </Box>
            </form>
          </Grid>
        </Grid>

        <Typography>Your Best Friend Wants a Buddy</Typography>
        <Typography>
          We know you do your best. You throw the ball. You go on walks. But no
          one plays with a dog like another dog. And ZoomBuddy will help you
          find a best buddy for your buddy. Because dogs that play together are
          happier, healthier, and love you more.
        </Typography>
      </Masterpage>
    </>
  );
}
