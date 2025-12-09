package job_portal.feature.seeker.auth;
import java.util.Optional;

import job_portal.domain.backend.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import job_portal.domain.backend.seeker.EmailVerification;
import job_portal.domain.backend.seeker.Seeker;


@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerification,Integer> {
    Optional<EmailVerification> findBySeeker(Seeker seeker);
    Optional<EmailVerification> findByCompany(Company company);
}