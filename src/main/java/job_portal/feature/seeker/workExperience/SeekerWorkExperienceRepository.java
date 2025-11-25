package job_portal.feature.seeker.workExperience;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import job_portal.domain.backend.seeker.SeekerWorkExperience;

@Repository
public interface SeekerWorkExperienceRepository extends JpaRepository<SeekerWorkExperience,Integer> {
}
