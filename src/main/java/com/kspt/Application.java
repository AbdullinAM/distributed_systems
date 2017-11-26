package com.kspt;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.kspt.pms.project.Message;
import com.kspt.pms.repository.MessageRepository;
import com.kspt.pms.repository.UserRepository;
import com.kspt.pms.user.User;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

    @Bean
    CommandLineRunner init(UserRepository userRepository, MessageRepository messageRepository) {
        return (evt) -> {};
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}