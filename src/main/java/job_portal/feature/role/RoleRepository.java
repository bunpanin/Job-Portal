package job_portal.feature.role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import job_portal.domain.backend.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer>{
    Optional<Role> findByName(String name);
    Optional<Role> findByAlias(String alias);
    Optional<Role> findByUuid(String uuid);
    Boolean existsByAlias(String alias);
    Boolean existsByName(String name);
}