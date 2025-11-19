package job_portal.feature.seeker.auth.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import job_portal.domain.backend.seeker.WorkExperience;

@Repository
public interface WorkExperienceRepository extends JpaRepository<WorkExperience,Integer> {
    
}
