package com.example.huecoscolombia.Model.entity;

import com.example.huecoscolombia.Model.driver.Foreign;
import com.example.huecoscolombia.Model.driver.Id;
import com.example.huecoscolombia.Model.driver.Table;

import java.util.List;

@Table(name = "CHAT")
public class Chat {

    @Id(name = "id")
    private String id;
    private List<Message> messages;
    @Foreign(name = "publication", reference = "id")
    private Publication publication;

    public Chat() {
    }

    public Chat(String id, Publication publication) {
        this.id = id;
        this.publication = publication;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }
}
