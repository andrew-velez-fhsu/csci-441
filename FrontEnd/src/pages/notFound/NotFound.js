import { Container, Typography } from "@mui/material";
import Masterpage from "../../components/Masterpage";
import { Link } from "react-router-dom";

export default function NotFound() {
  return (
    <Masterpage>
      <Container>
        <Typography variant="h4">Page not found</Typography>
        <Typography variant="h6">
          The page you requested was not found
        </Typography>
        <Link to="/">
          <Typography>Return to homepage</Typography>
        </Link>
      </Container>
    </Masterpage>
  );
}
