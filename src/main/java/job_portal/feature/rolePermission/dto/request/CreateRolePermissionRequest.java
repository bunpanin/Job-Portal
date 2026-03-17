package job_portal.feature.rolePermission.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateRolePermissionRequest(
        @NotBlank(message = "Role is required")
        String roleName,
        @NotNull(message = "PermissionIds is required")
        List<Integer> permissionIds
) {
}
