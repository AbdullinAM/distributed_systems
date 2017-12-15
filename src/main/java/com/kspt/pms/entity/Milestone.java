package com.kspt.pms.entity;

import com.kspt.pms.exception.MilestoneTicketNotClosedException;
import com.kspt.pms.exception.TwoActiveMilestonesException;
import com.kspt.pms.exception.WrongStatusException;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kivi on 03.12.17.
 */
@Entity
@Table(name = "MILESTONE")
public class Milestone {

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public Date getActivatedDate() {
        return activatedDate;
    }

    public void setActivatedDate(Date activatedDate) {
        this.activatedDate = activatedDate;
    }

    public Date getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    public enum Status {
        OPENED,
        ACTIVE,
        CLOSED
    }

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    private Project project;

    @Column(name = "STATUS")
    private Status status;

    @Column(name = "STARTING_TIME", columnDefinition = "DATETIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startingDate;

    @Column(name = "ACTIVATED_TIME", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date activatedDate;

    @Column(name = "ENDING_TIME", columnDefinition = "DATETIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endingDate;

    @Column(name = "CLOSED_TIME", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closingDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id")
    private Set<Ticket> tickets = new HashSet<>();

    @Override
    public int hashCode() {return project.hashCode() + startingDate.hashCode() + endingDate.hashCode();}
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        Milestone other = (Milestone) obj;
        return project.equals(other.getProject()) &&
                startingDate.equals(other.getStartingDate()) &&
                endingDate.equals(other.getEndingDate());
    }

    public boolean isOpened() { return status.equals(Status.OPENED); }
    public boolean isActive() { return status.equals(Status.ACTIVE); }
    public boolean isClosed() { return status.equals(Status.CLOSED); }

    public void setActive() throws TwoActiveMilestonesException, WrongStatusException {
        if (isActive()) return;
        if (!isOpened()) throw new WrongStatusException(getStatus().name(), Status.ACTIVE.name());

        for (Milestone milestone : project.getMilestones()) {
            if (milestone.isActive()) throw new TwoActiveMilestonesException(milestone.getId(), this.getId());
        }

        status = Status.ACTIVE;
        activatedDate = new Date();
    }

    public void setClosed() throws MilestoneTicketNotClosedException, WrongStatusException {
        if (isClosed()) return;
        if (!isActive()) throw new WrongStatusException(getStatus().name(), Status.CLOSED.name());
        for (Ticket t : tickets)
            if (!t.isClosed()) throw new MilestoneTicketNotClosedException(t.getId());
        closingDate = new Date();
        status = Status.CLOSED;
    }
}