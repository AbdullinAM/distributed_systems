package com.kspt.pms.project;

import com.kspt.pms.exception.NoRightsException;
import com.kspt.pms.user.Role;
import com.kspt.pms.user.User;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by kivi on 03.12.17.
 */
@Entity
@Table(name = "BUGREPORT")
public class BugReport {

    public enum Status {
        OPENED,
        ACCEPTED,
        FIXED,
        CLOSED
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private Project project;

    @ManyToOne
    private User creator;

    @ManyToOne
    private User developer;

    @Column(name = "STATUS")
    private Status status;

    @Column(name = "CREATION_TIME", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany
    @JoinTable(
            name = "BUGREPORT_COMMENTS",
            joinColumns = { @JoinColumn(name = "bugreport") },
            inverseJoinColumns = { @JoinColumn(name = "commentid") }
    )
    private Set<Comment> comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public User getDeveloper() {
        return developer;
    }

    public void setDeveloper(User developer) {
        this.developer = developer;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOpened()   { return status.equals(Status.OPENED); }
    public boolean isAccepted() { return status.equals(Status.ACCEPTED); }
    public boolean isFixed()    { return status.equals(Status.FIXED); }
    public boolean isClosed()   { return status.equals(Status.CLOSED); }
}
