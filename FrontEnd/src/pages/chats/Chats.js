import { useEffect, useState } from "react";
import Masterpage from "../../components/Masterpage";
import { UserAuth } from "../../context/AuthContext";
import MessageCard from "../../components/MessageCard";
import { Box, Grid, Paper, Stack, Typography } from "@mui/material";
import { Link } from "react-router-dom";

export default function Chats() {
  const { getProfile } = UserAuth();
  const [chats, setChats] = useState([]);
  const [messages, setMessages] = useState([]);

  useEffect(() => {
    getProfile().then((profile) =>
      setMessages([
        {
          from: profile,
          state: "read",
          body: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Fames ac turpis egestas integer eget aliquet nibh.",
        },
        {
          from: { ...profile, uid: "01J36FE8NNQ1Q068XWX6EDFFSH" },
          state: "read",
          body: "Porttitor lacus luctus accumsan tortor posuere ac ut. At elementum eu facilisis sed odio morbi quis commodo odio.",
        },
        {
          from: profile,
          state: "read",
          body: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Fames ac turpis egestas integer eget aliquet nibh.",
        },
        {
          from: { ...profile, uid: "01J36FE8NNQ1Q068XWX6EDFFSH" },
          state: "unread",
          body: "Porttitor lacus luctus accumsan tortor posuere ac ut. At elementum eu facilisis sed odio morbi quis commodo odio.",
        },
        {
          from: profile,
          state: "read",
          body: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Fames ac turpis egestas integer eget aliquet nibh.",
        },
        {
          from: { ...profile, uid: "01J36FE8NNQ1Q068XWX6EDFFSH" },
          state: "unread",
          body: "Porttitor lacus luctus accumsan tortor posuere ac ut. At elementum eu facilisis sed odio morbi quis commodo odio.",
        },
        {
          from: profile,
          state: "unread",
          body: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Fames ac turpis egestas integer eget aliquet nibh.",
        },
        {
          from: { ...profile, uid: "01J36FE8NNQ1Q068XWX6EDFFSH" },
          state: "read",
          body: "Porttitor lacus luctus accumsan tortor posuere ac ut. At elementum eu facilisis sed odio morbi quis commodo odio.",
        },
        {
          from: profile,
          state: "unread",
          body: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Fames ac turpis egestas integer eget aliquet nibh.",
        },
        {
          from: { ...profile, uid: "01J36FE8NNQ1Q068XWX6EDFFSH" },
          state: "unread",
          body: "Porttitor lacus luctus accumsan tortor posuere ac ut. At elementum eu facilisis sed odio morbi quis commodo odio.",
        },
        {
          from: profile,
          state: "unread",
          body: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Fames ac turpis egestas integer eget aliquet nibh.",
        },
        {
          from: { ...profile, uid: "01J36FE8NNQ1Q068XWX6EDFFSH" },
          state: "unread",
          body: "Porttitor lacus luctus accumsan tortor posuere ac ut. At elementum eu facilisis sed odio morbi quis commodo odio.",
        },
        {
          from: profile,
          state: "unread",
          body: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Fames ac turpis egestas integer eget aliquet nibh.",
        },
        {
          from: { ...profile, uid: "01J36FE8NNQ1Q068XWX6EDFFSH" },
          state: "unread",
          body: "Porttitor lacus luctus accumsan tortor posuere ac ut. At elementum eu facilisis sed odio morbi quis commodo odio.",
        },
      ])
    );
  }, [getProfile]);
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
              {chats &&
                chats.length > 0 &&
                (!messages || messages.length === 0) && (
                  <Typography variant="subtitle1">
                    Select a conversation to view
                  </Typography>
                )}

              {messages.map((chat) => (
                <MessageCard
                  from={chat.from}
                  to={chat.to}
                  body={chat.body}
                  state={chat.state}
                />
              ))}
            </Grid>
          </Paper>
        </Grid>
      </Grid>
    </Masterpage>
  );
}
