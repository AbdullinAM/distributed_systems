package com.kspt.pms.exception;

/**
 * Created by kivi on 20.12.17.
 */
public class UserAlreadyExistsException extends RuntimeException {

    private static final String template = "User %s alreasy exists in system";

    public UserAlreadyExistsException(String login) {
        super(String.format(template, login));
    }
}
