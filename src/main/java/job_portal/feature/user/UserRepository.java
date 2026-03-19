package job_portal.feature.user;
import job_portal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
