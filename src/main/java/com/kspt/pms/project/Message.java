package com.kspt.pms.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kspt.pms.user.User;

import javax.persistence.*;

/**
 * Created by kivi on 26.11.17.
 */
@Entity
@Table(name = "MESSAGE")
public class Message {
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne
    private User owner;

    @Column(name = "CONTENT")
    private String content;

    public Message() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
