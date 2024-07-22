import { Avatar, Paper, Stack, Typography } from "@mui/material";

import { styled } from "@mui/material/styles";
import { UserAuth } from "../context/AuthContext";

export default function ChatCard({ to, from, subject }) {
  const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: "#ddd",
    ...theme.typography.body2,
    padding: theme.spacing(1),
    color: theme.palette.text.secondary,
  }));

  const { userId } = UserAuth();
  const user = to.uid !== userId ? to : from;

  return (
    <Item className="chat-card">
      <Stack spacing={2} direction="row" alignItems="center">
        <Avatar alt={from.displayName} src={from.profileUrl}>
          {user.firstName.slice(0, 1)}
          {user.lastName.slice(0, 1)}
        </Avatar>

        <Typography variant="div">{subject}</Typography>
      </Stack>
    </Item>
  );
}
