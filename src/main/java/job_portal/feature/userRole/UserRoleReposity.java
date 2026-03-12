package job_portal.feature.userRole;

import job_portal.domain.backend.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleReposity extends JpaRepository<UserRole,Integer> {

}
