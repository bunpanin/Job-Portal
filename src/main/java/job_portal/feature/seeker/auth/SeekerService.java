package job_portal.feature.seeker.auth;

import jakarta.mail.MessagingException;
import job_portal.feature.seeker.auth.dto.RegisterRequest;
import job_portal.feature.seeker.auth.dto.VerifyRequest;

public interface SeekerService {

    void verify(VerifyRequest verifyRequest);
    void register(RegisterRequest registerRequest) throws MessagingException;

}