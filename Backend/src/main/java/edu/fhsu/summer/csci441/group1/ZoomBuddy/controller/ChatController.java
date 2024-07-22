package edu.fhsu.summer.csci441.group1.ZoomBuddy.controller;

import edu.fhsu.summer.csci441.group1.ZoomBuddy.data.ChatRepository;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.data.MessagesRepository;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.data.PetsRepository;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.data.UsersRepository;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.entities.CreateChatRequest;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.Chat;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.Message;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class ChatController {

    @Autowired
    private ChatRepository chatRepository;
    private UsersRepository usersRepository;
    private MessagesRepository messagesRepository;
    private PetsRepository petsRepository;

    public ChatController(UsersRepository usersRepository, ChatRepository chatRepository,
            MessagesRepository messagesRepository, PetsRepository petsRepository) {
        this.usersRepository = usersRepository;
        this.chatRepository = chatRepository;
        this.messagesRepository = messagesRepository;
        this.petsRepository = petsRepository;
    }

    @PostMapping("/chats")
    public Chat createChat(Authentication auth, @RequestBody CreateChatRequest createChatRequest) {
        // get the pet
        var subjectPet = petsRepository.findById(createChatRequest.getPetId()).orElseThrow();
        // get the sender and the recipient
        var sender = usersRepository.findById(auth.getName()).orElseThrow();
        var recipient = subjectPet.getOwner();
        // create the subject
        var subject = String.format("Chat about %s", subjectPet.getName());

        // create new chat
        Chat chat = new Chat();

        chat.setDate(LocalDateTime.now());
        chat.setSubject(subject);
        chat.setRecipient(recipient);
        chat.setSender(sender);
        // Save the chat to get the generated ID
        chat = chatRepository.save(chat);

        var message = new Message();

        // Create and save the initial message
        message.setSentBy(sender);
        message.setChat(chat);
        message.setBody(createChatRequest.getMessage());
        message.setStatus("unread");
        message.setTimestamp(LocalDateTime.now());

        messagesRepository.save(message);

        return chatRepository.save(chat);
    }

    // Get Messages By Chat ==========================================
    @GetMapping("/chats/{chatId}/messages")
    public List<Message> getMessagesByChats(Authentication auth, @PathVariable("chatId") Integer chatId) {
        // Retrieve the authenticated user
        User user = usersRepository.findById(auth.getName())
                .orElseThrow();

        var chat = chatRepository.findById(chatId).orElseThrow();
        // Check if the user is a participant in the chat
        // TODO - after convert chat.sender and chat.recipient to user\
        // NOTE - you do this multiple times, you should put this into a separate
        // private method
        if (!chat.getSender().getUid().equals(user.getUid()))
            throw new RuntimeException("User is not a participant in the chat");

        List<Message> messages = chat.getMessages();// chatRepository.findByUsersId(auth.getName());

        return messages;
    }

    // get Chat for current user
    // ========================================================
    @GetMapping("/chats")
    public List<Chat> getChatsForCurrentUser(Authentication auth) {
        // TODO: get all chats where user is either sender or recipient
        var chats = new ArrayList<Chat>();// replace stub with this.chatRepository.NEED_NEW_METHOD
        return chats;
    }

    // Reply to chat
    // ========================================================================
    @PostMapping("/chats/{chatId}/messages")
    public void replyToChat(Authentication auth, @PathVariable("chatId") Integer chatId,
            @RequestBody String messageText) {
        // Retrieve the authenticated user
        String uid = auth.getName();
        User sender = usersRepository.findById(uid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // get the chat by id
        var chat = chatRepository.findById(chatId).orElseThrow();

        // Check if the user is a participant in the chat
        // TODO: fix after chat.sender and chat.recepient is converted to user
        // if (!chat.getRecipientUid().contains((CharSequence) sender)) {
        // throw new RuntimeException("User is not a participant in the chat");
        // }
        // Set the message properties
        var message = new Message();
        message.setSentBy(sender);
        message.setChat(chat);
        message.setBody(messageText);
        message.setTimestamp(LocalDateTime.now());

        // Save the message
        messagesRepository.save(message);
    }

}