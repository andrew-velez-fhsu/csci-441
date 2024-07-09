package edu.fhsu.summer.csci441.group1.ZoomBuddy.controller;

import edu.fhsu.summer.csci441.group1.ZoomBuddy.data.MessagesRepository;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.data.UsersRepository;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.Message;
import org.springframework.http.HttpStatus;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin
public class MessageController {

    private final MessagesRepository messagesRepository;
    private final UsersRepository usersRepository;

    // constructor here
    public MessageController(MessagesRepository messagesRepository, UsersRepository usersRepository) {
        this.messagesRepository = messagesRepository;
        this.usersRepository = usersRepository;
    }

    // find all messages ===============================================
    @GetMapping("/message")
    public Iterable<Message> findAllMessagesByUser() {
        return this.messagesRepository.findAll();
    }

    // Send email
    @PostMapping("/messages")
    public void sendEmail(@RequestBody Message message) {
        messagesRepository.save(message);
    }

    // get message by id ========================================================
    @GetMapping("/messages/thread/{id}")
    public Iterable<Message> getMessagesByThread(@PathVariable("id") int id, Authentication auth) {
        var message = this.messagesRepository.getMessagesByThreadId(id);
        if (message != null) {
            return message;
        } else
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Message not found");
    }

    // Delete
    @DeleteMapping("/messages/{id}")
    public void deleteMessage(@PathVariable("id") int id) {
        System.out.println("Deleted successfully");
        messagesRepository.deleteById(id);
    }

    // Count unread messages
    @SuppressWarnings("unused")
    @GetMapping("/messages/unread")
    public int getCountUnreadMessages(Authentication auth) {
        var user = usersRepository.findByFirebaseUid(auth.getName());
        var messages = messagesRepository.getAllUnreadMessages(user.getId());
        var count = 0;
        for (Message message : messages) {
            count++;
        }
        return count;
    }
}
