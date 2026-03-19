package job_portal.feature.admin.RolePermission;

import job_portal.domain.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission,Integer> {

}
