package job_portal.feature.seeker.language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import job_portal.domain.backend.seeker.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language,Integer> {

    
}