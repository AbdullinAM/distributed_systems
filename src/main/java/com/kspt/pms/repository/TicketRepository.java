package com.kspt.pms.repository;

import com.kspt.pms.project.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by kivi on 03.12.17.
 */
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Collection<Ticket> findByMilestoneId(Long id);
    Collection<Ticket> findByCreatorLogin(String login);
}
