package com.kspt.pms.logic;

import com.kspt.pms.entity.BugReport;
import com.kspt.pms.entity.Comment;
import com.kspt.pms.entity.Project;
import com.kspt.pms.entity.User;
import com.kspt.pms.exception.NoRightsException;
import com.kspt.pms.repository.CommentRepository;

/**
 * Created by kivi on 14.12.17.
 */
public interface ReportManager extends ReportCommenter {
    default void checkReportManagerPermissions(BugReport report) throws NoRightsException {
        User user = getUser();
        Project project = report.getProject();
        Permissions permissions = Permissions.getPermissionsByRole(project.getRoleForUser(user));
        if (!permissions.isReportManager())
            throw new NoRightsException(user, Permissions.getRepoortManager(), project);
    }

    default void reopenReport(BugReport report) throws NoRightsException {
        checkReportManagerPermissions(report);
        report.setReopened();
        commentReport(report, "Opened");
    }

    default void closeReport(BugReport report) throws NoRightsException {
        checkReportManagerPermissions(report);
        report.setClosed();
        commentReport(report, "Closed");
    }
}
