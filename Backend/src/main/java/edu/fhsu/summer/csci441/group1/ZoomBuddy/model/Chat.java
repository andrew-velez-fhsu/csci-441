package edu.fhsu.summer.csci441.group1.ZoomBuddy.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter // Lombok Getter and Setter
@Setter
@AllArgsConstructor   // Lombok constructors
@NoArgsConstructor

@Entity
@Data
@Table(name = "chats")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Pet subjectPet;
    private String senderUid;
    private String recipientUid;

    @OneToMany(mappedBy = "chat")
    private List<Message> messages = new ArrayList<>();

    private LocalDateTime timestamp;


}
