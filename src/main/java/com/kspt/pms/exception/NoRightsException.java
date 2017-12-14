/**
 * Created by Azat on 28.03.2017.
 */

package com.kspt.pms.exception;

import com.kspt.pms.entity.Project;
import com.kspt.pms.entity.User;
import com.kspt.pms.logic.Permissions;

public class NoRightsException extends Exception {

    private static final String template = "User %s don't have permission %s for project %s";

    public NoRightsException(User user, Permissions permissions, Project project) {
        super(String.format(template, user.getLogin(), permissions.toString(), project.getName()));
    }
}
