package com.kspt.pms.controller;

import com.kspt.pms.entity.Message;
import com.kspt.pms.entity.Project;
import com.kspt.pms.entity.User;
import com.kspt.pms.exception.UnknownRequestParamValueException;
import com.kspt.pms.exception.NotFoundException;
import com.kspt.pms.repository.MessageRepository;
import com.kspt.pms.repository.ProjectRepository;
import com.kspt.pms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @RequestMapping("rest/{login}/authenticate")
    public String authenticate(@PathVariable String login,
                               @RequestParam("passwd") String passwd) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException(login));
        return Boolean.toString(user.getPassword().equals(passwd));
    }

    @RequestMapping("rest/{login}")
    public User getUser(@PathVariable String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException(login));
    }

    @RequestMapping(value = "rest/{login}", method = RequestMethod.POST)
    public void addUser(@PathVariable String login, @RequestBody User user) {
        System.out.println("Saving user: " + user.getName() + " " + user.getLogin());
        userRepository.save(user);
    }

    @RequestMapping("rest/{login}/messages")
    public Collection<Message> getMessages(@PathVariable String login) {
        return messageRepository.findByOwnerLogin(login);
    }

    @RequestMapping(value = "rest/{login}/messages", method = RequestMethod.POST)
    public void addMessage(@PathVariable String login, @RequestBody Message message) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException(login));
        message.setOwner(user);
        messageRepository.save(message);
    }

    @RequestMapping("rest/{login}/projects")
    public Collection<Project> getProjects(@PathVariable String login,
                                           @RequestParam("type") String type) {
        Collection<Project> result;
        switch (type) {
            case "manager": result = projectRepository.findByManagerLogin(login); break;
            case "teamlead": result = projectRepository.findByTeamLeaderLogin(login); break;
            case "dev": result = new ArrayList<>(); break;
            case "tester": result = new ArrayList<>(); break;
            default: throw new UnknownRequestParamValueException("type", type);
        }
        return result;
    }

    @RequestMapping(value = "rest/{login}/projects", method = RequestMethod.POST)
    public void createProject(@PathVariable String login, @RequestBody Project project) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException(login));
        project.setManager(user);
        projectRepository.save(project);
    }
}
