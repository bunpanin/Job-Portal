package job_portal.feature.seeker.Auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import job_portal.feature.seeker.Auth.dto.request.LoginRequest;
import job_portal.feature.seeker.Auth.dto.request.RegisterRequest;
import job_portal.feature.seeker.Auth.dto.request.UpdateRequest;
import job_portal.feature.seeker.Auth.dto.respone.SeekerRespone;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/seeker/auth")
@RequiredArgsConstructor
@Tag(name = "Seeker Auth")
public class AuthController {

    private final AuthService authService;

    @PatchMapping("/profile")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('SEEKER_MANAGE_PROFILE')")
    void updateSeeker(@Valid @RequestBody UpdateRequest update) {
        authService.updateSeekerByUuid(update);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    SeekerRespone login(@Valid @RequestBody LoginRequest login){
       return authService.login(login);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    void register(@Valid @RequestBody RegisterRequest request) throws MessagingException {
        authService.register(request);
    }


}
