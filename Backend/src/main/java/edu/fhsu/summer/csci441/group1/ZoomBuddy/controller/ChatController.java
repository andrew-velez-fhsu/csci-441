package edu.fhsu.summer.csci441.group1.ZoomBuddy.controller;

import edu.fhsu.summer.csci441.group1.ZoomBuddy.data.ChatRepository;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.data.MessagesRepository;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.data.UsersRepository;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.Chat;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.Message;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.Pet;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ChatController {

    @Autowired
    private ChatRepository chatRepository;
    private UsersRepository usersRepository;
    private MessagesRepository messagesRepository;

    public ChatController(UsersRepository usersRepository, ChatRepository chatRepository, MessagesRepository messagesRepository) {
        this.usersRepository = usersRepository;
        this.chatRepository = chatRepository;
        this.messagesRepository = messagesRepository;
    }

    @PostMapping("/api/chats/create")
    public Chat createChat(Authentication sender, User recipient, Pet subjectPet, Message message) {

        Chat isExist = chatRepository.findChatByUsersId(recipient, sender);

        if (isExist != null){
            return isExist;
        }
        Chat chat = new Chat(); // create new chat
        chat.getClass().cast(recipient);   // getClass is references the users
        chat.getClass().cast(sender);
        chat.setTimestamp(LocalDateTime.now());
        chat.setSubjectPet(subjectPet);


        // Save the chat to get the generated ID
//        chat = chatRepository.save(chat);

        // Create and save the initial message
        message.setSendBy(message.getSendBy());
        message.setChatId(message.getChatId());
        message.setBody(message.getBody());
        message.setStatus(message.getStatus());
        message.setTimestamp(LocalDateTime.now());

        messagesRepository.save(message);

        return chatRepository.save(chat);
    }


    // Get Messages By Chat  ==========================================
    @GetMapping("/messages")
    public List<Message> getMessagesByChats(Authentication auth, Chat chat) {
        // Retrieve the authenticated user
        String username = auth.getName();
        User user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the user is a participant in the chat
        if (!chat.getSenderUid().equals(user.getUid())) throw new RuntimeException("User is not a participant in the chat");

        // Retrieve and return the messages in the chat
        return (List<Message>) chatRepository.findChatById(chat.getId());
    }


    // get Chat for current user  ========================================================
    @GetMapping("/messages/{id}")
    public List<Chat> getChatsForCurrentUser(@PathVariable("id") int id, Authentication auth) {
        var chats = this.chatRepository.findChatById(id);
        if (chats != null) {
            return (List<Chat>) chats;
        } else
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Message not found");
    }

    // Reply to  chat   ========================================================================
    @PostMapping("/{chatId}/reply")
    public void replyToChat(Authentication sender, Chat chat, @PathVariable("chatId") Message message) {
        // Retrieve the authenticated user
        String username = sender.getName();
        User senders = usersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the user is a participant in the chat
        if (!chat.getRecipientUid().contains((CharSequence) sender)) {
            throw new RuntimeException("User is not a participant in the chat");
        }
        // Set the message properties
        message.setSendBy(message.getSendBy());
        message.setChatId(message.getChatId());
        message.setBody(message.getBody());
        message.setTimestamp(LocalDateTime.now());

        // Save the message
        messagesRepository.save(message);
    }


}
