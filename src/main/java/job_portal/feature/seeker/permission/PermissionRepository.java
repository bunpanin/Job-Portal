package job_portal.feature.seeker.permission;

import job_portal.domain.backend.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission,Integer> {
}
