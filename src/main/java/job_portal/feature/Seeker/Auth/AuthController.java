package job_portal.feature.Seeker.Auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import job_portal.feature.Seeker.Auth.dto.request.LoginRequest;
import job_portal.feature.Seeker.Auth.dto.request.RegisterRequest;
import job_portal.feature.Seeker.Auth.dto.respone.SeekerRespone;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sekker/auth")
@RequiredArgsConstructor
@Tag(name = "Seeker Auth")
public class AuthController {

    private final AuthService authService;

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
