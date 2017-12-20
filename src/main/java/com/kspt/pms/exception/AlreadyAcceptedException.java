package com.kspt.pms.exception;

import com.kspt.pms.entity.BugReport;
import com.kspt.pms.entity.User;

/**
 * Created by kivi on 14.12.17.
 */
public class AlreadyAcceptedException extends PMSException {

    private static final String template = "Report %d already accepted by user %s";

    public AlreadyAcceptedException(BugReport report, User user) {
        super(String.format(template, report.getId(), user.getLogin()));
    }

}
