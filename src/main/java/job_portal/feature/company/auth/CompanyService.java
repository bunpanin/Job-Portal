package job_portal.feature.company.auth;
import jakarta.mail.MessagingException;
import job_portal.feature.company.auth.dto.request.CompanyLoginRequest;
import job_portal.feature.company.auth.dto.request.CompanyRegisterRequest;
import job_portal.feature.company.auth.dto.request.CompanyVerifyRequest;

public interface CompanyService {

    void login(CompanyLoginRequest companyLoginRequest);
    void verify(CompanyVerifyRequest companyVerifyRequest);
    void register(CompanyRegisterRequest companyRegisterRequest) throws MessagingException;
}
