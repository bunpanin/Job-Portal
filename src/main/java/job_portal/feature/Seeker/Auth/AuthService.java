package job_portal.feature.Seeker.Auth;


import jakarta.mail.MessagingException;
import job_portal.feature.Seeker.Auth.dto.request.LoginRequest;
import job_portal.feature.Seeker.Auth.dto.request.RegisterRequest;
import job_portal.feature.Seeker.Auth.dto.respone.SeekerRespone;

public interface AuthService {
    SeekerRespone login(LoginRequest login);
    void register(RegisterRequest request) throws MessagingException;
}
