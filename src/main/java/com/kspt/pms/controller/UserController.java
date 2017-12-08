package com.kspt.pms.controller;

import com.kspt.pms.entity.User;
import com.kspt.pms.exception.UserNotFoundException;
import com.kspt.pms.entity.Message;
import com.kspt.pms.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by kivi on 03.12.17.
 */
@RestController
@RequestMapping({"{login}"})
public class UserController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping
    public User getUser(@PathVariable String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException(login));
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addUser(@PathVariable String login, @RequestBody User user) {
        userRepository.save(user);
    }
}
