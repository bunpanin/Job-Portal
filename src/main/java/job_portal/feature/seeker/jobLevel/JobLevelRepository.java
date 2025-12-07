package job_portal.feature.seeker.jobLevel;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import job_portal.domain.backend.seeker.JobLevel;

@Repository
public interface JobLevelRepository extends JpaRepository<JobLevel,Integer> {
    Optional<JobLevel> findByAlias(String alias);
}