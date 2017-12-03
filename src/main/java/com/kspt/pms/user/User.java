package com.kspt.pms.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kspt.pms.project.Message;
import com.kspt.pms.project.Project;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by kivi on 26.11.17.
 */
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    @Column(name = "LOGIN", unique = true, nullable = false)
    private String login;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PASSWORD")
    private String password;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    private List<Message> messages = new ArrayList<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "manager")
    private Set<Project> managedProjects = new HashSet<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "teamLeader")
    private Set<Project> leadedProjects = new HashSet<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "PROJECT_DEVELOPERS",
            joinColumns = { @JoinColumn(name = "user") },
            inverseJoinColumns = { @JoinColumn(name = "project") }
    )
    private Set<Project> developedProjects = new HashSet<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "PROJECT_TESTERS",
            joinColumns = { @JoinColumn(name = "user") },
            inverseJoinColumns = { @JoinColumn(name = "project") }
    )
    private Set<Project> testedProjects = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Set<Project> getManagedProjects() {
        return managedProjects;
    }

    public void setManagedProjects(Set<Project> managedProjects) {
        this.managedProjects = managedProjects;
    }

    public Set<Project> getLeadedProjects() {
        return leadedProjects;
    }

    public void setLeadedProjects(Set<Project> leadedProjects) {
        this.leadedProjects = leadedProjects;
    }
}
