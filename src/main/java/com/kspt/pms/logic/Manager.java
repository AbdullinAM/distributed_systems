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

import java.util.Date;
import java.util.Set;

/**
 * Created by kivi on 14.12.17.
 */
public class Manager implements TicketManager {

    private User user;
    @Autowired
    MilestoneRepository milestoneRepository;
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    MessageRepository messageRepository;

    public Manager(User user) {
        this.user = user;
    }

    public Milestone createMilestone(Project project, Date start, Date end) throws NoRightsException {
        Permissions permissions = Permissions.getPermissionsByRole(project.getRoleForUser(user));
        if (!permissions.isMilestoneManager())
            throw new NoRightsException(user, Permissions.getMilestoneManager(), project);

        Milestone milestone = new Milestone();
        milestone.setProject(project);
        milestone.setStartingDate(start);
        milestone.setEndingDate(end);
        milestoneRepository.save(milestone);
        return milestone;
    }

    public void activateMilestone(Milestone milestone) throws TwoActiveMilestonesException, WrongStatusException, NoRightsException {
        Project project = milestone.getProject();
        Permissions permissions = Permissions.getPermissionsByRole(project.getRoleForUser(user));
        if (!permissions.isMilestoneManager())
            throw new NoRightsException(user, Permissions.getMilestoneManager(), project);

        milestone.setActive();
    }

    public void closeMilestone(Milestone milestone) throws MilestoneTicketNotClosedException, WrongStatusException, NoRightsException {
        Project project = milestone.getProject();
        Permissions permissions = Permissions.getPermissionsByRole(project.getRoleForUser(user));
        if (!permissions.isMilestoneManager())
            throw new NoRightsException(user, Permissions.getMilestoneManager(), project);

        milestone.setClosed();
    }

    public void setTeamLeader(Project project, User user) throws MultipleRoleException, NoRightsException {
        Permissions permissions = Permissions.getPermissionsByRole(project.getRoleForUser(user));
        if (!permissions.isUserManager())
            throw new NoRightsException(user, Permissions.getMilestoneManager(), project);

        Role role = project.getRoleForUser(user);
        if (role.equals(Role.NONE)) {
            project.setTeamLeader(user);
        } else if (role.equals(Role.DEVELOPER)) {
            project.setTeamLeader(user);
            project.removeDeveloper(user);
        } else {
            throw new MultipleRoleException(user.getLogin(), role.toString(), project.getName());
        }
    }

    public void addDeveloper(Project project, User user) throws MultipleRoleException, NoRightsException {
        Permissions permissions = Permissions.getPermissionsByRole(project.getRoleForUser(user));
        if (!permissions.isUserManager())
            throw new NoRightsException(user, Permissions.getUserManager(), project);

        Role role = project.getRoleForUser(user);
        if (role.equals(Role.NONE)) {
            project.addDeveloper(user);
        } else {
            throw new MultipleRoleException(user.getLogin(), role.toString(), project.getName());
        }
    }

    public void addTester(Project project, User user) throws MultipleRoleException, NoRightsException {
        Permissions permissions = Permissions.getPermissionsByRole(project.getRoleForUser(user));
        if (!permissions.isUserManager())
            throw new NoRightsException(user, Permissions.getUserManager(), project);

        Role role = project.getRoleForUser(user);
        if (role.equals(Role.NONE)) {
            project.addTester(user);
        } else {
            throw new MultipleRoleException(user.getLogin(), role.toString(), project.getName());
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