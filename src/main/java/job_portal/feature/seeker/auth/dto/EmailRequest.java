package job_portal.feature.seeker.auth.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailRequest(
    @Email(message = "Email must be a valid address!")
    @NotBlank(message = "Email is required")
    String email
) {
}