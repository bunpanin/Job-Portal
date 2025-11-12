package job_portal.feature.seeker.auth.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record VerifyRequest(
    @NotBlank(message = "Email is required!")
    @Email(message = "Email must be a valid address!")
    String email,
    @NotBlank(message = "VerificationCode is required!")
    String vertificationCode
) {
}