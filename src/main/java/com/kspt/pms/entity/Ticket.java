package com.kspt.pms.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by kivi on 03.12.17.
 */
@Entity
@Table(name = "TICKET")
public class Ticket {
    public enum Status {
        NEW,
        ACCEPTED,
        IN_PROGRESS,
        FINISHED,
        CLOSED
    }

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    private Milestone milestone;

    @ManyToOne
    private User creator;

    @Column(name = "STATUS")
    private Status status;

    @ManyToMany
    @JoinTable(
            name = "TICKET_ASSIGNEES",
            joinColumns = { @JoinColumn(name = "ticket") },
            inverseJoinColumns = { @JoinColumn(name = "user") }
    )
    private Set<User> assignees;

    @Column(name = "CREATION_TIME", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;

    @Column(name = "TASK")
    private String task;

    @OneToMany
    @JoinTable(
            name = "TICKET_COMMENTS",
            joinColumns = { @JoinColumn(name = "ticket") },
            inverseJoinColumns = { @JoinColumn(name = "commentid") }
    )
    private Set<Comment> comments;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<User> getAssignees() {
        return assignees;
    }

    public void setAssignees(Set<User> assignees) {
        this.assignees = assignees;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public boolean isNew()          { return status.equals(Status.NEW); }
    public boolean isAccepted()     { return status.equals(Status.ACCEPTED); }
    public boolean isInProgress()   { return status.equals(Status.IN_PROGRESS); }
    public boolean isFinished()     { return status.equals(Status.FINISHED); }
    public boolean isClosed()       { return status.equals(Status.CLOSED); }
}
