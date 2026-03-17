package job_portal.feature.rolePermission;

import job_portal.feature.rolePermission.dto.request.CreateRolePermissionRequest;

public interface RolePermissionService {
    void creatNewRolePermission(CreateRolePermissionRequest createRolePermissionRequest);
}
