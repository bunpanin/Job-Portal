package job_portal.feature.seeker.auth;
import io.swagger.v3.oas.annotations.tags.Tag;
import job_portal.feature.seeker.auth.dto.request.SeekerUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import job_portal.feature.seeker.auth.dto.request.EmailRequest;
import job_portal.feature.seeker.auth.dto.request.RegisterRequest;
import job_portal.feature.seeker.auth.dto.request.VerifyRequest;
import job_portal.feature.seeker.auth.dto.respone.LoginRequest;
import job_portal.feature.seeker.auth.dto.respone.SeekerDataRespone;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/seeker/auth")
@RequiredArgsConstructor
@Tag(name = "Seeker Authentication")
public class SeekerController {

    private final SeekerService seekerService;

    @PatchMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('seeker_manage_profile')")
    void updateSeeker(@PathVariable String uuid, @Valid @RequestBody SeekerUpdateRequest  seekerUpdateRequest) {
        seekerService.updateSeekerByUuid(uuid, seekerUpdateRequest);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    SeekerDataRespone login(@Valid @RequestBody LoginRequest loginRequest){
        return seekerService.login(loginRequest);
    }

    @PostMapping("/resentVerify")
    @ResponseStatus(HttpStatus.OK)
    void resentVerify(@Valid @RequestBody EmailRequest emailRequest) throws MessagingException {
        seekerService.resentVerify(emailRequest);
    }
    @PostMapping("/verify")
    @ResponseStatus(HttpStatus.OK)
    void verify(@Valid @RequestBody VerifyRequest verifyRequest){
        seekerService.verify(verifyRequest);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    void register(@Valid @RequestBody RegisterRequest registerRequest) throws MessagingException{
        seekerService.register(registerRequest);
    }
}