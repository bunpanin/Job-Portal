package job_portal.feature.seeker.typeOfExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import job_portal.domain.backend.seeker.TypeOfExperience;

@Repository
public interface TypeOfExperienceRepository extends JpaRepository<TypeOfExperience,Integer> {

}