package com.kspt.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kspt.pms.project.Message;

import javax.transaction.Transactional;
import java.util.Collection;

/**
 * Created by kivi on 26.11.17.
 */
@Transactional
public interface MessageRepository extends JpaRepository<Message, Long> {
    Collection<Message> findByOwnerLogin(String login);
}
