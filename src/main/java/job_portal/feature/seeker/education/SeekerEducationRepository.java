package job_portal.feature.seeker.education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import job_portal.domain.backend.seeker.SeekerEducation;

@Repository
public interface SeekerEducationRepository extends JpaRepository<SeekerEducation,Integer> {
    
}
