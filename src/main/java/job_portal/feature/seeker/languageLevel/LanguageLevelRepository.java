package job_portal.feature.seeker.languageLevel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import job_portal.domain.backend.seeker.LanguageLevel;

@Repository
public interface LanguageLevelRepository extends JpaRepository<LanguageLevel,Integer> {

    
}