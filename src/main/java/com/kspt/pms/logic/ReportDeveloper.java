package com.kspt.pms.logic;

import com.kspt.pms.entity.BugReport;
import com.kspt.pms.entity.Comment;
import com.kspt.pms.entity.Project;
import com.kspt.pms.entity.User;
import com.kspt.pms.exception.AlreadyAcceptedException;
import com.kspt.pms.exception.NoRightsException;
import com.kspt.pms.repository.CommentRepository;

/**
 * Created by kivi on 14.12.17.
 */
public interface ReportDeveloper extends ReportCommenter {

    default void checkReportDeveloperPermissions(BugReport report) throws NoRightsException {
        User user = getUser();
        Project project = report.getProject();
        Permissions permissions = Permissions.getPermissionsByRole(project.getRoleForUser(user));
        if (!permissions.isReportDeveloper())
            throw new NoRightsException(user, Permissions.getReportDeveloper(), project);
    }

    default void notifyNew(BugReport report) {
        StringBuilder builder = new StringBuilder();
        builder.append("New bug report: ");
        builder.append(report.toString());
        addMessage(builder.toString());
    }

    default void acceptReport(BugReport report) throws AlreadyAcceptedException, NoRightsException {
        checkReportDeveloperPermissions(report);
        if (report.getDeveloper() != null && !report.getDeveloper().equals(getUser()))
            throw new AlreadyAcceptedException(report, getUser());

        report.setAccepted();
        commentReport(report, "Accepted");
    }

    default void fixReport(BugReport report) throws NoRightsException {
        checkReportDeveloperPermissions(report);
        report.setFixed();
        commentReport(report, "Fixed");
    }

}