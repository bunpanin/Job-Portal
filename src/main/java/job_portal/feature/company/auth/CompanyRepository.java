package job_portal.feature.company.auth;
import job_portal.domain.backend.company.Company;
import job_portal.domain.backend.seeker.Seeker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Integer> {
    Boolean existsByEmail(String email);
    Optional<Company> findByEmail(String email);
    Optional<Company> findByUuid(String uuid);
}
