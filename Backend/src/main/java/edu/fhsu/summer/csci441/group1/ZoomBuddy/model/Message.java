package edu.fhsu.summer.csci441.group1.ZoomBuddy.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name="messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    private int threadId;
    private int senderUid;
    private int recipientUid;
    private String body;
    private String status;
    private Date date;

    // constructor here
    public Message(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public int getSenderUid() {
        return senderUid;
    }

    public void setSenderUid(int senderUid) {
        this.senderUid = senderUid;
    }

    public int getRecipientUid() {
        return recipientUid;
    }

    public void setRecipientUid(int recipientUid) {
        this.recipientUid = recipientUid;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}