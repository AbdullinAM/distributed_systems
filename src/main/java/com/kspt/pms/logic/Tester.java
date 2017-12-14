package com.kspt.pms.logic;

import com.kspt.pms.entity.User;
import com.kspt.pms.repository.BugReportRepository;
import com.kspt.pms.repository.CommentRepository;
import com.kspt.pms.repository.MessageRepository;
import com.kspt.pms.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by kivi on 14.12.17.
 */
public class Tester implements ReportCreator, ReportManager {

    private User user;
    @Autowired
    BugReportRepository bugReportRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    MessageRepository messageRepository;

    public Tester(User user) {
        this.user = user;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public CommentRepository getCommentRepository() {
        return commentRepository;
    }

    @Override
    public MessageRepository getMessageRepository() {
        return messageRepository;
    }

    @Override
    public BugReportRepository getBugReportRepository() {
        return bugReportRepository;
    }
}
