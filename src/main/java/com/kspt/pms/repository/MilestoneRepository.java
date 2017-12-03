package com.kspt.pms.repository;

import com.kspt.pms.project.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by kivi on 03.12.17.
 */
public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
    Collection<Milestone> findByProjectName(String name);
}
