package job_portal.feature.Seeker;
import job_portal.domain.Seeker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeekerRepository extends JpaRepository<Seeker,Integer> {
}
