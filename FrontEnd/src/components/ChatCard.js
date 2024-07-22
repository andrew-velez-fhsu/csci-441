import { Avatar, Paper, Stack, Typography } from "@mui/material";

import { styled } from "@mui/material/styles";
import { UserAuth } from "../context/AuthContext";

export default function ChatCard({
  id,
  to,
  from,
  subject,
  setMessages,
  setSelectedChat,
}) {
  const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: "#eee",
    ...theme.typography.body2,
    padding: theme.spacing(1),
    color: theme.palette.text.secondary,
  }));

  const { userId } = UserAuth();
  const user = to.uid !== userId ? to : from;

  const handleClick = () => {
    setSelectedChat(id);
  };

  return (
    <>
      <Item className="chat-card" onClick={handleClick}>
        <Stack spacing={2} direction="row" alignItems="center">
          <Avatar alt={from.displayName} src={from.profileUrl}>
            {user.firstName.slice(0, 1)}
            {user.lastName.slice(0, 1)}
          </Avatar>

          <Typography variant="div">{subject}</Typography>
        </Stack>
      </Item>
    </>
  );
}
