/**
 * Created by Azat on 28.03.2017.
 */

package com.kspt.pms.exception;

import com.kspt.pms.logic.Permissions;

public class NoRightsException extends Exception {

    private static final String template = "User %s don't have permission %s for project %s";

    public NoRightsException(String login, Permissions permissions, String project) {
        super(String.format(template,login, permissions.toString(), project));
    }
}
