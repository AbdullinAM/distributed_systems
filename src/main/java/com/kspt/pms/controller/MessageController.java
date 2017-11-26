package com.kspt.pms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.kspt.pms.project.Message;
import com.kspt.pms.repository.MessageRepository;
import com.kspt.pms.repository.UserRepository;
import com.kspt.pms.user.User;

import java.util.Collection;

/**
 * Created by kivi on 26.11.17.
 */
@RestController
@RequestMapping("{login}/messages")
public class MessageController {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    UserRepository userRepository;

    @RequestMapping
    Collection<Message> getMessages(@PathVariable String login) {
        return messageRepository.findByOwnerLogin(login);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addMessage(@PathVariable String login, @RequestBody Message message) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException(login));
        message.setOwner(user);
        messageRepository.save(message);
    }
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String login) {
        super("could not find user with login: " + login);
    }
}
