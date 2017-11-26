package com.kspt.pms.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.kspt.pms.project.Message;
import com.kspt.pms.repository.MessageRepository;
import com.kspt.pms.repository.UserRepository;
import com.kspt.pms.user.User;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by kivi on 26.11.17.
 */
@RestController
@RequestMapping("{login}/messages")
public class MessageController {
    @Autowired
    Logger log;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    UserRepository userRepository;

    @RequestMapping
    Collection<Message> getMessages(@PathVariable String login) {
        log.debug("getMessages for user " + login);
        return messageRepository.findByOwnerLogin(login);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addMessage(@PathVariable String login, @RequestBody Message message) {
        log.debug("addMessage " + message.toString() + " to user " + login);
        Optional<User> user = userRepository.findByLogin(login);
        if (user.isPresent()) {
            message.setOwner(user.get());
            messageRepository.save(message);
        } else {
            log.error("User " + login + "not found");
            new UserNotFoundException(login);
        }
    }
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String login) {
        super("could not find user with login: " + login);
    }
}
