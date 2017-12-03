package com.kspt.pms.repository;

import com.kspt.pms.project.BugReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by kivi on 03.12.17.
 */
public interface BugReportRepository extends JpaRepository<BugReport, Long> {
    Collection<BugReport> findByProjectName(String name);
    Collection<BugReport> findByCreatorLogin(String login);
    Collection<BugReport> findByDeveloperLogin(String login);
}