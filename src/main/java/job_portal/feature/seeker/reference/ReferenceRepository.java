package job_portal.feature.seeker.reference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import job_portal.domain.backend.seeker.SeekerReference;

@Repository
public interface ReferenceRepository extends JpaRepository<SeekerReference,Integer>{

    
}