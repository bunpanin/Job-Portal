package job_portal.feature.permission;

import job_portal.domain.backend.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission,Integer> {
    Optional<Permission> findById(Integer id);
}
