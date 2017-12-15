package com.kspt.pms.controller;

import com.kspt.pms.entity.Message;
import com.kspt.pms.entity.Project;
import com.kspt.pms.entity.Role;
import com.kspt.pms.entity.User;
import com.kspt.pms.exception.UnknownRequestParamValueException;
import com.kspt.pms.exception.UserNotFoundException;
import com.kspt.pms.repository.MessageRepository;
import com.kspt.pms.repository.ProjectRepository;
import com.kspt.pms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @RequestMapping("rest/{login}")
    public User getUser(@PathVariable String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException(login));
    }

    @RequestMapping("rest/{login}/messages")
    public Collection<Message> getMessages(@PathVariable String login) {
        return messageRepository.findByOwnerLogin(login);
    }

    @RequestMapping(value = "rest/{login}/messages", method = RequestMethod.POST)
    public void addMessage(@PathVariable String login, @RequestBody Message message) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException(login));
        message.setOwner(user);
        messageRepository.save(message);
    }

    @RequestMapping("rest/{login}/projects")
    public Collection<Project> getProjects(@PathVariable String login,
                                           @RequestParam("type") String type) {
        System.out.println("Resuest param: " + type);
        Collection<Project> result;
        switch (type) {
            case "manager": result = projectRepository.findByManagerLogin(login); break;
            case "teamlead": result = projectRepository.findByTeamLeaderLogin(login); break;
            case "dev": result = projectRepository.findByDevelopersContaining(login); break;
            case "tester": result = projectRepository.findByTestersContaining(login); break;
            default: throw new UnknownRequestParamValueException("type", type);
        }
        System.out.println("Result size: " + result.size());
        return result;
    }
}
