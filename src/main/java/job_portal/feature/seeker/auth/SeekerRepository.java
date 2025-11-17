package job_portal.feature.seeker.auth;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import job_portal.domain.backend.seeker.Seeker;

@Repository
public interface SeekerRepository extends JpaRepository<Seeker,Integer> {

    Boolean existsByEmail(String email); 
    Optional<Seeker> findByEmail(String email);
    Optional<Seeker> findByUuid(String uuid);

}