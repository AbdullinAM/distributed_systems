package com.kspt.pms.controller;

import com.kspt.pms.entity.Message;
import com.kspt.pms.entity.User;
import com.kspt.pms.exception.UserNotFoundException;
import com.kspt.pms.repository.MessageRepository;
import com.kspt.pms.repository.UserRepository;
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
}
