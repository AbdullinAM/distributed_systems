package com.kspt.pms.exception;

/**
 * Created by kivi on 20.12.17.
 */
public class IncorrectMilestoneDateException extends PMSException {

    private static final String template = "Can't create milestone with ending date in the past";

    public IncorrectMilestoneDateException() {
        super(template);
    }
}
