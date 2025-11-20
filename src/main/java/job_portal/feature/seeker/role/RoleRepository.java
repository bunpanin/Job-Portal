package job_portal.feature.seeker.role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import job_portal.domain.backend.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer>{

}