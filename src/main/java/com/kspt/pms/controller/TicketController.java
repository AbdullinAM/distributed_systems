package com.kspt.pms.controller;

import com.kspt.pms.entity.BugReport;
import com.kspt.pms.entity.Comment;
import com.kspt.pms.entity.Ticket;
import com.kspt.pms.entity.User;
import com.kspt.pms.exception.NotFoundException;
import com.kspt.pms.logic.Developer;
import com.kspt.pms.logic.Manager;
import com.kspt.pms.logic.TeamLeader;
import com.kspt.pms.logic.Tester;
import com.kspt.pms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by kivi on 18.12.17.
 */
@RestController
public class TicketController {
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BugReportRepository bugReportRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    MessageRepository messageRepository;

    @RequestMapping("/rest/ticket/{id}")
    public Ticket getTicket(@PathVariable Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket: " + id.toString()));
    }

    @RequestMapping(value = "/rest/ticket/{id}", method = RequestMethod.PUT)
    public void updateStatus(@PathVariable Long id,
                             @RequestParam("user") String login,
                             @RequestBody String status) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket: " + id.toString()));
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException(login));
        TeamLeader teamLeader = new TeamLeader(user, ticketRepository, bugReportRepository, commentRepository, messageRepository);
        switch (status) {
            case "OPENED":
                teamLeader.reopenTicket(ticket);
                break;
            case "ACCEPTED":
                teamLeader.acceptTicket(ticket);
                break;
            case "IN_PROGRESS":
                teamLeader.setInProgress(ticket);
                break;
            case "FINISHED":
                teamLeader.finishTicket(ticket);
                break;
            case "CLOSED":
                teamLeader.closeTicket(ticket);
                break;
            default: break;
        }
        ticketRepository.save(ticket);
    }

    @RequestMapping("/rest/ticket/{id}/assignees")
    public Collection<User> getAssignees(@PathVariable Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket: " + id.toString()));
        return ticket.getAssignees();
    }

    @RequestMapping(value = "/rest/ticket/{id}/assignees", method = RequestMethod.POST)
    public void getAssignees(@PathVariable Long id,
                             @RequestParam("user") String login,
                             @RequestBody User assignee) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket: " + id.toString()));
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException(login));
        User assign = userRepository.findByLogin(assignee.getLogin())
                .orElseThrow(() -> new NotFoundException(assignee.getLogin()));
        Manager manager = new Manager(user, ticketRepository, commentRepository, messageRepository);
        manager.addAssignee(ticket, assign);
        ticketRepository.save(ticket);
    }

    @RequestMapping("/rest/ticket/{id}/comments")
    public Collection<Comment> getComments(@PathVariable Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket " + id.toString()));
        return ticket.getComments();
    }

    @RequestMapping(value = "/rest/ticket/{id}/comments", method = RequestMethod.POST)
    public void getComments(@PathVariable Long id,
                            @RequestParam("user") String login,
                            @RequestBody Comment comment) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket " + id.toString()));
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException(login));
        Developer developer = new Developer(user, bugReportRepository, commentRepository, messageRepository);
        developer.commentTicket(ticket, comment.getDescription());
        ticketRepository.save(ticket);
    }
}
