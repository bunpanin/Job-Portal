package job_portal.feature.role;

import job_portal.feature.role.dto.request.CreateRoleRequest;

public interface RoleService {
    void createNewRole(String uuid, CreateRoleRequest createRoleRequest);
    
}
