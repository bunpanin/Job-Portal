package job_portal.feature.seeker.auth;

import jakarta.mail.MessagingException;
import job_portal.feature.seeker.auth.dto.request.EmailRequest;
import job_portal.feature.seeker.auth.dto.request.RegisterRequest;
import job_portal.feature.seeker.auth.dto.request.VerifyRequest;
import job_portal.feature.seeker.auth.dto.respone.LoginRequest;
import job_portal.feature.seeker.auth.dto.respone.SeekerDataRespone;

public interface SeekerService {
    

    // SeekerDataRespone updateByUuid(String uuid,SeekerUpdateRequest seekerUpdateRequest);

    SeekerDataRespone login(LoginRequest loginRequest);
    // void resentVerify(VerifyRequest verifyRequest);
    void resentVerify(EmailRequest emailRequest) throws MessagingException;
    void verify(VerifyRequest verifyRequest);
    void register(RegisterRequest registerRequest) throws MessagingException;

}