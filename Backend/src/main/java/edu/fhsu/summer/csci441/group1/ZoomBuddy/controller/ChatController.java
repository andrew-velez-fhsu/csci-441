package edu.fhsu.summer.csci441.group1.ZoomBuddy.controller;

import edu.fhsu.summer.csci441.group1.ZoomBuddy.data.ChatRepository;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.data.MessagesRepository;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.data.PetsRepository;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.data.UsersRepository;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.entities.CreateChatRequest;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.entities.ReplyToChatRequest;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.Chat;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.Message;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
        if (!chat.getSender().getUid().equals(user.getUid()) && !chat.getRecipient().getUid().equals((user.getUid())))
            throw new RuntimeException("User is not a participant in the chat");

        List<Message> messages = chat.getMessages();// chatRepository.findByUsersId(auth.getName());

        return messages;
    }

    // get Chat for current user
    // ========================================================
    @GetMapping("/chats")
    public List<Chat> getChatsForCurrentUser(Authentication auth) {
        // Get user
        var user = usersRepository.findById(auth.getName()).orElseThrow();
        // Get chats for the user
        var chats = this.chatRepository.findByUser(user);
        return chats;
    }

    // Reply to chat
    // ========================================================================
    @PostMapping("/chats/{chatId}/messages")
    public void replyToChat(Authentication auth, @PathVariable("chatId") Integer chatId,
            @RequestBody ReplyToChatRequest replyToChatRequest) {
        // Retrieve the authenticated user
        String uid = auth.getName();
        User sender = usersRepository.findById(uid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // get the chat by id
        var chat = chatRepository.findById(chatId).orElseThrow();

        // Check if the user is a participant in the chat
        if (!chat.getSender().getUid().equals(sender.getUid())
                && !chat.getRecipient().getUid().equals((sender.getUid())))
            throw new RuntimeException("User is not a participant in the chat");

        // Set the message properties
        var message = new Message();
        message.setSentBy(sender);
        message.setChat(chat);
        message.setBody(replyToChatRequest.getMessageText());
        message.setTimestamp(LocalDateTime.now());

        // Save the message
        messagesRepository.save(message);
    }

}
