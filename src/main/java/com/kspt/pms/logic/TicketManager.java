package com.kspt.pms.logic;

import com.kspt.pms.entity.*;
import com.kspt.pms.exception.MilestoneAlreadyClosedException;
import com.kspt.pms.exception.NoRightsException;
import com.kspt.pms.repository.CommentRepository;
import com.kspt.pms.repository.TicketRepository;

/**
 * Created by kivi on 14.12.17.
 */
public interface TicketManager  extends TicketCommenter {
    TicketRepository getTicketRepository();

    default void checkTicketManagerPermissions(Ticket ticket) throws NoRightsException {
        User user = getUser();
        Project project = ticket.getMilestone().getProject();
        Permissions permissions = Permissions.getPermissionsByRole(project.getRoleForUser(user));
        if (!permissions.isTicketDeveloper())
            throw new NoRightsException(user, Permissions.getTicketManager(), project);
    }

    default Ticket createTicket(Milestone milestone, String task) throws MilestoneAlreadyClosedException, NoRightsException {
        Project project = milestone.getProject();
        Permissions permissions = Permissions.getPermissionsByRole(project.getRoleForUser(getUser()));
        if (!permissions.isTicketManager())
            throw new NoRightsException(getUser(), Permissions.getTicketManager(), project);
        if (milestone.isClosed()) throw new MilestoneAlreadyClosedException(milestone);
        Ticket ticket = new Ticket();
        ticket.setMilestone(milestone);
        ticket.setTask(task);
        ticket.setCreator(getUser());
        getTicketRepository().save(ticket);
        return ticket;
    }

    default void addAssignee(Ticket ticket, TicketDeveloper developer) throws NoRightsException {
        checkTicketManagerPermissions(ticket);
        User user = developer.getUser();
        Project project = ticket.getMilestone().getProject();
        Permissions permissions = Permissions.getPermissionsByRole(project.getRoleForUser(user));
        if (!permissions.isTicketDeveloper())
            throw new NoRightsException(user, Permissions.getTicketDeveloper(), project);
        ticket.addAssignee(user);
    }

    default void reopenTicket(Ticket ticket, String description) throws NoRightsException {
        checkTicketManagerPermissions(ticket);
        ticket.setNew();
        Comment comment = new Comment();
        comment.setDescription(description);
        comment.setUser(getUser());
        getCommentRepository().save(comment);
        ticket.addComment(comment);
    }

    default void closeTicket(Ticket ticket) throws NoRightsException {
        checkTicketManagerPermissions(ticket);
        ticket.setClosed();
        commentTicket(ticket, "Closed");
    }

}
