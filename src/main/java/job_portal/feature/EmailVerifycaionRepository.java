package job_portal.feature;

import job_portal.domain.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailVerifycaionRepository extends JpaRepository<EmailVerification,Integer> {

}
