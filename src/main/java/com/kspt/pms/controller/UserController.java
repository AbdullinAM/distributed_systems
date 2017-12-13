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

    @RequestMapping({"rest/{login}"})
    public User getUser(@PathVariable String login) {
        System.out.println("Find by login: " + login);
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException(login));
        System.out.println(user.getName());
        return user;
    }

    @RequestMapping({"rest/{login}/messages"})
    public Collection<Message> getMessages(@PathVariable String login) {
        return messageRepository.findByOwnerLogin(login);
    }
}
