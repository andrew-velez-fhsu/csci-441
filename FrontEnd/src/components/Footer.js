import {
  faSquareFacebook,
  faSquareInstagram,
  faSquareTwitter,
} from "@fortawesome/free-brands-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Divider, Stack, Typography } from "@mui/material";

export default function Footer() {
  return (
    <>
      <footer className="footer">
        <Stack
          justifyContent="center"
          spacing={15}
          align="center"
          direction="row"
        >
          <Typography align="center" variant="subtitle2">
            About
          </Typography>
          <Typography align="center" variant="subtitle2">
            <a href="mailto:a_velez4@mail.fhsu.edu">Contact</a>
          </Typography>
        </Stack>

        <Divider variant="middle" />

        <div className="links">
          <Stack
            justifyContent="center"
            spacing={15}
            align="center"
            direction="row"
          >
            <FontAwesomeIcon size="2x" icon={faSquareTwitter} />

            <FontAwesomeIcon size="2x" icon={faSquareInstagram} />
            <FontAwesomeIcon size="2x" icon={faSquareFacebook} />
          </Stack>
        </div>
        <Stack justifyContent="center" align="center">
          <Typography variant="button" align="center">
            ZoomBuddy.com
          </Typography>
          <Typography variant="caption">&copy;2024</Typography>
          <Typography variant="caption">Privacy - Terms</Typography>
        </Stack>
      </footer>
    </>
  );
}
