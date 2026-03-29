package job_portal.feature.seeker.Auth;


import jakarta.mail.MessagingException;
import job_portal.feature.seeker.Auth.dto.request.LoginRequest;
import job_portal.feature.seeker.Auth.dto.request.RegisterRequest;
import job_portal.feature.seeker.Auth.dto.request.UpdateRequest;
import job_portal.feature.seeker.Auth.dto.respone.SeekerRespone;
import org.springframework.security.core.Authentication;

public interface AuthService {
    void logout(Authentication authentication);
    void updateSeekerByUuid(UpdateRequest update);
    SeekerRespone login(LoginRequest login);
    void register(RegisterRequest request) throws MessagingException;
}
