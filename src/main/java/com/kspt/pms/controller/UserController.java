package com.kspt.pms.controller;

import com.kspt.pms.entity.*;
import com.kspt.pms.exception.UnknownRequestParamValueException;
import com.kspt.pms.exception.NotFoundException;
import com.kspt.pms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by kivi on 03.12.17.
 */
@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    BugReportRepository bugReportRepository;

    @RequestMapping("rest/user/{login}/authenticate")
    public String authenticate(@PathVariable String login,
                               @RequestParam("passwd") String passwd) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException(login));
        return Boolean.toString(user.getPassword().equals(passwd));
    }

    @RequestMapping("rest/user/{login}")
    public User getUser(@PathVariable String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException(login));
    }

    @RequestMapping(value = "rest/user/{login}", method = RequestMethod.POST)
    public void addUser(@PathVariable String login, @RequestBody User user) {
        userRepository.save(user);
    }

    @RequestMapping("rest/user/{login}/messages")
    public Collection<Message> getMessages(@PathVariable String login) {
        return messageRepository.findByOwnerLogin(login);
    }

    @RequestMapping(value = "rest/user/{login}/messages", method = RequestMethod.POST)
    public void addMessage(@PathVariable String login, @RequestBody Message message) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException(login));
        message.setOwner(user);
        messageRepository.save(message);
    }

    @RequestMapping("rest/user/{login}/projects")
    public Collection<Project> getProjects(@PathVariable String login,
                                           @RequestParam("type") String type) {
        Collection<Project> result;
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException(login));
        switch (type) {
            case "manager": result = projectRepository.findByManagerLogin(login); break;
            case "teamlead": result = projectRepository.findByTeamLeaderLogin(login); break;
            case "dev": result = projectRepository.findByDevelopersContaining(user); break;
            case "tester": result = projectRepository.findByTestersContaining(user); break;
            default: throw new UnknownRequestParamValueException("type", type);
        }
        return result;
    }

    @RequestMapping(value = "rest/user/{login}/projects", method = RequestMethod.POST)
    public void createProject(@PathVariable String login, @RequestBody Project project) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException(login));
        project.setManager(user);
        projectRepository.save(project);
    }

    @RequestMapping("rest/user/{login}/managed_tickets")
    public Collection<Ticket> getManagedTickets(@PathVariable String login) {
        return ticketRepository.findByCreatorLogin(login);
    }

    @RequestMapping("rest/user/{login}/assigned_tickets")
    public Collection<Ticket> getAssignedTickets(@PathVariable String login) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException(login));
        return ticketRepository.findByAssigneesContaining(user);
    }

    @RequestMapping("rest/user/{login}/managed_reports")
    public Collection<BugReport> getManagedReports(@PathVariable String login) {
        return bugReportRepository.findByCreatorLogin(login);
    }

    @RequestMapping("rest/user/{login}/assigned_reports")
    public Collection<BugReport> getAssignedReports(@PathVariable String login) {
        return bugReportRepository.findByDeveloperLogin(login);
    }
}
