import { useEffect, useState } from "react";
import Masterpage from "../../components/Masterpage";
import MessageCard from "../../components/MessageCard";
import {
  Box,
  Button,
  Grid,
  Paper,
  Stack,
  TextField,
  Typography,
} from "@mui/material";
import { Link } from "react-router-dom";
import { Chats as ChatContext } from "../../context/ChatContext";
import ChatCard from "../../components/ChatCard";
import { Send } from "@mui/icons-material";

export default function Chats() {
  const [chats, setChats] = useState([]);
  const [messages, setMessages] = useState([]);
  const { getChats, sendMessage, getMessagesByChat } = ChatContext();
  const [newMessage, setNewMessage] = useState("");
  const [selectedChat, setSelectedChat] = useState(null);

  useEffect(() => {
    getChats(setChats);
  }, [getChats, setChats]);

  useEffect(() => {
    async function loadMessages() {
      if (selectedChat) {
        const messages = await getMessagesByChat(selectedChat);
        setMessages(messages);
      }
    }
    loadMessages();
  }, [selectedChat, getMessagesByChat]);

  const handleSendMessage = async (e) => {
    e.preventDefault();
    if (newMessage && selectedChat) {
      await sendMessage(selectedChat, newMessage);
      const messages = await getMessagesByChat(selectedChat);
      setMessages(messages);
      setNewMessage("");
    }
  };

  return (
    <Masterpage title={"Chats"}>
      <Grid container spacing={2} direction="row" alignItems="stretch">
        <Grid item xs={12} md={4}>
          <Paper sx={{ minHeight: "100%", padding: "20px" }}>
            <Stack direction="column">
              <Box sx={{ display: "flex" }}>
                <Typography variant="subtitle1">Conversations</Typography>
              </Box>
              {(!chats || chats.length === 0) && (
                <Typography variant="subtitle1">
                  There are no chats to view.{" "}
                  <Link to="/search">Find a friendly dog</Link> and start a
                  chat!
                </Typography>
              )}
              {chats &&
                Array.isArray(chats) &&
                chats.map((chat) => (
                  <ChatCard
                    key={chat.id}
                    id={chat.id}
                    to={chat.recipient}
                    from={chat.sender}
                    subject={chat.subject}
                    setMessages={setMessages}
                    setSelectedChat={setSelectedChat}
                  />
                ))}
            </Stack>
          </Paper>
        </Grid>
        <Grid item xs={12} md={8}>
          <Paper
            sx={{
              minHeight: "100%",
              padding: "20px",
              backgroundColor: "#f7f7f7",
              overflowY: "auto",
              height: "1px",
            }}
          >
            <Grid container spacing={2}>
              <Grid item xs={12}>
                {chats &&
                  chats.length > 0 &&
                  (!messages || messages.length === 0) && (
                    <Typography variant="subtitle1">
                      Select a conversation to view
                    </Typography>
                  )}

                {messages &&
                  Array.isArray(messages) &&
                  messages.length > 0 &&
                  messages.map((message) => (
                    <MessageCard
                      key={message.id}
                      from={message.sentBy}
                      body={message.body}
                      state={message.state}
                      date={message.timestamp}
                    />
                  ))}
              </Grid>
              <Grid item xs={12}>
                <form noValidate onSubmit={handleSendMessage}>
                  <TextField
                    fullWidth
                    type="search"
                    label="Message"
                    helperText="Enter your reply above"
                    disabled={!selectedChat}
                    value={newMessage}
                    onChange={(e) => setNewMessage(e.target.value)}
                  />
                  <Button
                    variant="contained"
                    type="submit"
                    endIcon={<Send />}
                    disabled={!selectedChat}
                  >
                    Send
                  </Button>
                </form>
              </Grid>
            </Grid>
          </Paper>
        </Grid>
      </Grid>
    </Masterpage>
  );
}
