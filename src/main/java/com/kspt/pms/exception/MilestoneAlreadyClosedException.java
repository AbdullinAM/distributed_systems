package com.kspt.pms.exception;

import com.kspt.pms.entity.Milestone;

/**
 * Created by kivi on 14.12.17.
 */
public class MilestoneAlreadyClosedException extends PMSException {

    private static final String template = "Milestone %d of project %s is already closed";

    public MilestoneAlreadyClosedException(Milestone milestone) {
        super(String.format(template, milestone.getId(), milestone.getProject().getName()));
    }

}
