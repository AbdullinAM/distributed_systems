package com.kspt.pms.logic;

import com.kspt.pms.entity.Milestone;
import com.kspt.pms.entity.Project;
import com.kspt.pms.entity.Role;
import com.kspt.pms.entity.User;
import com.kspt.pms.exception.*;
import com.kspt.pms.repository.MilestoneRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Set;

/**
 * Created by kivi on 14.12.17.
 */
public class Manager {

    private User user;
    @Autowired
    MilestoneRepository milestoneRepository;

    public Manager(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Milestone createMilestone(Project project, Date start, Date end) throws NoRightsException {
        Permissions permissions = Permissions.getPermissionsByRole(project.getRoleForUser(user));
        if (!permissions.isMilestoneManager())
            throw new NoRightsException(user.getLogin(), Permissions.getMilestoneManager(), project.getName());

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
            throw new NoRightsException(user.getLogin(), Permissions.getMilestoneManager(), project.getName());

        milestone.setActive();
    }

    public void closeMilestone(Milestone milestone) throws MilestoneTicketNotClosedException, WrongStatusException, NoRightsException {
        Project project = milestone.getProject();
        Permissions permissions = Permissions.getPermissionsByRole(project.getRoleForUser(user));
        if (!permissions.isMilestoneManager())
            throw new NoRightsException(user.getLogin(), Permissions.getMilestoneManager(), project.getName());

        milestone.setClosed();
    }

    public void setTeamLeader(Project project, User user) throws MultipleRoleException, NoRightsException {
        Permissions permissions = Permissions.getPermissionsByRole(project.getRoleForUser(user));
        if (!permissions.isUserManager())
            throw new NoRightsException(user.getLogin(), Permissions.getUserManager(), project.getName());

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
            throw new NoRightsException(user.getLogin(), Permissions.getUserManager(), project.getName());

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
            throw new NoRightsException(user.getLogin(), Permissions.getUserManager(), project.getName());

        Role role = project.getRoleForUser(user);
        if (role.equals(Role.NONE)) {
            project.addTester(user);
        } else {
            throw new MultipleRoleException(user.getLogin(), role.toString(), project.getName());
        }
    }
}
