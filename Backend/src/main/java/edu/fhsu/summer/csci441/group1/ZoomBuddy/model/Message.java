package edu.fhsu.summer.csci441.group1.ZoomBuddy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter // Lombok Getter and Setter
@Setter
@NoArgsConstructor // Lombok constructors
@AllArgsConstructor

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    // TODO - convert to many to one chat
    private int chatId;
    private String sendBy;
    private String body;
    private String status;
    private LocalDateTime timestamp;

    // I included Lombok dependency which creates all Getters, Setters and necessary
    // Constructors

    // // constructor here
    // public Message() {
    //
    // }
    //
    // public int getId() {
    // return id;
    // }
    //
    // public void setId(int id) {
    // this.id = id;
    // }
    //
    // public String getBody() {
    // return body;
    // }
    //
    // public void setBody(String body) {
    // this.body = body;
    // }
    //
    // public String getStatus() {
    // return status;
    // }
    //
    // public void setStatus(String status) {
    // this.status = status;
    // }
    //
    // public Date getDate() {
    // return date;
    // }
    //
    // public void setDate(Date date) {
    // this.date = date;
    // }
}
