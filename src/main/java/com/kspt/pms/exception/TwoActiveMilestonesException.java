package com.kspt.pms.exception;

/**
 * Created by kivi on 14.12.17.
 */
public class TwoActiveMilestonesException extends RuntimeException {

    private static final String template = "Attempting to activate milestone %d, when milestone %d is already active";

    public TwoActiveMilestonesException(Long active, Long attempting) {
        super(String.format(template, attempting, active));
    }
}
