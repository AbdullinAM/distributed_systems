package com.kspt.pms.exception;

/**
 * Created by kivi on 14.12.17.
 */
public class MilestoneTicketNotClosedException extends PMSException {

    private static final String template = "Ticket %d is not closed";

    public MilestoneTicketNotClosedException(Long id) {
        super(String.format(template, id));
    }
}
