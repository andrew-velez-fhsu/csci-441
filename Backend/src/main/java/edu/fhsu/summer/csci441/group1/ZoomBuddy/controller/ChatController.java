package edu.fhsu.summer.csci441.group1.ZoomBuddy.controller;

import edu.fhsu.summer.csci441.group1.ZoomBuddy.data.ChatRepository;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.data.MessagesRepository;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.data.UsersRepository;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.Chat;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.Message;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.Pet;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
public class ChatController {

    @Autowired
    private ChatRepository chatRepository;
    private UsersRepository usersRepository;
    private MessagesRepository messagesRepository;

    public ChatController(UsersRepository usersRepository, ChatRepository chatRepository,
            MessagesRepository messagesRepository) {
        this.usersRepository = usersRepository;
        this.chatRepository = chatRepository;
        this.messagesRepository = messagesRepository;
    }

    @PostMapping("/chats")
    public Chat createChat(Authentication sender, User recipient, Pet subjectPet, Message message) {
        // TODO: the below isn't needed, delete it
        // Chat isExist = chatRepository.findChatByUsersId(recipient, sender);

        // if (isExist != null) {
        // return isExist;
        // }

        // TODO: check that recipient, pet and message are not null. If null, return
        // client request error

        // create the subject
        var subject = String.format("Chat about %s", subjectPet.getName());
        Chat chat = new Chat(); // create new chat

        chat.setTimestamp(LocalDateTime.now());
        // TODO: change to string, use subject above
        chat.setSubjectPet(subjectPet);

        // Save the chat to get the generated ID
        // chat = chatRepository.save(chat);

        // Create and save the initial message
        message.setSendBy(message.getSendBy());
        message.setChatId(message.getChatId());
        message.setBody(message.getBody());
        message.setStatus(message.getStatus());
        message.setTimestamp(LocalDateTime.now());

        messagesRepository.save(message);

        return chatRepository.save(chat);
    }

    // Get Messages By Chat ==========================================
    @GetMapping("/chats/{chatId}/messages")
    public List<Message> getMessagesByChats(Authentication auth, @PathVariable("chatId") long chatId) {
        // Retrieve the authenticated user
        String username = auth.getName();
        User user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the user is a participant in the chat
        // TODO - after convert chat.sender and chat.recipient to user\
        // NOTE - you do this multiple times, you should put this into a separate
        // private method
        if (!chat.getSenderUid().equals(user.getUid()))
            throw new RuntimeException("User is not a participant in the chat");

        // Retrieve and return the messages in the chat
        return (List<Message>) chatRepository.findChatById(chat.getId());
    }

    // get Chat for current user
    // ========================================================
    @GetMapping("/chats")
    public List<Chat> getChatsForCurrentUser(Authentication auth) {
        //TODO: get all chats where user is either sender or recipient
        var chats = this.chatRepository.NEED_NEW_METHOD
        if (chats != null) {
            return (List<Chat>) chats;
        } else
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Message not found");
    }

    // Reply to chat
    // ========================================================================
    @PostMapping("/chats/{chatId}/messages")
    public void replyToChat(Authentication sender, @PathVariable("chatId") Integer chatId,
            @RequestBody Message message) {
        // Retrieve the authenticated user
        String username = sender.getName();
        User senders = usersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // get the chat by id
        var chat = chatRepository.findById(chatId);

        // Check if the user is a participant in the chat
        // TODO: fix after chat.sender and chat.recepient is converted to user
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
