package job_portal.feature.company.auth;
import jakarta.mail.MessagingException;
import job_portal.feature.company.auth.dto.request.CompanyLoginRequest;
import job_portal.feature.company.auth.dto.request.CompanyRegisterRequest;
import job_portal.feature.company.auth.dto.request.CompanyVerifyRequest;
import job_portal.feature.seeker.auth.dto.request.RegisterRequest;
import job_portal.feature.seeker.auth.dto.request.VerifyRequest;
import job_portal.feature.seeker.auth.dto.respone.LoginRequest;
import job_portal.feature.seeker.auth.dto.respone.SeekerDataRespone;

public interface CompanyService {

    void login(CompanyLoginRequest companyLoginRequest);
    void verify(CompanyVerifyRequest companyVerifyRequest);
    void register(CompanyRegisterRequest companyRegisterRequest) throws MessagingException;
}
