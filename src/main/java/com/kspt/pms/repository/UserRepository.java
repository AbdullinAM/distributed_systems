package com.kspt.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kspt.pms.entity.User;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Created by kivi on 26.11.17.
 */
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
}
