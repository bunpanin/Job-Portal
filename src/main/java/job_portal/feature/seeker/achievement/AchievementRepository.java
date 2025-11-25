package job_portal.feature.seeker.achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import job_portal.domain.backend.seeker.Achievement;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement,Integer> {

    
}