package job_portal.feature.seeker.degree;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import job_portal.domain.backend.seeker.Degree;

@Repository
public interface DegreeRepository extends JpaRepository<Degree,Integer>{

    
}