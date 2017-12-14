package com.kspt.pms.logic;

import com.kspt.pms.entity.Comment;
import com.kspt.pms.entity.Ticket;
import com.kspt.pms.repository.CommentRepository;

/**
 * Created by kivi on 14.12.17.
 */
public interface TicketCommenter extends UserInterface {
    CommentRepository getCommentRepository();

    default void commentTicket(Ticket ticket, String description) {
        Comment comment = new Comment();
        comment.setDescription(description);
        comment.setUser(getUser());
        getCommentRepository().save(comment);
        ticket.addComment(comment);
    }
}
