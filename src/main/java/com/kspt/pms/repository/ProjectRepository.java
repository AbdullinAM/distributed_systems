package com.kspt.pms.repository;

import com.kspt.pms.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by kivi on 03.12.17.
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByName(String name);
}