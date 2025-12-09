package job_portal.feature.company.auth;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import job_portal.feature.company.auth.dto.request.CompanyLoginRequest;
import job_portal.feature.company.auth.dto.request.CompanyRegisterRequest;
import job_portal.feature.company.auth.dto.request.CompanyVerifyRequest;
import job_portal.feature.seeker.auth.dto.request.RegisterRequest;
import job_portal.feature.seeker.auth.dto.request.VerifyRequest;
import job_portal.feature.seeker.auth.dto.respone.LoginRequest;
import job_portal.feature.seeker.auth.dto.respone.SeekerDataRespone;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/company/auth")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    void login(@Valid @RequestBody CompanyLoginRequest companyLoginRequest){
        companyService.login(companyLoginRequest);
//        return seekerService.login(loginRequest);
    }


    @PostMapping("/verify")
    @ResponseStatus(HttpStatus.OK)
    void verify(@Valid @RequestBody CompanyVerifyRequest companyVerifyRequest){
        companyService.verify(companyVerifyRequest);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    void registerCompany(@Valid @RequestBody CompanyRegisterRequest companyRegisterRequest) throws MessagingException{
        companyService.register(companyRegisterRequest);
    }

}
