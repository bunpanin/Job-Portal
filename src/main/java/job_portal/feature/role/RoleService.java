package job_portal.feature.role;

import job_portal.feature.role.dto.request.CreateRoleRequest;
import job_portal.feature.role.dto.request.UpdateRoleRequest;

public interface RoleService {
    void deleteByUuid(String uuid);
    void updateByUuid(String uuid,UpdateRoleRequest updateRoleRequest);
    void createNewRole(CreateRoleRequest createRoleRequest);
}
