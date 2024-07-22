import { createContext, useContext } from "react";
import { UserAuth } from "./AuthContext";

const ChatContext = createContext();

export const ChatContextProvider = ({ children }) => {
  const { bearerToken } = UserAuth();

  const getChats = async (setChats) => {
    const myMessages = await fetch(`${process.env.REACT_APP_API}/chats`, {
      method: "GET",
      headers: {
        "Content-type": "application/json",
        Authorization: `Bearer ${bearerToken}`,
      },
    }).then((data) => data.json());
    setChats(myMessages);
    return myMessages;
  };

  const getMessagesByChat = async (chatId) => {
    const myMessages = await fetch(
      `${process.env.REACT_APP_API}/chats/${chatId}/messages`,
      {
        method: "GET",
        headers: {
          "Content-type": "application/json",
          Authorization: `Bearer ${bearerToken}`,
        },
      }
    ).then((data) => data.json());
    return myMessages;
  };

  const createChat = async (message, petId) => {
    const createChatRequest = {
      message,
      petId,
    };
    const response = await fetch(`${process.env.REACT_APP_API}/chats`, {
      method: "POST",
      headers: {
        "Content-type": "application/json",
        Authorization: `Bearer ${bearerToken}`,
      },
      body: JSON.stringify(createChatRequest),
    });

    if (!response.ok) {
      // const body = await response.then((data) => data.json());
      console.error("Unable to send message");
    }
  };

  const sendMessage = async (chatId, messageText) => {
    const messageRequest = {
      messageText,
    };
    const response = await fetch(
      `${process.env.REACT_APP_API}/chats/${chatId}/messages`,
      {
        method: "POST",
        headers: {
          "Content-type": "application/json",
          Authorization: `Bearer ${bearerToken}`,
        },
        body: JSON.stringify(messageRequest),
      }
    );

    if (!response.ok) {
      // const body = await response.then((data) => data.json());
      console.error("Unable to send message");
    }
  };

  return (
    <ChatContext.Provider
      value={{
        getChats,
        createChat,
        getMessagesByChat,
        sendMessage,
      }}
    >
      {children}
    </ChatContext.Provider>
  );
};

export const Chats = () => {
  return useContext(ChatContext);
};
