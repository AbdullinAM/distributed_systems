package com.kspt.pms.logic;

import com.kspt.pms.entity.Comment;
import com.kspt.pms.entity.Project;
import com.kspt.pms.entity.Ticket;
import com.kspt.pms.entity.User;
import com.kspt.pms.exception.NoRightsException;
import com.kspt.pms.exception.WrongStatusException;
import com.kspt.pms.repository.CommentRepository;

/**
 * Created by kivi on 14.12.17.
 */
public interface TicketDeveloper extends TicketCommenter {
    CommentRepository getCommentRepository();

    default void checkTicketDeveloperPermissions(Ticket ticket) throws NoRightsException {
        User user = getUser();
        Project project = ticket.getMilestone().getProject();
        Permissions permissions = Permissions.getPermissionsByRole(project.getRoleForUser(user));
        if (!permissions.isTicketDeveloper())
            throw new NoRightsException(user, Permissions.getTicketDeveloper(), project);
    }

    default void acceptTicket(Ticket ticket) throws NoRightsException {
        checkTicketDeveloperPermissions(ticket);
        if (ticket.isAccepted())
            throw new WrongStatusException("Accepted", "Accepted");
        ticket.setAccepted();
        commentTicket(ticket, "Accepted");
    }

    default void setInProgress(Ticket ticket) throws NoRightsException {
        checkTicketDeveloperPermissions(ticket);
        if (ticket.isInProgress())
            throw new WrongStatusException("In progress", "In progress");
        ticket.setInProgress();
        commentTicket(ticket, "In progress");
    }

    default void finishTicket(Ticket ticket) throws NoRightsException {
        checkTicketDeveloperPermissions(ticket);
        if (ticket.isFinished())
            throw new WrongStatusException("Finished", "Finished");
        ticket.setFinished();
        commentTicket(ticket, "Finished");
    }

}
