package com.kspt.pms.exception;

/**
 * Created by kivi on 14.12.17.
 */
public class WrongStatusException extends PMSException {
    public WrongStatusException(String prev, String next) {
        super("Cannot change status from " + prev + " to " + next);
    }
}
