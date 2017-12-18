package com.kspt.pms.logic;

import com.kspt.pms.entity.Milestone;
import com.kspt.pms.entity.Project;
import com.kspt.pms.entity.Role;
import com.kspt.pms.entity.User;
import com.kspt.pms.exception.*;
import com.kspt.pms.repository.CommentRepository;
import com.kspt.pms.repository.MessageRepository;
import com.kspt.pms.repository.MilestoneRepository;
import com.kspt.pms.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

/**
 * Created by kivi on 14.12.17.
 */
public class Manager implements TicketManager {

    private User user;
    TicketRepository ticketRepository;
    CommentRepository commentRepository;
    MessageRepository messageRepository;

    public Manager(User user, TicketRepository ticketRepository,
                   CommentRepository commentRepository, MessageRepository messageRepository) {
        this.user = user;
        this.ticketRepository = ticketRepository;
        this.commentRepository = commentRepository;
        this.messageRepository = messageRepository;
    }

    public Milestone createMilestone(Project project, Date start, Date end) throws NoRightsException {
        Permissions permissions = Permissions.getPermissionsByRole(project.getRoleForUser(user));
        if (!permissions.isMilestoneManager())
            throw new NoRightsException(user, Permissions.getMilestoneManager(), project);

        Milestone milestone = new Milestone();
        milestone.setProject(project);
        milestone.setStartingDate(start);
        milestone.setEndingDate(end);
        return milestone;
    }

    public void activateMilestone(Milestone milestone) throws TwoActiveMilestonesException, WrongStatusException, NoRightsException {
        Project project = milestone.getProject();
        Permissions permissions = Permissions.getPermissionsByRole(project.getRoleForUser(user));
        if (!permissions.isMilestoneManager())
            throw new NoRightsException(user, Permissions.getMilestoneManager(), project);
        if (milestone.isActive())
            throw new WrongStatusException("Active", "Active");

        milestone.setActive();
    }

    public void closeMilestone(Milestone milestone) throws MilestoneTicketNotClosedException, WrongStatusException, NoRightsException {
        Project project = milestone.getProject();
        Permissions permissions = Permissions.getPermissionsByRole(project.getRoleForUser(user));
        if (!permissions.isMilestoneManager())
            throw new NoRightsException(user, Permissions.getMilestoneManager(), project);
        if (milestone.isClosed())
            throw new WrongStatusException("Closed", "Closed");

        milestone.setClosed();
    }

    public void setTeamLeader(Project project, User teamLeader) throws MultipleRoleException, NoRightsException {
        Permissions permissions = Permissions.getPermissionsByRole(project.getRoleForUser(user));
        if (!permissions.isUserManager())
            throw new NoRightsException(user, Permissions.getMilestoneManager(), project);

        Role role = project.getRoleForUser(teamLeader);
        User oldTL = project.getTeamLeader();
        if (role.equals(Role.NONE)) {
            project.setTeamLeader(teamLeader);
            if (oldTL != null) project.addDeveloper(oldTL);
        } else if (role.equals(Role.DEVELOPER)) {
            project.addDeveloper(project.getTeamLeader());
            project.setTeamLeader(teamLeader);
            project.removeDeveloper(teamLeader);
            if (oldTL != null) project.addDeveloper(oldTL);
        } else {
            throw new MultipleRoleException(teamLeader.getLogin(), role.toString(), project.getName());
        }
    }

    public void addDeveloper(Project project, User developer) throws MultipleRoleException, NoRightsException {
        Permissions permissions = Permissions.getPermissionsByRole(project.getRoleForUser(user));
        if (!permissions.isUserManager())
            throw new NoRightsException(user, Permissions.getUserManager(), project);

        Role role = project.getRoleForUser(developer);
        if (role.equals(Role.NONE)) {
            project.addDeveloper(developer);
        } else {
            throw new MultipleRoleException(developer.getLogin(), role.toString(), project.getName());
        }
    }

    public void addTester(Project project, User tester) throws MultipleRoleException, NoRightsException {
        Permissions permissions = Permissions.getPermissionsByRole(project.getRoleForUser(user));
        if (!permissions.isUserManager())
            throw new NoRightsException(user, Permissions.getUserManager(), project);

        Role role = project.getRoleForUser(tester);
        if (role.equals(Role.NONE)) {
            project.addTester(tester);
        } else {
            throw new MultipleRoleException(tester.getLogin(), role.toString(), project.getName());
        }
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public MessageRepository getMessageRepository() {
        return messageRepository;
    }

    @Override
    public TicketRepository getTicketRepository() {
        return ticketRepository;
    }

    @Override
    public CommentRepository getCommentRepository() {
        return commentRepository;
    }
}
