package com.kspt.pms.logic;

import com.kspt.pms.entity.Comment;
import com.kspt.pms.entity.Project;
import com.kspt.pms.entity.Ticket;
import com.kspt.pms.entity.User;
import com.kspt.pms.exception.NoRightsException;
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

    default void notifyNew(Ticket ticket) {
        StringBuilder builder = new StringBuilder();
        builder.append("New ticket: ");
        builder.append(ticket.toString());
        addMessage(builder.toString());
    }

    default void acceptTicket(Ticket ticket) throws NoRightsException {
        checkTicketDeveloperPermissions(ticket);
        ticket.setAccepted();
        commentTicket(ticket, "Accepted");
    }

    default void setInProgress(Ticket ticket) throws NoRightsException {
        checkTicketDeveloperPermissions(ticket);
        ticket.setInProgress();
        commentTicket(ticket, "In progress");
    }

    default void finishTicket(Ticket ticket) throws NoRightsException {
        checkTicketDeveloperPermissions(ticket);
        ticket.setFinished();
        commentTicket(ticket, "Finished");
    }

}
