package job_portal.util;

import job_portal.domain.Role;
import job_portal.domain.User;
import job_portal.domain.UserRole;

import java.util.HashSet;

public class GlobalUtil {

    public static void addRoleToUser(User user, Role role) {

        if (user.getUserRoles() == null) {
            user.setUserRoles(new HashSet<>());
        }

        boolean exists = user.getUserRoles().stream()
                .anyMatch(ur -> ur.getRole().getName().equals(role.getName()));

        if (!exists) {
            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole(role);
            user.getUserRoles().add(userRole);
        }
    }

}
