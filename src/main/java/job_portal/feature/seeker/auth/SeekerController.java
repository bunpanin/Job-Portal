package job_portal.feature.seeker.auth;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import job_portal.feature.seeker.auth.dto.EmailRequest;
import job_portal.feature.seeker.auth.dto.RegisterRequest;
import job_portal.feature.seeker.auth.dto.VerifyRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/seeker/auth")
@RequiredArgsConstructor
public class SeekerController {

    private final SeekerService seekerService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    void register(@Valid @RequestBody RegisterRequest registerRequest) throws MessagingException{
        seekerService.register(registerRequest);
    }

    @PostMapping("/verify")
    @ResponseStatus(HttpStatus.CREATED)
    void verify(@Valid @RequestBody VerifyRequest verifyRequest){
        seekerService.verify(verifyRequest);
    }

    @PostMapping("/resentVerify")
    @ResponseStatus(HttpStatus.CREATED)
    void resentVerify(@Valid @RequestBody EmailRequest emailRequest) throws MessagingException {
        seekerService.resentVerify(emailRequest);
    }

}