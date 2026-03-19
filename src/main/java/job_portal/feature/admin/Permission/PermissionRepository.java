package job_portal.feature.admin.Permission;

import job_portal.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Integer> {
    Optional<Permission> findByName(String name);
    Optional<Permission> findById(Integer id);
}
