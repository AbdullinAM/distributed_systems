package com.kspt.pms.project;

import com.kspt.pms.user.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kivi on 03.12.17.
 */
@Entity
@Table(name = "PROJECT")
public class Project {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToOne
    private User manager;

    @ManyToOne
    private User teamLeader;

    @ManyToMany(mappedBy = "developedProjects", fetch = FetchType.LAZY)
    private Set<User> developers = new HashSet<>();

    @ManyToMany(mappedBy = "testedProjects", fetch = FetchType.LAZY)
    private Set<User> testers = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    private Set<BugReport> reports = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public User getTeamLeader() {
        return teamLeader;
    }

    public void setTeamLeader(User teamLeader) {
        this.teamLeader = teamLeader;
    }

    public Set<User> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<User> developers) {
        this.developers = developers;
    }

    public Set<User> getTesters() {
        return testers;
    }

    public void setTesters(Set<User> testers) {
        this.testers = testers;
    }

    public Set<BugReport> getReports() {
        return reports;
    }

    public void setReports(Set<BugReport> reports) {
        this.reports = reports;
    }
}
