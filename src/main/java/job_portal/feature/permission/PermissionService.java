package job_portal.feature.permission;
import job_portal.feature.permission.dto.request.CreatePermissionRequest;
import job_portal.feature.permission.dto.request.UpdatePermissionRequest;
import job_portal.feature.seeker.achievement.dto.request.UpdateAchievementRequest;


public interface PermissionService {
    void deleteById(Integer id);
    void updatePermissionById(Integer id, UpdatePermissionRequest updatePermissionRequest);
    void createNewPermission(String uuid, CreatePermissionRequest createPermissionRequest);
}
