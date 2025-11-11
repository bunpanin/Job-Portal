package job_portal.feature.seeker.auth.dto;
import jakarta.validation.constraints.NotBlank;

public record VerifyRequest(
    @NotBlank(message = "Email is required!")
    String email,
    @NotBlank(message = "VerificationCode is required!")
    String vertificationCode
) {
}