import { Avatar, Badge, Paper, Stack, Typography } from "@mui/material";

import { styled } from "@mui/material/styles";
import { UserAuth } from "../context/AuthContext";

export default function MessageCard({ from, body, date, state }) {
  const { userId } = UserAuth();
  const messageClass = userId === from.uid ? "message-mine" : "message-theirs";

  const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === "dark" ? "#1A2027" : "#fff",
    ...theme.typography.body2,
    padding: theme.spacing(1),
    color: theme.palette.text.secondary,
  }));

  const messageTimestamp = Date.parse(date);
  const messageDate = new Date(messageTimestamp);

  return (
    <Item className={messageClass}>
      <Stack spacing={2} direction="row" alignItems="center">
        <Badge color="primary" variant="dot" invisible={state !== "unread"}>
          <Avatar alt={from.displayName} src={from.profileUrl}>
            {from.firstName.slice(0, 1)}
            {from.lastName.slice(0, 1)}
          </Avatar>
        </Badge>

        <Typography variant="div">{body}</Typography>
      </Stack>
      <Typography variant="caption">{messageDate.toDateString()}</Typography>
    </Item>
  );
}
